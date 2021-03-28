package csdn.po;

public class Comment {
    private Integer cId;
    private String cContent;

    public Comment() {
    }

    public Comment(Integer cId, String cContent) {
        this.cId = cId;
        this.cContent = cContent;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cId=" + cId +
                ", cContent='" + cContent + '\'' +
                '}';
    }
}
