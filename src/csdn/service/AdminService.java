package csdn.service;

import csdn.po.Blog;
import csdn.po.Comment;
import csdn.po.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface AdminService {
    //管理员登录功能
    boolean login(String userName, String password);

    //管理用户
    ArrayList<User> checkUser(int index);

    //封禁用户
    String banUser(int uId);

    //解禁用户
    String noBanUser(int uId);

    //获取用户的博客信息
    ArrayList<Blog> getUserBlogMes(int uId, int index);

    //删除回复评论（从回复评论发起删除请求）
    String deleteRespCom(String cId);

    //删除回复评论（被调用【博客评论删除】）
    PreparedStatement deleteRespCom(String cId, Connection con, PreparedStatement ps) throws SQLException;

    //删除评论（从博客评论处发起删除请求）
    String deleteCom(String cId);

    //删除博客评论（被调用【博客删除】）
    PreparedStatement deleteCom(String cId, Connection con, PreparedStatement ps) throws SQLException;

    //删除博客
    String deleteBlog(int bId);

    //获取评论信息通过用户Id
    ArrayList<Comment> getUserCom(int uId, int index);

    //删除评论（回复评论/博客评论）
    String deleteComment(String cId);

    //查找所有博客（带分页）
    ArrayList<Blog> findAllBlog(int index);

    //查找所有评论（带分页）
    ArrayList<Comment> findAllComment(int index);

    //查找所有举报信息（带分页）
    ArrayList<Blog> findAllReport(int index);

    //根据Id查找举报信息
    Blog findReportByRId(int rId);
}
