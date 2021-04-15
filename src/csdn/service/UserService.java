package csdn.service;

import csdn.po.Blog;
import csdn.po.User;

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
}
