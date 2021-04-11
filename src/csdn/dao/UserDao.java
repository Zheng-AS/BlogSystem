package csdn.dao;

import csdn.po.User;

public interface UserDao {
    //新增用户
    int addUser(User user);

    int selectUser(User user);

    boolean isExist(String userName);

    User queryUser(int uid);

    int update(User user);
}
