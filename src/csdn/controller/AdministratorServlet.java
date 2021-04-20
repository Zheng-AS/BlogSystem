package csdn.controller;

import csdn.service.AdminService;
import csdn.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin/*")
public class AdministratorServlet extends BaseServlet {
    private AdminService adminService = ServiceFactory.getAdminService();

    /**
     * 登录账号
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String userName = req.getParameter("username");
        String password = req.getParameter("password");


        if(adminService.login(userName, password)){
            HttpSession session = req.getSession();
            resp.sendRedirect("/psdn/index.html");
        }else {
            resp.sendRedirect("/psdn/admin_login_error.html");
        }
    }
}
