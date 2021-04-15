package csdn.dao;

import csdn.po.User;

public interface UserDao {
    //新增用户
    int addUser(User user);

    //匹配用户名和姓名是否正确
    int selectUser(User user);

    //判断用户是否存在
    boolean isExist(String userName);

    //通过id查找返回用户信息
    User queryUser(int uid);

    //更新用户信息
    int update(User user);
}
