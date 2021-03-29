package csdn.dao;

public class DaoFactory {
    public static UserDao getUserDao(){
        return new UserDaoImpl();
    }
}
