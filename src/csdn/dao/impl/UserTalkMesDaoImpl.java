package csdn.dao.impl;

import csdn.dao.UserTalkMesDao;
import csdn.po.UserTalkMes;
import csdn.util.IDUtil;
import csdn.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserTalkMesDaoImpl  implements UserTalkMesDao {
    private JdbcUtil util = new JdbcUtil();
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;

    @Override
    public String addTalkMes(int reqId, int uuId, String content) {
        String mes = "发送失败";
        String sql = "insert into user_talk(req_id, time, uuid, content) values (?,?,?,?)";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,reqId);
            ps.setString(2, IDUtil.getTime());
            ps.setInt(3,uuId);
            ps.setString(4,content);
            ps.execute();
            mes = "发送成功";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return mes;
    }

    @Override
    public ArrayList<UserTalkMes> getTalkMes(ArrayList<Integer> arrayList) {
        ArrayList<UserTalkMes> userTalkMes = new ArrayList<>();
        String sql = "select * from user_talk where uuid = ? or uuid = ? order by time asc";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1, arrayList.get(0));
            ps.setInt(2, arrayList.get(1));
            rs = ps.executeQuery();
            while (rs.next()){
                UserTalkMes userTalkMes1 = new UserTalkMes();
                userTalkMes1.setUuId(rs.getInt("uuid"));
                userTalkMes1.setUtId(rs.getInt("utid"));
                userTalkMes1.setContent(rs.getString("content"));
                userTalkMes1.setTime(rs.getString("time"));
                userTalkMes1.setReqId(rs.getInt("req_id"));
                userTalkMes.add(userTalkMes1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return userTalkMes;
    }
}
