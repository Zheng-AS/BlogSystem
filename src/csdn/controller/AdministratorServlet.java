package csdn.controller;

import csdn.po.Blog;
import csdn.po.Comment;
import csdn.po.User;
import csdn.service.AdminService;
import csdn.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/admin/*")
public class AdministratorServlet extends BaseServlet {
    private AdminService adminService = ServiceFactory.getAdminService();

    /**
     * 登录账号
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");

        String userName = req.getParameter("username");
        String password = req.getParameter("password");


        if(adminService.login(userName, password)){
            HttpSession session = req.getSession();
            resp.sendRedirect("/psdn/index_admin.html");
        }else {
            resp.sendRedirect("/psdn/admin_login_error.html");
        }
    }

    /**
     *  管理用户
     */
    public void user(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        String index = req.getParameter("index");
        int indexInt = Integer.parseInt(index);
        ArrayList<User> userArrayList = adminService.checkUser(indexInt);

        PrintWriter out;

        int i = 0;
        out = resp.getWriter();
        out.print("<table border='2' align='center'>");
        out.print("<tr>");
        out.print("<td>编号</td>");
        out.print("<td>用户Id</td>");
        out.print("<td>用户姓名</td>");
        out.print("<td>操作</td>");
        out.print("<td>操作</td>");
        out.print("<td>操作</td>");
        out.print("</tr>");
        for (User user : userArrayList) {
            out.print("<tr>");
            out.print("<td>" + (i+1) + "</td>");
            out.print("<td>" + user.getuId()+ "</td>");
            out.print("<td>" + user.getUserName()+ "</td>");
            if(user.getAble().equals("否")){
                out.print("<td><a href=\"/psdn/admin/banUser?able=1&uid="+user.getuId()+"\" target=\"down\">封禁</a></td>");
            }else {
                out.print("<td><a href=\"/psdn/admin/banUser?able=0&uid="+user.getuId()+"\" target=\"down\">解封</a></td>");
            }
            out.print("<td><a href=\"/psdn/admin/userBlog?index=0&uid="+user.getuId()+"&uIndex="+index+"\" target=\"down\">查看用户</a></td>");
            out.print("<td><a href=\"/psdn/admin/userCom?index=0&uid="+user.getuId()+"&uIndex="+index+"\" target=\"down\">查看用户评论</a></td>");
            out.print("</tr>");
            i ++;
        }
        out.print("<tr>");
        if (indexInt != 0){
            out.print("<td><a href=\"/psdn/admin/user?&index="+(indexInt-6)+"\" target=\"down\">上一页</a></td>");
        }
        if (i == 6){
            out.print("<td><a href=\"/psdn/admin/user?&index="+(indexInt+6)+"\" target=\"down\">下一页</a></td>");
        }
        out.print("</table>");
    }

    /**
     *  管理用户：【封禁/解禁】
     */
    public void banUser(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String mes;

        String able = req.getParameter("able");
        String ret = req.getParameter("ret");
        int uId = Integer.parseInt(req.getParameter("uid"));
        if(able.equals("1")){
            mes = adminService.banUser(uId);
        }else {
            mes = adminService.noBanUser(uId);
        }
        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }

    /**
     *  管理用户：【查看用户】
     */
    public void userBlog(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int uId = Integer.parseInt(req.getParameter("uid"));
        String index = req.getParameter("index");
        int indexInt = Integer.parseInt(index);
        int uIndex = Integer.parseInt(req.getParameter("uIndex"));
        PrintWriter out = resp.getWriter();


        ArrayList<Blog> blogArrayList = adminService.getUserBlogMes(uId, indexInt);
        int i = 0;
        out = resp.getWriter();
        out.print("<table border='2' align='center'>");
        out.print("<tr>");
        out.print("<td>编号</td>");
        out.print("<td>博客Id</td>");
        out.print("<td>博客标题</td>");
        out.print("<td>博客分类</td>");
        out.print("<td>博客收藏数</td>");
        out.print("<td>博客点赞数</td>");
        out.print("<td>操作</td>");
        out.print("<td>操作</td>");
        out.print("</tr>");
        for (i = 0; i<blogArrayList.size(); i++) {
            Blog blog = blogArrayList.get(i);
            out.print("<tr>");
            out.print("<td>" + (i+1) + "</td>");
            out.print("<td>" + blog.getbId() + "</td>");
            out.print("<td>" + blog.getTitle()+ "</td>");
            out.print("<td>" + blog.getTag()+ "</td>");
            out.print("<td>" + blog.getnOfCon() + "</td>");
            out.print("<td>" + blog.getnOfLike() + "</td>");
            out.print("<td><a href=\"http://localhost:8080/psdn/admin_view_blog.html?blogId="+blog.getbId()+"\" target=\"down\">查看博客</a></td>");
            out.print("<td><a href=\"/psdn/blog/deleteBlog?blogId="+blog.getbId()+"\" target=\"down\">删除博客</a></td>");
            out.print("</tr>");
        }
        out.print("<tr>");
        if (indexInt != 0){
            out.print("<td><a href=\"/psdn/admin/userBlog?uid="+uId+"&index="+(indexInt-6)+"\" target=\"down\">上一页</a></td>");
        }
        if (i == 6){
            out.print("<td><a href=\"/psdn/admin/userBlog?uid="+uId+"&index="+(indexInt+6)+"\" target=\"down\">下一页</a></td>");
        }
        out.print("<td><a href=\"/psdn/admin/user?index="+uIndex+"\" target=\"down\">返回</a></td>");
        out.print("</table>");
    }

    /**
     *  管理用户：【查看用户评论】
     */
    public void userCom(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int uId = Integer.parseInt(req.getParameter("uid"));
        String index = req.getParameter("index");
        int indexInt = Integer.parseInt(index);
        int uIndex = Integer.parseInt(req.getParameter("uIndex"));
        PrintWriter out = resp.getWriter();


        ArrayList<Comment> commentArrayList = adminService.getUserCom(uId, indexInt);
        int i = 0;
        out = resp.getWriter();
        out.print("<table border='2' align='center'>");
        out.print("<tr>");
        out.print("<td>编号</td>");
        out.print("<td>评论Id</td>");
        out.print("<td>用户昵称</td>");
        out.print("<td>用户Id</td>");
        out.print("<td>评论内容</td>");
        out.print("<td>评论时间</td>");
        out.print("<td>操作</td>");
        out.print("</tr>");
        for (Comment comment : commentArrayList) {
            out.print("<tr>");
            out.print("<td>" + (i+1) + "</td>");
            out.print("<td>" + comment.getcId() + "</td>");
            out.print("<td>" + comment.getUserName()+ "</td>");
            out.print("<td>" + comment.getuId()+ "</td>");
            out.print("<td>" + comment.getContent() + "</td>");
            out.print("<td>" + comment.getTime() + "</td>");
            out.print("<td><a href=\"/psdn/comment/deleteComment?cId="+comment.getcId()+"\" target=\"down\">删除评论</a></td>");
            out.print("</tr>");
            i ++;
        }
        out.print("<tr>");
        if (indexInt != 0){
            out.print("<td><a href=\"/psdn/admin/userCom?uid="+uId+"&index="+(indexInt-6)+"&uIndex="+uIndex+"\" target=\"down\">上一页</a></td>");
        }
        if (i == 6){
            out.print("<td><a href=\"/psdn/admin/userCom?uid="+uId+"&index="+(indexInt+6)+"&uIndex="+uIndex+"\" target=\"down\">下一页</a></td>");
        }
        out.print("<td><a href=\"/psdn/admin/user?index="+uIndex+"\" target=\"down\">返回</a></td>");
        out.print("</table>");
    }
}
