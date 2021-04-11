package csdn.controller;

import csdn.service.ServiceFactory;
import csdn.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName, password;
        UserService userService = ServiceFactory.getUserService();
        req.setCharacterEncoding("utf-8");

        userName = req.getParameter("username");
        password = req.getParameter("password");

        int uid = userService.login(userName, password);
        if(uid != 0){
            req.getServletContext().setAttribute("uid",uid);
            req.getServletContext().setAttribute("userName",userName);
            req.getServletContext().setAttribute("password",password);
            resp.sendRedirect("/psdn/index.html");
        }else {
            resp.sendRedirect("/psdn/login_error.html");
        }
    }
}
