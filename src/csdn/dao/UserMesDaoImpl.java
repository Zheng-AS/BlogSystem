package csdn.dao;

import csdn.po.UserMes;
import csdn.util.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMesDaoImpl implements UserMesDao {
    private JdbcUtil util = new JdbcUtil();
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public boolean sendFriendRequest(int reqId, int respId) {
        boolean result = false;
        String sql = "insert into user_mes (req_id,resp_id,type) values (?,?,?)";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,reqId);
            ps.setInt(2,respId);
            ps.setString(3,"request");
            result = ps.execute();
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
}
