package csdn.dao;

import csdn.po.Blog;
import csdn.po.Comment;
import csdn.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BlogDaoImpl implements BlogDao {
    private JdbcUtil util = new JdbcUtil();
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;
    @Override
    public int createBlog(Blog blog) {
        int result = 0;
        String sql = "insert into blog(uid,title,tag,b_content,is_pub,img_url) values(?,?,?,?,?,?)";
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
                    "n_of_con desc, n_of_like desc";
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
    public ArrayList<Blog> checkBlog(int uId, int index) {
        ArrayList<Blog> blogArrayList = new ArrayList<>();
        String sql = "select u.uid, b.* " +
                "from " +
                    "user u " +
                "left join " +
                    "blog b " +
                "on " +
                    "u.uid = ? and u.uid = b.uid " +
                "having " +
                    "is_pub = '是' " +
                "order by " +
                    "n_of_con desc, n_of_like desc " +
                "limit ?, 6";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,uId);
            ps.setInt(2,index);
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

    @Override
    public boolean likeIsExist(int uId, int bId) {
        boolean result = false;
        String sql = "select * from great where uid = ? and bid = ?";
        ps =util.createStatement(sql);
        try {
            ps.setInt(1,uId);
            ps.setInt(2,bId);
            rs = ps.executeQuery();
            if (rs.next()){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return result;
    }

    @Override
    public String addLikeNum(int uId, int bId) {
        String result = "服务器出现错误，正在为您加急抢修";
        try {
            int count = 0;
            //开启事务
            con = util.getCon();

            String sql1 = "update blog set n_of_like = n_of_like +1 where bId = ?";
            ps = con.prepareStatement(sql1);
            ps.setInt(1,bId);
            count += ps.executeUpdate();

            String sql2 = "insert into great(uid, bid) values(?,?)";
            ps = con.prepareStatement(sql2);
            ps.setInt(1,uId);
            ps.setInt(2,bId);
            count += ps.executeUpdate();

            if (count == 2){
                result = "点赞成功";
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }

    @Override
    public String reduceLikeNum(int uId, int bId) {
        String result = "服务器出现错误，正在为您加急抢修";
        try {
            int count = 0;
            //开启事务
            con = util.getCon();

            String sql1 = "update blog set n_of_like = n_of_like -1 where bId = ?";
            ps = con.prepareStatement(sql1);
            ps.setInt(1,bId);
            count += ps.executeUpdate();

            String sql2 = "delete from great where uid = ? and bid = ?";
            ps = con.prepareStatement(sql2);
            ps.setInt(1,uId);
            ps.setInt(2,bId);
            count += ps.executeUpdate();

            if (count == 2){
                result = "取消点赞成功";
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }

    @Override
    public String addCon(int uId, int bId) {
        String result = "收藏失败，正在为您加急抢修.";
        try {
            int count = 0;
            //开启事务
            con = util.getCon();

            String sql1 = "update blog set n_of_con = n_of_con +1 where bId = ?";
            ps = con.prepareStatement(sql1);
            ps.setInt(1,bId);
            count += ps.executeUpdate();

            String sql2 = "insert into user_con(uid,bid) values (?,?)";
            ps = con.prepareStatement(sql2);
            ps.setInt(1,uId);
            ps.setInt(2,bId);
            count += ps.executeUpdate();

            if (count == 2){
                result = "收藏成功";
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }

    @Override
    public String cancelCon(int uId, int bId) {
        String result = "操作失败，正在为您加急抢修.";
        try {
            int count = 0;
            //开启事务
            con = util.getCon();

            String sql1 = "update blog set n_of_con = n_of_con -1 where bId = ?";
            ps = con.prepareStatement(sql1);
            ps.setInt(1,bId);
            count += ps.executeUpdate();

            String sql2 = "delete from user_con where uid = ? and bid = ?";
            ps = con.prepareStatement(sql2);
            ps.setInt(1,uId);
            ps.setInt(2,bId);
            count += ps.executeUpdate();

            if (count == 2){
                result = "收藏成功";
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }

    @Override
    public ArrayList<Integer> queryBIdByUId(int uId, int index) {
        ArrayList<Integer> bIdList = new ArrayList<>();
        String sql = "select * from user_con where uid = ? limit ?, 6";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1, uId);
            ps.setInt(2,index);
            rs = ps.executeQuery();
            if(rs.next()){
                bIdList.add(rs.getInt("bid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bIdList;
    }

    @Override
    public PreparedStatement deleteBlog(int bId, Connection con, PreparedStatement ps) throws SQLException {
        String sql1 = "delete from great where bId = ?";
        ps = con.prepareStatement(sql1);
        ps.setInt(1, bId);
        ps.executeUpdate();

        String sql2 = "delete from user_con where bid = ?";
        ps = con.prepareStatement(sql2);
        ps.setInt(1,bId);
        ps.executeUpdate();

        String sql3 = "delete from blog where bid = ?";
        ps = con.prepareStatement(sql3);
        ps.setInt(1,bId);
        ps.executeUpdate();

        return ps;
    }

    @Override
    public ArrayList<Blog> findAllBlog(int index) {
        ArrayList<Blog> blogArrayList = new ArrayList<>();
        int bId, uId, nOfLike, nOfCon;
        String title, tag;
        String sql = "select * from blog where is_pub = '是' order by n_of_con desc, n_of_like desc limit ?, 6";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,index);
            rs = ps.executeQuery();
            while (rs.next()){
                bId = rs.getInt("bid");
                uId = rs.getInt("uid");
                nOfCon = rs.getInt("n_of_con");
                nOfLike = rs.getInt("n_of_like");
                title = rs.getString("title");
                tag = rs.getString("tag");
                blogArrayList.add(new Blog(bId, uId, null, tag, nOfLike, nOfCon, null, null, title, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return blogArrayList;
    }
}
