package csdn.dao;

import csdn.po.Blog;

import java.util.ArrayList;

public interface BlogDao {
    //创建博客
    int createBlog(Blog blog);

    //通过用户id查找返回其下所有博客信息
    ArrayList<Blog> checkBlog(int uId);

    //通过博客id查找返回博客信息
    Blog queryBlog(int bId);

    //更新博客
    int updateBlog(Blog blog);

    //动态查询他人博客信息
    ArrayList<Blog> findBlog(String sql, ArrayList<String> arrayList);
}
