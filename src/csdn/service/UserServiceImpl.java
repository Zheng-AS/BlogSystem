package csdn.service;

import csdn.dao.DaoFactory;
import csdn.dao.UserDao;
import csdn.po.User;

public class UserServiceImpl implements UserService {
    @Override
    public boolean login(String userName, String password){
        UserDao userDao = DaoFactory.getUserDao();
        return userDao.selectUser(new User(userName,password));
    }
}
