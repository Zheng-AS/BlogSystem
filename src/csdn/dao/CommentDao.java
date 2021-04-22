package csdn.dao;

import csdn.po.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CommentDao {
    //发表评论
    String addComment(int uId, int bId, String content);

    //根据博客Id查找评论
    ArrayList<Comment> getCommentByBId(int bId, int index);

    //根据评论Id查找评论
    Comment getCommentByCId(String cId);

    //根据用户Id查找评论
    ArrayList<Comment> getCommentIdByUId(int uId, int index);

    //根据博客Id查找评论Id
    ArrayList<String> getCommentIdByBId(int bId);

    //根据此评论Id获取其评论回复Id
    ArrayList<String> getCommentRespId(String cId);

    //发表回复评论
    String addRespCom(String cId, String content, int uId);

    //删除回复评论（被调用）
    PreparedStatement deleteRespCom(String cId, Connection con, PreparedStatement ps) throws SQLException;

    //删除回复（被调用）
    PreparedStatement deleteCom(String cId, Connection con, PreparedStatement ps) throws SQLException;

    //判断是否回复评论
    boolean isRespCom(String rId);

    //查询所有评论
    ArrayList<Comment> findAllComment(int index);
}
