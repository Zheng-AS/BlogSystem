package csdn.dao;

public class DaoFactory {
    public static UserDao getUserDao(){ return new UserDaoImpl(); }

    public static BlogDao getBlogDao(){
        return new BlogDaoImpl();
    }

    public static CommentDao getCommentDao(){
        return new CommentDaoImpl();
    }

    public static AdministratorDao getAdminDao(){ return new AdminDaoImpl(); }
}
