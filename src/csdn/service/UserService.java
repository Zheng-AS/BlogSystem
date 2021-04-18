package csdn.service;

import csdn.po.Blog;
import csdn.po.Comment;
import csdn.po.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserService {
    //登录功能
    int login(String userName, String password);

    //注册功能
    int register(String userName, String password);

    //更新个人信息功能
    boolean updateMes(User user);

    //创建博客功能
    int createBlog(Blog blog);

    //管理博客功能
    ArrayList<Blog> checkBlog(int uId);

    //查看博客功能
    Blog viewBlog(int bId);

    //更新博客功能
    int updateBlog(Blog blog);

    //查看他人博客功能
    ArrayList<Blog> findBlog(String userName, String tag, String title, String index);

    //点赞功能
    String changeLikeNum(int uId, int bId);

    //收藏功能
    String changeConNum(int uId, int bId);

    //获取作者姓名
    String getAuthorName(int uId);

    //更改用户关注
    String changeUserAttn(int uId, int aId);

    //用户发表评论
    String addComment(int uId, int bId, String content);

    //获取博客下的评论
    ArrayList<Comment> getComment(int bId, int index);

    //用户发表回复评论
    String addRespCom(String cId, String content, int uId);

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
}
