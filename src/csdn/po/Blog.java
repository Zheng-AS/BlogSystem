package csdn.po;

import java.util.Arrays;

public class Blog {
    private Integer bId;
    private Integer uId;
    private String bContent;
    private String tag;
    private Integer nOfLike;
    private Integer nOfCon;
    private String isPublic;
    private Comment[] comments;
    private String title;
    private String imgUrl;

    public Blog() {
    }

    public Blog(Integer uId, String bContent, String tag, String isPublic, String title, String imgUrl) {
        this.uId = uId;
        this.bContent = bContent;
        this.tag = tag;
        this.isPublic = isPublic;
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Blog(Integer bId, String bContent, String tag, Integer nOfLike, Integer nOfCon, String isPublic, Comment[] comments, String title, String imgUrl) {
        this.bId = bId;
        this.bContent = bContent;
        this.tag = tag;
        this.nOfLike = nOfLike;
        this.nOfCon = nOfCon;
        this.isPublic = isPublic;
        this.comments = comments;
        this.title = title;
        this.imgUrl = imgUrl;
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

    public String getPublic() {
        return isPublic;
    }

    public void setPublic(String aPublic) {
        isPublic = aPublic;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
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
                ", title=" + title +
                '}';
    }
}
