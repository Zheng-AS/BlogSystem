package csdn.dao;

import csdn.util.IDUtil;
import csdn.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentDaoImpl implements CommentDao {
    private JdbcUtil util = new JdbcUtil();
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;

    @Override
    public String addComment(int uId, int bId, String content){
        String mes = "发布评论失败";
        try {
            int count = 0;
            String cId = IDUtil.getCId();
            //开启事务
            con = util.getCon();

            String sql1 = "insert into comment(cid, uid, content) values(?,?,?)";
            ps = con.prepareStatement(sql1);
            ps.setString(1, cId);
            ps.setInt(2,uId);
            ps.setString(3,content);
            count += ps.executeUpdate();

            String sql2 = "insert into blog_comment(bid,cid) values(?,?)";
            ps = con.prepareStatement(sql2);
            ps.setInt(1,bId);
            ps.setString(2,cId);
            count += ps.executeUpdate();
            if(count == 2){
                mes = "发布评论成功";
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
        return mes;
    }
}
