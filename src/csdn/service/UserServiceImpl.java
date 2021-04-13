package csdn.service;

import csdn.dao.BlogDao;
import csdn.dao.DaoFactory;
import csdn.dao.UserDao;
import csdn.po.Blog;
import csdn.po.User;

import java.util.ArrayList;

public class UserServiceImpl implements UserService {
    private UserDao userDao = DaoFactory.getUserDao();
    private BlogDao blogDao = DaoFactory.getBlogDao();
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
}
