package csdn.dao;

public interface CommentDao {
    //发表评论
    String addComment(int uId, int bId, String content);
}
