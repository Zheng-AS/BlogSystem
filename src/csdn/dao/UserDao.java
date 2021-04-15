package csdn.dao;

import csdn.po.User;

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

    //查询用户关注是否存在
    boolean attnIsExist(int uId, int aId);

    //增加关注的人
    String addAttn(int uId, int aId);

    //取消关注的人
    String cancelAttn(int uId, int aId);
}
