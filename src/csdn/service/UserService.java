package csdn.service;

import csdn.po.Blog;
import csdn.po.User;

import java.util.ArrayList;

public interface UserService {
    int login(String userName, String password);

    int register(String userName, String password);

    boolean updateMes(User user);

    int createBlog(Blog blog);

    ArrayList<Blog> checkBlog(int uId);

    Blog viewBlog(int bId);

    int updateBlog(Blog blog);

    ArrayList<Blog> findBlog(String userName, String tag, String title, String index);
}
