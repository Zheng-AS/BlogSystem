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
    public int selectUser(User user) {
        int result = 0;
        String sql = "select * from user where username = ? and password =?";
        ps = util.createStatement(sql);
        try {
            ps.setString(1,user.getUserName());
            ps.setString(2,user.getPassword());
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("uid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return result;
    }

    @Override
    public boolean isExist(String userName) {
        boolean result = false;
        String sql = "select * from user where username = ?";
        ps = util.createStatement(sql);
        try {
            ps.setString(1,userName);
            rs = ps.executeQuery();
            if(rs.next()){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return false;
    }

    @Override
    public User queryUser(int uid) {
        String userName = null, password = null;
        String sql = "select * from user where uid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,uid);
            rs = ps.executeQuery();
            if(rs.next()){
                userName = rs.getString("username");
                password = rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return new User(userName,password);
    }

    @Override
    public int update(User user) {
        String userName = user.getUserName();
        String password = user.getPassword();
        int uid = user.getuId(),result = 0;
        String sql = "update user set username = ?, password = ? where uid = ?";
        ps = util.createStatement(sql);
        try{
            ps.setString(1,userName);
            ps.setString(2,password);
            ps.setInt(3,uid);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }

    @Override
    public boolean conIsExist(int uId, int bId) {
        boolean result = false;
        String sql = "select * from user_con where uid = ? and bid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,uId);
            ps.setInt(2,bId);
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
    public String getNameByUId(int uId) {
        String userName = "";
        String sql = "select * from user where uid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,uId);
            rs = ps.executeQuery();
            if(rs.next()){
                userName = rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return userName;
    }

    @Override
    public boolean attnIsExist(int uId, int aId) {
        boolean result = false;
        String sql = "select * from user_attn where uid = ? and aid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,uId);
            ps.setInt(2,aId);
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
    public String addAttn(int uId, int aId) {
        String result = "关注失败，正在为您加急抢修";
        String sql = "insert into user_attn (uid, aid) values (?,?)";
        ps =util.createStatement(sql);
        try {
            ps.setInt(1,uId);
            ps.setInt(2,aId);
            ps.executeUpdate();
            result = "关注成功";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return  result;
    }

    @Override
    public String cancelAttn(int uId, int aId) {
        String result = "取消关注失败，正在为您加急抢修";
        String sql = "delete from user_attn where uid =? and aid = ?";
        ps =util.createStatement(sql);
        try {
            ps.setInt(1,uId);
            ps.setInt(2,aId);
            ps.executeUpdate();
            result = "取消关注成功";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return  result;
    }
}
