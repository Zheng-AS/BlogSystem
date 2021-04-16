package csdn.po;

import java.util.Arrays;

public class Comment {
    private Integer cId;
    private String content;
    private Integer uId;
    private Comment[] respCom;

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
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

    public Comment[] getRespCom() {
        return respCom;
    }

    public void setRespCom(Comment[] respCom) {
        this.respCom = respCom;
    }

    public Comment() {
    }

    public Comment(Integer cId, String content, Integer uId, Comment[] respCom) {
        this.cId = cId;
        this.content = content;
        this.uId = uId;
        this.respCom = respCom;
    }

    public Comment(Integer cId, String content, Integer uId) {
        this.cId = cId;
        this.content = content;
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cId=" + cId +
                ", content='" + content + '\'' +
                ", uId=" + uId +
                ", respCom=" + Arrays.toString(respCom) +
                '}';
    }
}
