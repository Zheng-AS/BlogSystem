package csdn.service;

import csdn.po.Blog;
import csdn.po.User;

public interface UserService {
    int login(String userName, String password);

    int register(String userName, String password);

    boolean updateMes(User user);

    int createBlog(Blog blog);
}
