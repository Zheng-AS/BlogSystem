package csdn.service;

import csdn.dao.*;
import csdn.po.Blog;
import csdn.po.Comment;
import csdn.po.User;
import csdn.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminServiceImpl implements AdminService {
    private AdministratorDao adminDao = DaoFactory.getAdminDao();
    private BlogDao blogDao = DaoFactory.getBlogDao();
    private CommentDao commentDao = DaoFactory.getCommentDao();
    private UserDao userDao = DaoFactory.getUserDao();

    @Override
    public boolean login(String userName, String password) {
        return adminDao.isExist(userName, password);
    }

    @Override
    public ArrayList<User> checkUser(int index) {
        return userDao.findAllUser(index);
    }

    @Override
    public String banUser(int uId) {
        return userDao.banUser(uId);
    }

    @Override
    public String noBanUser(int uId) {
        return userDao.noBanUser(uId);
    }

    @Override
    public ArrayList<Blog> getUserBlogMes(int uId, int index) {
        return blogDao.checkBlog(uId, index);
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
    public ArrayList<Comment> getUserCom(int uId, int index) {
        ArrayList<Comment> commentArrayList = commentDao.getCommentIdByUId(uId, index);
        for (Comment comment : commentArrayList) {
            comment.setUserName(userDao.getNameByUId(comment.getuId()));
        }
        return commentArrayList;
    }

    @Override
    public String deleteComment(String cId) {
        String mes = "删除失败";
        if(commentDao.isRespCom(cId)){
            mes = deleteRespCom(cId);
        }else {
            mes = deleteCom(cId);
        }
        return mes;
    }

    @Override
    public ArrayList<Blog> findAllBlog(int index) {
        ArrayList<Blog> blogArrayList =  blogDao.findAllBlog(index);
        for (Blog blog : blogArrayList) {
            blog.setbContent(userDao.getNameByUId(blog.getuId()));
        }
        return blogArrayList;
    }
}
