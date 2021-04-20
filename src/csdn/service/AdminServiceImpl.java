package csdn.service;

import csdn.dao.*;

public class AdminServiceImpl implements AdminService {
    private AdministratorDao adminDao = DaoFactory.getAdminDao();
    private BlogDao blogDao = DaoFactory.getBlogDao();
    private CommentDao commentDao = DaoFactory.getCommentDao();

    @Override
    public boolean login(String userName, String password) {
        return adminDao.isExist(userName, password);
    }
}
