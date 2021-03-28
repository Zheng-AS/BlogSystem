package csdn.po;

import java.util.Arrays;

public class Blog {
    private Integer bId;
    private String bContent;
    private String tag;
    private Integer nOfLike;
    private Integer nOfCon;
    private Boolean isPublic;
    private Comment[] comments;

    public Blog() {
    }

    public Blog(Integer bId, String bContent, String tag, Integer nOfLike, Integer nOfCon, Boolean isPublic, Comment[] comments) {
        this.bId = bId;
        this.bContent = bContent;
        this.tag = tag;
        this.nOfLike = nOfLike;
        this.nOfCon = nOfCon;
        this.isPublic = isPublic;
        this.comments = comments;
    }

    public Integer getbId() {
        return bId;
    }

    public void setbId(Integer bId) {
        this.bId = bId;
    }

    public String getbContent() {
        return bContent;
    }

    public void setbContent(String bContent) {
        this.bContent = bContent;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getnOfLike() {
        return nOfLike;
    }

    public void setnOfLike(Integer nOfLike) {
        this.nOfLike = nOfLike;
    }

    public Integer getnOfCon() {
        return nOfCon;
    }

    public void setnOfCon(Integer nOfCon) {
        this.nOfCon = nOfCon;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "bId=" + bId +
                ", bContent='" + bContent + '\'' +
                ", tag='" + tag + '\'' +
                ", nOfLike=" + nOfLike +
                ", nOfCon=" + nOfCon +
                ", isPublic=" + isPublic +
                ", comments=" + Arrays.toString(comments) +
                '}';
    }
}
