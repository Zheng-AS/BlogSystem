package csdn.service;

import csdn.dao.*;
import csdn.po.Blog;
import csdn.po.Comment;
import csdn.po.User;
import csdn.po.UserMes;
import csdn.util.JdbcUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserServiceImpl implements UserService {
    private UserDao userDao = DaoFactory.getUserDao();
    private BlogDao blogDao = DaoFactory.getBlogDao();
    private CommentDao commentDao = DaoFactory.getCommentDao();
    private UserMesDao userMesDao = DaoFactory.getUserMesDao();
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
    public ArrayList<Blog> checkBlog(int uId, int index) {
        return blogDao.checkBlog(uId, index);
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
    public ArrayList<Comment> getComment(int bId, int index) {
        //功能：获取该  博客id下的评论 <-- 该评论的回复 <-- 该回复评论的回复 <-- .........

        //获取该博客下的评论
        ArrayList<Comment> commentArrayList = commentDao.getCommentByBId(bId,index);
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

    @Override
    public String addRespCom(String cId, String content, int uId) {
        if(content == ""){
            return "评论不能为空";
        }
        return commentDao.addRespCom(cId,content,uId);
    }

    /**
     *  删除工具
     */
    public PreparedStatement deleteRespComUtil(ArrayList<String> commentId, Connection con, PreparedStatement ps) throws SQLException {
        for (String respId : commentId) {
            //先获取回复Id的回复评论Id数组
            ArrayList<String> commentRespId = commentDao.getCommentRespId(respId);
            //其回复评论调用此工具
            deleteRespComUtil(commentRespId,con,ps);
            //删除自己
            commentDao.deleteRespCom(respId,con,ps);
        }
        return ps;
    }

    /**
     *  从回复评论发起删除请求
     */
    @Override
    public String deleteRespCom(String cId) {
        String mes = "删除失败，正在为您加急抢修";
        JdbcUtil util = new JdbcUtil();
        //开启事务
        Connection con = util.getCon();
        PreparedStatement ps = null;
        try {
            //删除其下的回复评论
            deleteRespComUtil(commentDao.getCommentRespId(cId),con,ps);
            //删除自己
            commentDao.deleteRespCom(cId,con,ps);
            mes = "删除成功";
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            util.close();
        }
        return mes;
    }

    /**
     *  删除回复评论（被调用【博客评论删除】）
     */
    @Override
    public PreparedStatement deleteRespCom(String cId, Connection con, PreparedStatement ps) throws SQLException {
        //删除其下的回复评论
        deleteRespComUtil(commentDao.getCommentRespId(cId),con,ps);
        //删除自己
        commentDao.deleteRespCom(cId,con,ps);
        return ps;
    }

    /**
     *  从博客评论发起删除请求
     */
    @Override
    public String deleteCom(String cId) {
        String mes = "删除失败，正在为您加急抢修";
        JdbcUtil util = new JdbcUtil();
        //开启事务
        Connection con = util.getCon();
        PreparedStatement ps = null;
        try {
            //删除其下的回复评论
            deleteRespComUtil(commentDao.getCommentRespId(cId),con,ps);
            //删除自己
            commentDao.deleteCom(cId,con,ps);
            mes = "删除成功";
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            util.close();
        }
        return mes;
    }

    /**
     *  删除博客评论（被调用【博客删除】）
     */
    @Override
    public PreparedStatement deleteCom(String cId, Connection con, PreparedStatement ps) throws SQLException {
        //删除其下的回复评论
        deleteRespComUtil(commentDao.getCommentRespId(cId),con,ps);
        //删除自己
        commentDao.deleteCom(cId,con, ps);
        return ps;
    }

    /**
     *  删除博客
     */
    @Override
    public String deleteBlog(int bId) {
        String mes = "删除失败，正在为您加急抢修";
        JdbcUtil util = new JdbcUtil();
        //获取其下评论Id数组
        ArrayList<String> commentIdArrayList = commentDao.getCommentIdByBId(bId);
        //开启事务
        Connection con = util.getCon();
        PreparedStatement ps = null;
        try {
            for (String cId : commentIdArrayList) {
                deleteCom(cId,con,ps);
            }
            blogDao.deleteBlog(bId,con,ps);
            mes = "删除成功";
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            util.close();
        }
        return mes;
    }

    @Override
    public ArrayList<User> getUserAttn(int uId, int index) {
        ArrayList<Integer> attnIdList = userDao.queryAttnIdByUId(uId, index);
        ArrayList<User> attnUserList = new ArrayList<>();
        for (Integer attnId : attnIdList) {
            User attnUser = userDao.queryUser(attnId);
            attnUserList.add(attnUser);
        }
        return attnUserList;
    }

    @Override
    public ArrayList<Blog> getConBlog(int uId, int index) {
        ArrayList<Integer> bIdList = blogDao.queryBIdByUId(uId,index);
        ArrayList<Blog> blogArrayList = new ArrayList<>();
        for (Integer bId : bIdList) {
            blogArrayList.add(blogDao.queryBlog(bId));
        }
        return blogArrayList;
    }

    @Override
    public String updateReport(int uId, String title, String content, String imgUrl) {
        return userDao.updateReport(uId, title, content, imgUrl);
    }

    @Override
    public String sendFriendRequest(int reqId, String respUserName) {
        String mes = "发送失败，正在为您加急抢修";
        int respId = userDao.getUIdByName(respUserName);
        if(reqId == respId){
            return "不能添加自己为好友哦";
        }
        if(respId == -1){
            return "好友请发发送失败，没有该用户哦";
        }
        if(userMesDao.requestIsExist(reqId, respId)){
            mes = "好友请求已发送";
        }else {
            if(userMesDao.sendFriendRequest(reqId, respId)){
                mes = "好友请求发送成功";
            }
        }
        return mes;
    }

    @Override
    public ArrayList<UserMes> getUserMes(int respId) {
        return userMesDao.getUserMes(respId);
    }

    @Override
    public String rejectFriendRequest(int umId) {
        UserMes userMes = userMesDao.getUserMesByUMId(umId);
        return userMesDao.reject(userMes);
    }

    @Override
    public String readMes(int umId) {
        return userMesDao.deleteMes(umId);
    }

    @Override
    public String addFriend(int umId) {
        UserMes userMes = userMesDao.getUserMesByUMId(umId);
        String mes = "添加失败，正在为您加急抢修";
        JdbcUtil util = new JdbcUtil();

        //开启事务
        Connection con = util.getCon();
        PreparedStatement ps = null;
        try {
            userDao.addFriend(userMes, con, ps);
            userMesDao.deleteMes(umId, con, ps);
            userMesDao.addAcceptMes(userMes, con, ps);

            mes = "添加成功";
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            util.close();
        }
        return mes;
    }
}
