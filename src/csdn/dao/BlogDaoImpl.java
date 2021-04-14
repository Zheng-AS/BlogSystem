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
        String sql = "insert into blog(uid,title,tag,b_content,is_pub,img_url)" + "values(?,?,?,?,?,?)";
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
        ArrayList<Blog> blogArrayList = new ArrayList<>();
        String sql = "select u.uid, b.* " +
                "from " +
                    "user u " +
                "left join " +
                    "blog b " +
                "on " +
                    "u.uid = ? " +
                "having " +
                    "u.uid = b.uid " +
                "order by " +
                    "n_of_con, n_of_like desc";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,uId);
            rs = ps.executeQuery();
            while (rs.next()){
                int bId = rs.getInt("bid");
                String tag = rs.getString("tag");
                int nOfLike = rs.getInt("n_of_like");
                int nOfCon = rs.getInt("n_of_con");
                String isPublic = rs.getString("is_pub");
                String bContent = rs.getString("b_content");
                String title = rs.getString("title");
                String imgUrl = rs.getString("img_url");
                Blog blog = new Blog(bId,uId,bContent,tag,nOfLike,nOfCon,isPublic,null,title,imgUrl);
                blogArrayList.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return blogArrayList;
    }

    @Override
    public Blog queryBlog(int bId) {
        Blog blog = new Blog();
        String sql = "select * from blog where bid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,bId);
            rs = ps.executeQuery();
            if(rs.next()){
                blog.setuId(rs.getInt("uid"));
                blog.setTag(rs.getString("tag"));
                blog.setnOfCon(rs.getInt("n_of_con"));
                blog.setnOfLike(rs.getInt("n_of_like"));
                blog.setPublic(rs.getString("is_pub"));
                blog.setbContent(rs.getString("b_content"));
                blog.setTitle(rs.getString("title"));
                blog.setImgUrl(rs.getString("img_url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return blog;
    }

    @Override
    public int updateBlog(Blog blog) {
        int result = 0;
        String sql = "update blog set title = ?, tag = ?, b_content = ?, img_url = ?, is_pub = ? where bid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setString(1,blog.getTitle());
            ps.setString(2,blog.getTag());
            ps.setString(3,blog.getbContent());
            ps.setString(4,blog.getImgUrl());
            ps.setString(5,blog.getPublic());
            ps.setInt(6,blog.getbId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<Blog> findBlog(String sql, ArrayList<String> arrayList) {
        ArrayList<Blog> blogArrayList = new ArrayList<>();
        ps = util.createStatement(sql);
        int i;
        try {
            for(i = 0; i < arrayList.size(); i++){
                ps.setString(i+1,arrayList.get(i));
            }
            rs =  ps.executeQuery();
            while (rs.next()){
                int bId = rs.getInt("bid");
                String tag = rs.getString("tag");
                int nOfLike = rs.getInt("n_of_like");
                int nOfCon = rs.getInt("n_of_con");
                String isPublic = rs.getString("is_pub");
                String bContent = rs.getString("b_content");
                String title = rs.getString("title");
                String imgUrl = rs.getString("img_url");
                int uId = rs.getInt("uId");
                Blog blog = new Blog(bId,uId,bContent,tag,nOfLike,nOfCon,isPublic,null,title,imgUrl);
                blogArrayList.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return blogArrayList;
    }
}
