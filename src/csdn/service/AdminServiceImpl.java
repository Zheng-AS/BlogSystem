package csdn.service;

import csdn.dao.*;
import csdn.po.User;

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
}
