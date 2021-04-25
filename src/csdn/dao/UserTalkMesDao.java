package csdn.dao;

import csdn.po.UserTalkMes;

import java.util.ArrayList;

public interface UserTalkMesDao {
    //插入用户聊天数据
    String addTalkMes(int reqId, int uuId, String content);

    //获取用户聊天信息
    ArrayList<UserTalkMes> getTalkMes(ArrayList<Integer> arrayList);
}
