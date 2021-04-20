package csdn.dao;

import csdn.util.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
