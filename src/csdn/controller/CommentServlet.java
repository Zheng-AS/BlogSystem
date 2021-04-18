package csdn.controller;

import csdn.po.Comment;
import csdn.service.ServiceFactory;
import csdn.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/comment/*")
public class CommentServlet extends BaseServlet {
    private UserService userService = ServiceFactory.getUserService();

    /**
     *  递归拼接字符串
     */
    public StringBuffer outPrintComment(StringBuffer sb,ArrayList<Comment> commentArrayList, String userName){
        if(commentArrayList == null ){ }
        else {
            for (Comment comment : commentArrayList) {
                sb.append("<div>");
                sb.append("<div>------------------------------------------------------------------------</div>");
                sb.append("<div>" + comment.getUserName() + "</div>");
                sb.append("<div style=\"padding-left: 20px\">回复["+userName+"]:" + comment.getContent());
                sb.append("<a href=\"/comment/addResp?cId=" + comment.getcId() + "\">点击回复</a>");
                //调用递归函数
                outPrintComment(sb, comment.getRespCom(), comment.getUserName());
                sb.append("</div>");
                sb.append("</div>");
            }
        }
        return sb;
    }

    /**
     * 查看评论&发表评论入口
     */
    public void viewComment(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int bId = Integer.parseInt(req.getParameter("blogId"));
        ArrayList<Comment> commentArrayList = userService.getComment(bId);
        PrintWriter out;
        StringBuffer sb = new StringBuffer();

        out = resp.getWriter();
        //调用递归方法
        out.print("<div>_________________________________博客评论_____________________________________</div>");
        //属于博客评论，不是回复评论，不进入递归
        for (Comment comment : commentArrayList) {
            out.print("<div>");
            out.print("<div>" + comment.getUserName() + "</div>");
            out.print("<div style=\"padding-left: 20px\">评论：" + comment.getContent());
            out.print("<a href=\"/comment/addResp?cId=" + comment.getcId() + "\">点击回复</a>");
            //调用递归函数
            outPrintComment(sb, comment.getRespCom(), comment.getUserName());
            out.print(sb.toString());
            out.print("</div>");
            out.print("</div>");
            out.print("<div>________________________________________________________________________</div>");
        }
        out.print("<div>_____________________________________________________________________________</div>");
        out.print("<br>");
        out.print("<br>");
        out.print("<form id=\"from1\" method=\"post\" action=\"/psdn/comment/addComment\">\n" +
                "    <input type=\"text\" name=\"content\" />\n" +
                "    <input style=\"display: none\" type=\"text\" name=\"blogId\" value=\""+bId+"\"/>\n" +
                "    <input type=\"button\" value=\"发表评论\" onclick=\"submit1()\">\n" +
                "</form>\n" +
                "\n" +
                "<script>\n" +
                "    function submit1() {\n" +
                "        window.document.getElementById(\"from1\").submit();\n" +
                "    }\n" +
                "</script>");
    }

    /**
     *  对博客发表评论
     */
    public void addComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        int uId = (int) req.getServletContext().getAttribute("uid");
        int bId = Integer.parseInt(req.getParameter("blogId"));
        String content = req.getParameter("content");

        String mes = userService.addComment(uId,bId,content);
        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }
}
