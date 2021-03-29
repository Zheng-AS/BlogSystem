package csdn.dao;

import csdn.po.User;
import csdn.util.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private JdbcUtil util = new JdbcUtil();
    PreparedStatement ps = null;
    ResultSet rs = null;
    @Override
    public int addUser(User user) {
        int result = 0;
        String sql = "insert into user(username,password)" + "values(?,?)";
        ps = util.createStatement(sql);
        try {
            ps.setString(1,user.getUserName());
            ps.setString(2,user.getPassword());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }

    @Override
    public boolean selectUser(User user) {
        boolean result = false;
        String sql = "select * from user where username = ? and password =?";
        ps = util.createStatement(sql);
        try {
            ps.setString(1,user.getUserName());
            ps.setString(2,user.getPassword());
            rs = ps.executeQuery();
            if(rs.next()){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }
}
