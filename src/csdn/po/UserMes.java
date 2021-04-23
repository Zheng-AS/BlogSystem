package csdn.po;

public class UserMes {
    private Integer umId;
    private Integer reqId;
    private Integer respId;
    private String type;

    public UserMes() {
    }

    public UserMes(Integer umId, Integer reqId, Integer respId, String type) {
        this.umId = umId;
        this.reqId = reqId;
        this.respId = respId;
        this.type = type;
    }

    public Integer getUmId() {
        return umId;
    }

    public void setUmId(Integer umId) {
        this.umId = umId;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public Integer getRespId() {
        return respId;
    }

    public void setRespId(Integer respId) {
        this.respId = respId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserMes{" +
                "umId=" + umId +
                ", reqId=" + reqId +
                ", respId=" + respId +
                ", type='" + type + '\'' +
                '}';
    }
}
