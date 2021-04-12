package csdn.dao;

import csdn.po.Blog;
import csdn.util.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BlogDaoImpl implements BlogDao {
    private JdbcUtil util = new JdbcUtil();
    PreparedStatement ps = null;
    ResultSet rs = null;
    @Override
    public int createBlog(Blog blog) {
        int result = 0;
        String sql = "insert into blog(uid,title,tag,b_content,is_pub,imgUrl)" + "values(?,?,?,?,?,?)";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,blog.getuId());
            ps.setString(2,blog.getTitle());
            ps.setString(3,blog.getTag());
            ps.setString(4,blog.getbContent());
            ps.setString(5,blog.getPublic());
            ps.setString(6,blog.getImgUrl());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }

    @Override
    public ArrayList<Blog> checkBlog(int uId) {
        int result;
        String sql = "select * from "
    }
}
