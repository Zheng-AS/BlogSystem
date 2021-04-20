package csdn.po;

import java.util.Arrays;

public class User {
    private Integer uId;
    private String userName;
    private String password;
    //头像url
    private String url;
    //关注的人id
    private User[] myAttns;
    //粉丝id
    private User[] myFans;
    private Blog[] myBlogs;
    //收藏的博客
    private Blog[] conBlogs;
    //我的好友
    private User[] friends;
    private String able;

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(Integer uId, String userName, String password, String url, User[] myAttns, User[] myFans, Blog[] myBlogs, Blog[] conBlogs, User[] friends) {
        this.uId = uId;
        this.userName = userName;
        this.password = password;
        this.url = url;
        this.myAttns = myAttns;
        this.myFans = myFans;
        this.myBlogs = myBlogs;
        this.conBlogs = conBlogs;
        this.friends = friends;
    }

    public User(Integer uId, String userName, String password) {
        this.uId = uId;
        this.userName = userName;
        this.password = password;
    }

    public User(Integer uId, String userName, String password, String able) {
        this.uId = uId;
        this.userName = userName;
        this.password = password;
        this.able = able;
    }

    public String getAble() {
        return able;
    }

    public void setAble(String able) {
        this.able = able;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User[] getMyAttns() {
        return myAttns;
    }

    public void setMyAttns(User[] myAttns) {
        this.myAttns = myAttns;
    }

    public User[] getMyFans() {
        return myFans;
    }

    public void setMyFans(User[] myFans) {
        this.myFans = myFans;
    }

    public Blog[] getMyBlogs() {
        return myBlogs;
    }

    public void setMyBlogs(Blog[] myBlogs) {
        this.myBlogs = myBlogs;
    }

    public Blog[] getConBlogs() {
        return conBlogs;
    }

    public void setConBlogs(Blog[] conBlogs) {
        this.conBlogs = conBlogs;
    }

    public User[] getFriends() {
        return friends;
    }

    public void setFriends(User[] friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "uId=" + uId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", myAttns=" + Arrays.toString(myAttns) +
                ", myFans=" + Arrays.toString(myFans) +
                ", myBlogs=" + Arrays.toString(myBlogs) +
                ", conBlogs=" + Arrays.toString(conBlogs) +
                ", friends=" + Arrays.toString(friends) +
                ", able='" + able + '\'' +
                '}';
    }
}
