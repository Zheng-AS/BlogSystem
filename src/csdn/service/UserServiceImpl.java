package csdn.service;

import csdn.dao.BlogDao;
import csdn.dao.CommentDao;
import csdn.dao.DaoFactory;
import csdn.dao.UserDao;
import csdn.po.Blog;
import csdn.po.Comment;
import csdn.po.User;
import org.junit.Test;

import java.util.ArrayList;

public class UserServiceImpl implements UserService {
    private UserDao userDao = DaoFactory.getUserDao();
    private BlogDao blogDao = DaoFactory.getBlogDao();
    private CommentDao commentDao = DaoFactory.getCommentDao();
    @Override
    public int register(String userName, String password) {
        if(userDao.isExist(userName)){
            return 0;
        }
        return userDao.addUser(new User(userName,password));
    }

    @Override
    public int login(String userName, String password){
        return userDao.selectUser(new User(userName,password));
    }

    @Override
    public boolean updateMes(User user) {
        boolean result = false;
        if (userDao.update(user) == 1) {
            result = true;
        }
        return result;
    }

    @Override
    public int createBlog(Blog blog) {
        int result = 0;
        if(blogDao.createBlog(blog) == 1){
            result = 1;
        }
        return result;
    }

    @Override
    public ArrayList<Blog> checkBlog(int uId) {
        return blogDao.checkBlog(uId);
    }

    @Override
    public Blog viewBlog(int bId) {
        return blogDao.queryBlog(bId);
    }

    @Override
    public int updateBlog(Blog blog) {
        return blogDao.updateBlog(blog);
    }

    @Override
    public ArrayList<Blog> findBlog(String userName, String tag, String title, String index) {
        StringBuilder sql = new StringBuilder();
        ArrayList<String> queryMes = new ArrayList<>();

        //进行字符串拼接，若字段不为空将其存储进queryMes数组
        title = "%%" + title + "%%";
        sql.append("select * from blog where is_pub = \"是\" ");
        if ("" != userName) {
            sql.append("and uid = (select uid from user where username = ?) ");
            queryMes.add(userName);
        }
        if ("" != tag) {
            sql.append("and tag = ? ");
            queryMes.add(tag);
        }
        if ("" != title){
            sql.append("having title like ? ");
            queryMes.add(title);
        }
        sql.append("order by n_of_con desc, n_of_like desc ");
        sql.append("limit " + index + ", 6");
        System.out.println(sql.toString());
        return blogDao.findBlog(sql.toString(),queryMes);
    }

    @Override
    public String changeLikeNum(int uId, int bId) {
        //检测是否为自己的博客
        if(blogDao.queryBlog(bId).getuId() != uId) {
            //检测是已点赞
            if (blogDao.likeIsExist(uId, bId)) {
                return blogDao.reduceLikeNum(uId, bId);
            } else {
                return blogDao.addLikeNum(uId, bId);
            }
        }
        return "不可以给自己点赞哦";
    }

    @Override
    public String changeConNum(int uId, int bId) {
        //检测是否为自己的博客
        if(blogDao.queryBlog(bId).getuId() != uId){
            //检测是否已收藏
            if(userDao.conIsExist(uId,bId)){
                return blogDao.cancelCon(uId,bId);
            }else {
                return blogDao.addCon(uId,bId);
            }
        }
        return "不可以收藏自己的博客哦";
    }

    @Override
    public String getAuthorName(int uId) {
        return userDao.getNameByUId(uId);
    }

    @Override
    public String changeUserAttn(int uId, int aId) {
        //判断是否为自己
        if(uId != aId) {
            //检测是否已关注
            if (userDao.attnIsExist(uId, aId)) {
                return userDao.cancelAttn(uId, aId);
            } else {
                return userDao.addAttn(uId, aId);
            }
        }
        return "不可以关注自己哦";
    }

    @Override
    public String addComment(int uId, int bId, String content) {
        return commentDao.addComment(uId, bId, content);
    }

    @Override
    public ArrayList<Comment> getComment(int bId) {
        //功能：获取该  博客id下的评论 <-- 该评论的回复 <-- 该回复评论的回复 <-- .........

        //获取该博客下的评论
        ArrayList<Comment> commentArrayList = commentDao.getCommentByBId(bId);
        //调用递归方法
        getRespComList(commentArrayList);
        return commentArrayList;
    }

    //封装为一个方法
    public void getRespComList(ArrayList<Comment> commentArrayList){
        //对此评论数组进行遍历，使该数组下的评论对象进行如下操作
        for (Comment comment : commentArrayList) {
            //获取这个评论的用户呢称
            comment.setUserName(userDao.getNameByUId(comment.getuId()));
            //获取这每个评论的回复评论的ID数组
            ArrayList<String> commentRespId = commentDao.getCommentRespId(comment.getcId());
            //创建回复评论数组
            ArrayList<Comment> respCommentArrayList = new ArrayList<>();
            //遍历该数组，使该数组下每个评论ID进行如下操作
            for (String cId : commentRespId) {
                //查找对应ID的评论信息
                Comment respComment = commentDao.getCommentByCId(cId);
                //将获得到的评论对象存入回复数组中
                respCommentArrayList.add(respComment);
                //将回复评论数组存入上一级评论中
                comment.setRespCom(respCommentArrayList);
            }
            //对该回复评论ID数组对应的回复评论数组进行递归调用，查找该数组内的回复评论的回复评论数组
            getRespComList(respCommentArrayList);
        }
    }

    @Test
    public void myTest(){
        ArrayList<Comment> a = new ArrayList<>();
        a.add(new Comment("00000000000", "qqqqqq", 1, null));
        ArrayList<Comment> b = a;
        ArrayList<Comment> c = new ArrayList<>();
        c.add(new Comment("dahwuidahwu", "woshineirong", 8, null));
        for (Comment comment : b) {
            comment.setuId(88);
            comment.setcId("11111111111");

            comment.setRespCom(c);
            System.out.println(comment.getcId() +"{"+comment.getContent()+"}");
        }
        b = c;
        for (Comment comment : a) {
            System.out.println(comment.toString());
        }
        for (Comment comment : b) {
            System.out.println(comment.toString());
        }
    }
}
