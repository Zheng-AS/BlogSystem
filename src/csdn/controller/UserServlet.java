package csdn.controller;

import com.alibaba.fastjson.JSON;
import csdn.po.User;
import csdn.service.ServiceFactory;
import csdn.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private String userName, password;
    private UserService userService = ServiceFactory.getUserService();

    /**
     * 获取用户信息
     * @param req
     * @param resp
     * @throws IOException
     */
    public void getMes(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        userName = (String) req.getServletContext().getAttribute("userName");
        password = (String) req.getServletContext().getAttribute("password");
        Map<String,String> map = new HashMap<>();
        map.put("username",userName);
        map.put("password",password);
        String jsonData = JSON.toJSONString(map);
        PrintWriter out = resp.getWriter();
        out.print(jsonData);
    }

    /**
     * 登录账号
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    /**
     * 注册账号
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        userName = req.getParameter("username");
        password = req.getParameter("password");

        if(userService.register(userName,password) == 1){
            resp.sendRedirect("/psdn/login.html");
        }else {
            resp.sendRedirect("/psdn/register_error.html");
        }
    }

    /**
     * 更改个人信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void updateMes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
