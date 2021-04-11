package csdn.service;

import csdn.dao.DaoFactory;
import csdn.dao.UserDao;
import csdn.po.User;

public class UserServiceImpl implements UserService {
    private UserDao userDao = DaoFactory.getUserDao();
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
        System.out.println(15615);
        if (userDao.update(user) == 1) {
            System.out.println(122);
            result = true;
        }
        return result;
    }
}
