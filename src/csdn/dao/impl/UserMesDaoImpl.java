package csdn.dao.impl;

import csdn.dao.UserMesDao;
import csdn.po.UserMes;
import csdn.util.IDUtil;
import csdn.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMesDaoImpl implements UserMesDao {
    private JdbcUtil util = new JdbcUtil();
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;

    @Override
    public boolean sendFriendRequest(int reqId, int respId) {
        boolean result = false;
        String sql = "insert into user_mes (req_id,resp_id,type) values (?,?,?)";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,reqId);
            ps.setInt(2,respId);
            ps.setString(3,"request");
            ps.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }

    @Override
    public boolean requestIsExist(int reqId, int respId) {
        boolean result = false;
        String type = "request";
        String sql = "select * from user_mes where req_id = ? and resp_id = ? and type = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,reqId);
            ps.setInt(2,respId);
            ps.setString(3,type);
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
    public ArrayList<UserMes> getUserMes(int respId) {
        ArrayList<UserMes> userMesArrayList = new ArrayList<>();
        String sql = "select * from user_mes where resp_id = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1, respId);
            rs = ps.executeQuery();
            while (rs.next()){
                userMesArrayList.add(new UserMes(rs.getInt("umid"), rs.getInt("req_id"), respId, rs.getString("type")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return userMesArrayList;
    }

    @Override
    public UserMes getUserMesByUMId(int umId) {
        UserMes userMes = new UserMes();
        String sql = "select * from user_mes where umid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1, umId);
            rs = ps.executeQuery();
            if(rs.next()){
                userMes.setUmId(umId);
                userMes.setReqId(rs.getInt("req_id"));
                userMes.setRespId(rs.getInt("resp_id"));
                userMes.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return userMes;
    }

    @Override
    public String reject(UserMes userMes) {
        String mes = "操作失败，正在为您加急抢修";
        try {
            int count = 0;
            //开启事务
            con = util.getCon();

            String sql1 = "delete from user_mes where umid = ?";
            ps = con.prepareStatement(sql1);
            ps.setInt(1, userMes.getUmId());
            count += ps.executeUpdate();

            String sql2 = "insert into user_mes(req_id, resp_id, type) values(?,?,?)";
            ps = con.prepareStatement(sql2);
            ps.setInt(1,userMes.getRespId());
            ps.setInt(2,userMes.getReqId());
            ps.setString(3,"reject");
            count += ps.executeUpdate();
            if(count == 2){
                mes = "操作成功";
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            util.close();
        }
        return mes;
    }

    @Override
    public String deleteMes(int umId) {
        String mes = "服务器出现故障";
        String sql = "delete from user_mes where umid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1, umId);
            ps.executeUpdate();
            mes = "操作成功";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return mes;
    }

    @Override
    public PreparedStatement deleteMes(int umId, Connection con, PreparedStatement ps) throws SQLException {
        String sql1 = "delete from user_mes where umid = ?";
        ps = con.prepareStatement(sql1);
        ps.setInt(1, umId);
        ps.executeUpdate();

        return ps;
    }

    @Override
    public PreparedStatement addAcceptMes(UserMes userMes, Connection con, PreparedStatement ps) throws SQLException {
        String sql1 = "insert into user_mes (req_id, resp_id, type) values (?,?,?)";
        ps = con.prepareStatement(sql1);
        ps.setInt(1, userMes.getRespId());
        ps.setInt(2, userMes.getReqId());
        ps.setString(3, "accept");
        ps.execute();

        return ps;
    }

    @Override
    public PreparedStatement addDeleteMes(int reqId, int respId, Connection con, PreparedStatement ps) throws SQLException {
        String sql1 = "insert into user_mes (req_id, resp_id, type) values (?,?,?)";
        ps = con.prepareStatement(sql1);
        ps.setInt(1, reqId);
        ps.setInt(2, respId);
        ps.setString(3, "delete");
        ps.execute();

        return ps;
    }
}
