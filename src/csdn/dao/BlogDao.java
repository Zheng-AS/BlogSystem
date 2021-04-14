package csdn.dao;

import csdn.po.Blog;

import java.util.ArrayList;

public interface BlogDao {
    int createBlog(Blog blog);

    ArrayList<Blog> checkBlog(int uId);

    Blog queryBlog(int bId);

    int updateBlog(Blog blog);
}
