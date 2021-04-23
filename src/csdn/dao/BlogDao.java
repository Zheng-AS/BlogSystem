package csdn.dao;

import csdn.po.Blog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BlogDao {
    //创建博客
    int createBlog(Blog blog);

    //通过用户id查找返回其下所有博客信息
    ArrayList<Blog> checkBlog(int uId);

    //通过用户id查找返回其下所有博客信息(带分页)
    ArrayList<Blog> checkBlog(int uId, int index);

    //通过博客id查找返回博客信息
    Blog queryBlog(int bId);

    //更新博客
    int updateBlog(Blog blog);

    //动态查询他人博客信息
    ArrayList<Blog> findBlog(String sql, ArrayList<String> arrayList);

    //查询点赞表中有无记录
    boolean likeIsExist(int uId, int bId);

    //点赞数加1
    String addLikeNum(int uId, int bId);

    //点赞数减1
    String reduceLikeNum(int uId, int bId);

    //收藏者加1
    String addCon(int uId, int bId);

    //收藏者减1
    String cancelCon(int uId, int bId);

    //根据用户ID查找其收藏博客ID
    ArrayList<Integer> queryBIdByUId(int uId, int index);

    //博客删除
    PreparedStatement deleteBlog(int bId, Connection con, PreparedStatement ps) throws SQLException;

    //查找所有博客（带分页）
    ArrayList<Blog> findAllBlog(int index);
}
