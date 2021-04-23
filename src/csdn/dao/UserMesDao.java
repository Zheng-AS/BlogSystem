package csdn.dao;

import csdn.po.UserMes;

import java.util.ArrayList;

public interface UserMesDao {
    //插入好友请求
    boolean sendFriendRequest(int reqId, int respId);

    //查看好友请求是否存在
    boolean requestIsExist(int reqId, int respId);

    //返回用户信息
    ArrayList<UserMes> getUserMes(int respId);

    //通过ID查找用户信息
    UserMes getUserMesByUMId(int umId);

    //删除（好友请求），增添（拒绝消息）
    String reject(UserMes userMes);

    //删除消息
    String deleteMes(int umId);
}
