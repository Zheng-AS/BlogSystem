package csdn.service;

public class ServiceFactory {
    public static UserService getUserService(){
        return new UserServiceImpl();
    }
}
