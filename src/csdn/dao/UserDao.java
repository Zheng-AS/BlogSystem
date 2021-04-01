package csdn.dao;

import csdn.po.User;

public interface UserDao {
    //新增用户
    int addUser(User user);

    boolean selectUser(User user);

    boolean isExist(String userName);
}
