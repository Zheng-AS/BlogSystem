package csdn.service;

import csdn.dao.BlogDao;
import csdn.dao.CommentDao;
import csdn.dao.DaoFactory;
import csdn.dao.UserDao;
import csdn.po.Blog;
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

    @Test
    public void myTest(){
        String userName = "潘";
        //String userName = "";
        String tag = "人工智能";
        //String tag = "";
        String title = "5G";
        //String title = "";
        findBlog(userName,tag,title,"0");
    }
}
