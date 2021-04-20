package csdn.dao;

public interface AdministratorDao {
    //查询用户名和密码是否匹配
    boolean isExist(String userName, String password);
}
