package csdn.controller;

import csdn.po.Comment;
import csdn.service.AdminService;
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
    private AdminService adminService = ServiceFactory.getAdminService();

    /**
     *  输出评论（用户使用）
     */
    public void outPrintComment(PrintWriter out,ArrayList<Comment> commentArrayList, String userName){
        if(commentArrayList == null ){ }
        else {
            for (Comment comment : commentArrayList) {
                out.print("<div>");
                out.print("<div>------------------------------------------------------------------------</div>");
                out.print("<div>" + comment.getUserName() + "</div>");
                out.print("<div style=\"padding-left: 20px\">回复["+userName+"]:" + comment.getContent());
                out.print("<a href=\"http://localhost:8080/psdn/add_resp.html?cId=" + comment.getcId() + "\">点击回复</a>");
                //调用递归函数
                outPrintComment(out, comment.getRespCom(), comment.getUserName());
                out.print("</div>");
                out.print("</div>");
            }
        }
    }

   /**
     *  输出评论（管理员用）
     */
    public void adminOutPrintComment(PrintWriter out,ArrayList<Comment> commentArrayList, String userName){
        if(commentArrayList == null ){ }
        else {
            for (Comment comment : commentArrayList) {
                out.print("<div>");
                out.print("<div>------------------------------------------------------------------------</div>");
                out.print("<div>" + comment.getUserName() + "</div>");
                out.print("<div style=\"padding-left: 20px\">回复["+userName+"]:" + comment.getContent());
                out.print("<a href=\"/psdn/comment/deleteRespCom?cId=" + comment.getcId() + "\">点击删除</a>");
                //调用递归函数
                adminOutPrintComment(out, comment.getRespCom(), comment.getUserName());
                out.print("</div>");
                out.print("</div>");
            }
        }
    }

    /**
     * 查看评论&发表评论入口（用户使用）
     */
    public void viewComment(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int bId = Integer.parseInt(req.getParameter("blogId"));
        String index = req.getParameter("index");
        int indexInt = Integer.parseInt(index);
        ArrayList<Comment> commentArrayList = userService.getComment(bId,indexInt);
        PrintWriter out;

        out = resp.getWriter();
        //调用递归方法
        out.print("<div>_________________________________博客评论_____________________________________</div>");
        //属于博客评论，不是回复评论，不进入递归
        int i = 0;
        for (Comment comment : commentArrayList) {
            out.print("<div>");
            out.print("<div>" + comment.getUserName() + "</div>");
            out.print("<div style=\"padding-left: 20px\">评论：" + comment.getContent());
            out.print("<a href=\"http://localhost:8080/psdn/add_resp.html?cId=" + comment.getcId() + "\">点击回复</a>");
            //调用递归函数
            outPrintComment(out, comment.getRespCom(), comment.getUserName());
            out.print("</div>");
            out.print("</div>");
            out.print("<div>________________________________________________________________________</div>");
            i ++;
        }
        if (indexInt != 0){
            out.print("<div><a href=\"/psdn/comment/viewComment?blogId="+bId+"&index="+(indexInt-6)+"\" target=\"right\">上一页</a></div>");
        }
        if (i == 6){
            out.print("<div><a href=\"/psdn/comment/viewComment?blogId="+bId+"&index="+(indexInt+6)+"\" target=\"right\">下一页</a></div>");
        }
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
     * 查看评论&删除评论入口（管理员使用）
     */
    public void adminViewComment(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int bId = Integer.parseInt(req.getParameter("blogId"));
        String index = req.getParameter("index");
        int indexInt = Integer.parseInt(index);
        ArrayList<Comment> commentArrayList = userService.getComment(bId,indexInt);
        PrintWriter out;

        out = resp.getWriter();
        //调用递归方法
        out.print("<div>_________________________________博客评论_____________________________________</div>");
        //属于博客评论，不是回复评论，不进入递归
        int i = 0;
        for (Comment comment : commentArrayList) {
            out.print("<div>");
            out.print("<div>" + comment.getUserName() + "</div>");
            out.print("<div style=\"padding-left: 20px\">评论：" + comment.getContent());
            out.print("<a href=\"/psdn/comment/deleteCom?cId=" + comment.getcId() + "\">点击删除</a>");
            //调用递归函数
            adminOutPrintComment(out, comment.getRespCom(), comment.getUserName());
            out.print("</div>");
            out.print("</div>");
            out.print("<div>________________________________________________________________________</div>");
            i ++;
        }
        if (indexInt != 0){
            out.print("<div><a href=\"/psdn/comment/adminViewComment?blogId="+bId+"&index="+(indexInt-6)+"\" target=\"down\">上一页</a></div>");
        }
        if (i == 6){
            out.print("<div><a href=\"/psdn/comment/adminViewComment?blogId="+bId+"&index="+(indexInt+6)+"\" target=\"down\">下一页</a></div>");
        }
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

    /**
     *  增加回复评论功能
     */
    public void addRespCom(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        int uId = (int) req.getServletContext().getAttribute("uid");
        String cId = req.getParameter("cId");
        String content = req.getParameter("content");

        String mes = userService.addRespCom(cId,content,uId);
        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }

    /**
     *  删除回复评论
     */
    public void deleteRespCom(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        String cId = req.getParameter("cId");

        String mes = adminService.deleteRespCom(cId);
        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }

    /**
     *  删除博客评论
     */
    public void deleteCom(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        String cId = req.getParameter("cId");

        String mes = adminService.deleteCom(cId);
        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }

    /**
     *  删除评论(博客评论/回复评论)
     */
    public void deleteComment(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        String cId = req.getParameter("cId");

        String mes = adminService.deleteComment(cId);
        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }
}
