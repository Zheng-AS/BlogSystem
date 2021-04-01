package csdn.controller;

import csdn.dao.UserDao;
import csdn.service.ServiceFactory;
import csdn.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserRegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName, password;
        UserService userService = ServiceFactory.getUserService();
        req.setCharacterEncoding("utf-8");

        userName = req.getParameter("username");
        password = req.getParameter("password");

        if(userService.register(userName,password) == 1){
            resp.sendRedirect("/psdn/login.html");
        }else {
            resp.sendRedirect("/psdn/register_error.html");
        }
    }
}
