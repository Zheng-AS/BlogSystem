package csdn.dao;

import csdn.po.Blog;
import csdn.util.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDaoImpl implements AdministratorDao {
    private JdbcUtil util = new JdbcUtil();
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    @Override
    public boolean isExist(String userName, String password) {
        boolean result = false;
        String sql = "select * from admin where username = ? and password = ?";
        ps = util.createStatement(sql);
        try {
            ps.setString(1, userName);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()){
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
    public ArrayList<Blog> findAllReport(int index) {
        ArrayList<Blog> reportArrayList= new ArrayList<>();
        String sql = "select * from report limit ?, 6";
        int rId, uId;
        String title, content, imgUrl;
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,index);
            rs = ps.executeQuery();
            while (rs.next()){
                rId = rs.getInt("rid");
                uId = rs.getInt("uid");
                title = rs.getString("title");
                content = rs.getString("content");
                imgUrl = rs.getString("img_url");
                reportArrayList.add(new Blog(rId,uId,content,null,null,null,null,null,title,imgUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return reportArrayList;
    }

    @Override
    public Blog findRepByRId(int rId) {
        Blog report = new Blog();
        String sql = "select * from report where rid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,rId);
            rs = ps.executeQuery();
            if(rs.next()) {
                report.setuId(rs.getInt("uid"));
                report.setTitle(rs.getString("title"));
                report.setImgUrl(rs.getString("img_url"));
                report.setbContent(rs.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return report;
    }

    @Override
    public String deleteReport(int rId) {
        String mes = "删除失败，正在为您加急抢修";
        String sql = "delete from report where rid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,rId);
            ps.execute();
            mes = "删除成功";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return mes;
    }
}
