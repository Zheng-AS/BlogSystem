package csdn.controller;

import com.alibaba.fastjson.JSON;
import csdn.po.User;
import csdn.service.ServiceFactory;
import csdn.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateMesServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName,password;
        UserService userService = ServiceFactory.getUserService();
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        userName = req.getParameter("username");
        password = req.getParameter("password");
        int uid = (int) req.getServletContext().getAttribute("uid");

        if(userService.updateMes(new User(uid,userName,password))){
            out.print("<font style='color:red;font-size:40'>更改成功</font>");
        }
    }
}


