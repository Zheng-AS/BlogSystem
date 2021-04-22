package csdn.dao;

import csdn.po.Blog;

import java.util.ArrayList;

public interface AdministratorDao {
    //查询用户名和密码是否匹配
    boolean isExist(String userName, String password);

    //查询所有的举报信息
    ArrayList<Blog> findAllReport(int index);

    //根据rId查找举报信息
    Blog findRepByRId(int rId);

    //删除举报信息
    String deleteReport(int rId);
}
