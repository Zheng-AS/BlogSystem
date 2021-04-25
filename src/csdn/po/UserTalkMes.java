package csdn.po;

public class UserTalkMes {
    private int utId;
    private int uuId;
    private int reqId;
    private String time;
    private String userName;
    private String content;

    public UserTalkMes() {
    }

    public UserTalkMes(int utId, int uuId, int reqId, String time, String content) {
        this.utId = utId;
        this.uuId = uuId;
        this.reqId = reqId;
        this.time = time;
        this.content = content;
    }

    public int getUtId() {
        return utId;
    }

    public void setUtId(int utId) {
        this.utId = utId;
    }

    public int getUuId() {
        return uuId;
    }

    public void setUuId(int umId) {
        this.uuId = umId;
    }

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "UserTalkMes{" +
                "umId=" + uuId +
                ", reqId=" + reqId +
                ", time='" + time + '\'' +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
