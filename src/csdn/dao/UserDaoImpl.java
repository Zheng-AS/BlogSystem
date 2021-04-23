package csdn.dao;

import csdn.po.User;
import csdn.po.UserMes;
import csdn.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    private JdbcUtil util = new JdbcUtil();
    PreparedStatement ps = null;
    ResultSet rs = null;
    @Override
    public int addUser(User user) {
        int result = 0;
        String sql = "insert into user(username,password) values(?,?)";
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
                String able = rs.getString("able");
                System.out.println(able);
                if(able.equals("是")){
                    result = -1;
                }else {
                    result = rs.getInt("uid");
                }
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
        return new User(uid,userName,password);
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
    public int getUIdByName(String userName) {
        int uId = -1;
        String sql = "select * from user where username = ?";
        ps = util.createStatement(sql);
        try {
            ps.setString(1,userName);
            rs = ps.executeQuery();
            if(rs.next()){
                uId = rs.getInt("uid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return uId;
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

    @Override
    public ArrayList<Integer> queryAttnIdByUId(int uId, int index) {
        ArrayList<Integer> attnIdList = new ArrayList<>();
        String sql = "select * from user_attn where uid = ? limit ?, 6 ";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,uId);
            ps.setInt(2,index);
            rs = ps.executeQuery();
            while (rs.next()){
                attnIdList.add(rs.getInt("aid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return attnIdList;
    }

    @Override
    public String updateReport(int uId, String title, String content, String imgUrl) {
        String mes = "发布失败";
        String sql = "insert into report(uid, title, content, img_url) values(?,?,?,?)";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1, uId);
            ps.setString(2, title);
            ps.setString(3, content);
            ps.setString(4, imgUrl);
            ps.executeUpdate();
            mes = "发布成功";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return mes;
    }

    @Override
    public ArrayList<User> findAllUser(int index) {
        ArrayList<User> users = new ArrayList<>();
        String sql = "select * from user order by uid asc limit ?, 6";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,index);
            rs = ps.executeQuery();
            while (rs.next()){
                users.add(new User(rs.getInt("uid"), rs.getString("username"), rs.getString("password"), rs.getString("able")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return users;
    }

    @Override
    public String banUser(int uId) {
        String mes = "封禁失败，服务区繁忙";
        String sql = "update user set able = '是' where uid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,uId);
            ps.executeUpdate();
            mes = "封禁成功";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return mes;
    }

    @Override
    public String noBanUser(int uId) {
        String mes = "封禁失败，服务区繁忙";
        String sql = "update user set able = '否' where uid = ?";
        ps = util.createStatement(sql);
        try {
            ps.setInt(1,uId);
            ps.executeUpdate();
            mes = "封禁成功";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return mes;
    }

    @Override
    public PreparedStatement addFriend(UserMes userMes, Connection con, PreparedStatement ps) throws SQLException {
        String sql1 = "insert into user_friend (uid1, uid2) values (?, ?)";
        ps = con.prepareStatement(sql1);
        ps.setInt(1, userMes.getReqId());
        ps.setInt(2, userMes.getRespId());
        ps.executeUpdate();

        String sql2 = "insert into user_friend (uid1, uid2) values (?, ?)";
        ps = con.prepareStatement(sql2);
        ps.setInt(1, userMes.getRespId());
        ps.setInt(2, userMes.getReqId());
        ps.executeUpdate();

        return ps;
    }
}
