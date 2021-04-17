package csdn.controller;

import csdn.service.ServiceFactory;
import csdn.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/comment/*")
public class CommentServlet extends BaseServlet {
    private UserService userService = ServiceFactory.getUserService();

    /**
     * 查看评论&发表评论
     */
    public void viewComment(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int bId = Integer.parseInt(req.getParameter("blogId"));
        PrintWriter out;

        out = resp.getWriter();
        out.print("<div>");
        out.print("</div>");
        out.print("<br>");
        out.print("<form id=\"from1\" method=\"post\" action=\"/psdn/comment/addComment\">\n" +
                "    <input type=\"text\" name=\"content\" />\n" +
                "    <input style=\"display: none\" type=\"text\" name=\"blogId\" value=\""+bId+"\"/>\n" +
                "    <input type=\"button\" value=\"点击提交\" onclick=\"submit1()\">\n" +
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
