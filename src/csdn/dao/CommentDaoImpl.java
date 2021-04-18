package csdn.dao;

import csdn.po.Comment;
import csdn.util.IDUtil;
import csdn.util.JdbcUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

            String sql1 = "insert into comment(cid, uid, content, time) values(?,?,?,?)";
            ps = con.prepareStatement(sql1);
            ps.setString(1, cId);
            ps.setInt(2,uId);
            ps.setString(3,content);
            ps.setString(4,IDUtil.getTime());
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

    @Override
    public ArrayList<Comment> getCommentByBId(int bId) {
        ArrayList<Comment> commentArrayList = new ArrayList<>();
        String sql = "select " +
                    "b.cid, b.bid, c.* " +
                "from " +
                    "blog_comment b " +
                "join " +
                    "comment c " +
                "on " +
                    "b.cid = c.cid " +
                "having " +
                    "bid = ? " +
                "order by " +
                    "c.time asc";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,bId);
            rs = ps.executeQuery();
            while (rs.next()){
                String cId = rs.getString("cid");
                String content = rs.getString("content");
                int uId = rs.getInt("uid");
                String time = rs.getString("time");
                Comment comment = new Comment(cId,content,uId,time);
                commentArrayList.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return commentArrayList;
    }

    @Override
    public Comment getCommentByCId(String cId) {
        Comment comment = new Comment();
        String sql = "select * from comment where cid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setString(1,cId);
            rs = ps.executeQuery();
            if(rs.next()){
                comment.setcId(cId);
                comment.setContent(rs.getString("content"));
                comment.setuId(rs.getInt("uid"));
                comment.setTime(rs.getString("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return comment;
    }

    @Override
    public ArrayList<String> getCommentRespId(String cId) {
        ArrayList<String> respIdArrayList = new ArrayList<>();
        String respId = "00000000000";
        String sql = "select " +
                    "a.cid, a.time, c.cid, c.rid " +
                "from " +
                    "comment a " +
                "left join " +
                    "comment_resp c " +
                "on " +
                    "a.cid = c.cid " +
                "having " +
                    "c.cid = ? " +
                "order by " +
                    "a.time asc";
        ps = util.createStatement(sql);
        try {
            ps.setString(1,cId);
            rs = ps.executeQuery();
            while (rs.next()){
                respIdArrayList.add(rs.getString("rid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return respIdArrayList;
    }

    @Test
    public void test1(){
        ArrayList<Comment> commentByBId = getCommentByBId(1);
        for (Comment comment : commentByBId) {
            System.out.println(comment);
        }
    }
}
