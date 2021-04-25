package csdn.dao;

import csdn.dao.impl.*;

public class DaoFactory {
    public static UserDao getUserDao(){ return new UserDaoImpl(); }

    public static BlogDao getBlogDao(){
        return new BlogDaoImpl();
    }

    public static CommentDao getCommentDao(){
        return new CommentDaoImpl();
    }

    public static AdministratorDao getAdminDao(){ return new AdminDaoImpl(); }

    public static UserMesDao getUserMesDao(){ return new UserMesDaoImpl(); }

    public static UserTalkMesDao getUserTalkMesDao(){ return new UserTalkMesDaoImpl(); }
}
