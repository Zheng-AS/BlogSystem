package csdn.controller;

import csdn.po.Blog;
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
                out.print("<td><a href=\"/psdn/admin/banUser?mes=1 target=\"down\">封禁</a></td>");
            }else {
                out.print("<td><a href=\"/psdn/admin/banUser?mes=0 target=\"down\">解封</a></td>");
            }
            out.print("<td><a href=\"/psdn/admin/userBlog?uid="+user.getuId()+"\" target=\"down\">查看用户</a></td>");
            out.print("<td><a href=\"/psdn/admin/userCom?uid="+user.getuId()+"\" target=\"down\">查看用户评论</a></td>");
            out.print("</tr>");
            i ++;
        }
        out.print("<tr>");
        if (indexInt != 0){
            out.print("<td><a href=\"/psdn/blog/findBlog?&index="+(indexInt-6)+"\" target=\"down\">上一页</a></td>");
        }
        if (i == 6){
            out.print("<td><a href=\"/psdn/blog/findBlog?&index="+(indexInt+6)+"\" target=\"down\">下一页</a></td>");
        }
        out.print("</table>");
    }


}
