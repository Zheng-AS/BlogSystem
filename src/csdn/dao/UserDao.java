package csdn.dao;

import csdn.po.User;
import csdn.po.UserMes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDao {
    //新增用户
    int addUser(User user);

    //匹配用户名和姓名是否正确
    int selectUser(User user);

    //判断用户是否存在
    boolean isExist(String userName);

    //通过id查找返回用户信息
    User queryUser(int uId);

    //更新用户信息
    int update(User user);

    //查询用户是否收藏博客
    boolean conIsExist(int uId, int bId);

    //通过ID获取用户姓名
    String getNameByUId(int uId);

    //根据用户姓名获取ID
    int getUIdByName(String userName);

    //查询用户关注是否存在
    boolean attnIsExist(int uId, int aId);

    //增加关注的人
    String addAttn(int uId, int aId);

    //取消关注的人
    String cancelAttn(int uId, int aId);

    //根据用户ID获取其关注的人的ID
    ArrayList<Integer> queryAttnIdByUId(int uId, int index);

    //插入用户举报信息
    String updateReport(int uId, String title, String content, String imgUrl);

    //查找所有用户
    ArrayList<User> findAllUser(int index);

    //封禁用户
    String banUser(int uId);

    //解禁用户
    String noBanUser(int uId);

    //添加好友
    PreparedStatement addFriend(UserMes userMes, Connection con, PreparedStatement ps) throws SQLException;

    //查询用户好友ID
    ArrayList<Integer> findFriendId(int uId);

    //删除好友
    PreparedStatement deleteFriend(int uId1, int uId2, Connection con, PreparedStatement ps) throws SQLException;

    //根据两用户Id查询其好友关系Id
    ArrayList<Integer> getUuId(int uId1, int uId2);
}
