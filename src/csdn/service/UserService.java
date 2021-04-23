package csdn.service;

import csdn.po.Blog;
import csdn.po.Comment;
import csdn.po.User;
import csdn.po.UserMes;

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

    //管理博客功能(自己查看自己的)
    ArrayList<Blog> checkBlog(int uId);

    //管理博客功能(带分页,被人查看)
    ArrayList<Blog> checkBlog(int uId, int index);

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

    //获取用户的关注用户
    ArrayList<User> getUserAttn(int uId, int index);

    //获取用户收藏的博客
    ArrayList<Blog> getConBlog(int uId, int index);

    //上传举报信息
    String updateReport(int uId, String title, String content, String imgUrl);

    //发送好友请求
    String sendFriendRequest(int reqId, String respUserName);

    //获取用户信息
    ArrayList<UserMes> getUserMes(int respId);

    //拒绝好友请求
    String rejectFriendRequest(int umId);
}
