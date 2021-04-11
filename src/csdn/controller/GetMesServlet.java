package csdn.controller;

import com.alibaba.fastjson.JSON;
import csdn.service.ServiceFactory;
import csdn.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class GetMesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        UserService userService = ServiceFactory.getUserService();
        String userName = (String) req.getServletContext().getAttribute("userName");
        String password = (String) req.getServletContext().getAttribute("password");
        Map<String,String> map = new HashMap<>();
        map.put("username",userName);
        map.put("password",password);
        String jsonData = JSON.toJSONString(map);
        PrintWriter out = resp.getWriter();
        out.print(jsonData);
    }
}
