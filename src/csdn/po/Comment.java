package csdn.po;

import java.util.ArrayList;
import java.util.Arrays;

public class Comment {
    private String cId;
    private String content;
    private Integer uId;
    private ArrayList<Comment> respCom;
    private String time;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getcId() {
        return cId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public ArrayList<Comment> getRespCom() {
        return respCom;
    }

    public void setRespCom(ArrayList<Comment> respCom) {
        this.respCom = respCom;
    }

    public Comment() {
    }

    public Comment(String cId, String content, Integer uId, ArrayList<Comment> respCom, String time) {
        this.cId = cId;
        this.content = content;
        this.uId = uId;
        this.respCom = respCom;
        this.time = time;
    }

    public Comment(String cId, String content, Integer uId, String time) {
        this.cId = cId;
        this.content = content;
        this.uId = uId;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cId='" + cId + '\'' +
                ", content='" + content + '\'' +
                ", uId=" + uId +
                ", respCom=" + respCom +
                ", time='" + time + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
