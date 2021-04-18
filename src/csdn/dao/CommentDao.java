package csdn.dao;

import csdn.po.Comment;

import java.util.ArrayList;

public interface CommentDao {
    //发表评论
    String addComment(int uId, int bId, String content);

    //根据博客Id查找评论
    ArrayList<Comment> getCommentByBId(int bId);

    //根据评论Id查找评论
    Comment getCommentByCId(String cId);

    //根据此评论Id获取其评论回复Id
    ArrayList<String> getCommentRespId(String cId);

}
