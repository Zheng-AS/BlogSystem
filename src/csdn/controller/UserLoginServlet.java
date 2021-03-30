package csdn.controller;

import csdn.service.ServiceFactory;
import csdn.service.UserService;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginServlet extends HttpServlet {
    static {
        Logger logger = Logger.getLogger(UserLoginServlet.class);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName, password;
        UserService userService = ServiceFactory.getUserService();
        int result = 0;
        req.setCharacterEncoding("utf-8");

        userName = req.getParameter("username");
        password = req.getParameter("password");

        if(userService.login(userName, password)){
            resp.sendRedirect("/psdn/index.html");
        }else {
            resp.sendRedirect("/psdn/login_error.html");
        }
    }
}
