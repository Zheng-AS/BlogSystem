package csdn.service;

import csdn.po.User;

import java.util.ArrayList;

public interface AdminService {
    //管理员登录功能
    boolean login(String userName, String password);

    //管理用户
    ArrayList<User> checkUser(int index);
}
