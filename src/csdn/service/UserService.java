package csdn.service;

public interface UserService {
    boolean login(String userName, String password);

    int register(String userName, String password);
}
