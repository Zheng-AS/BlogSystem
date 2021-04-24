package csdn.controller;

import com.alibaba.fastjson.JSON;
import csdn.po.Blog;
import csdn.po.User;
import csdn.po.UserMes;
import csdn.service.ServiceFactory;
import csdn.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private String userName, password;
    private UserService userService = ServiceFactory.getUserService();

    /**
     * 获取用户信息
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
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");

        userName = req.getParameter("username");
        password = req.getParameter("password");

        int uid = userService.login(userName, password);
        if(uid == -1){
            resp.sendRedirect("/psdn/login_ban.html");
        }else if(uid != 0){
            HttpSession session = req.getSession();
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
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
     */
    public void updateMes(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    /**
     *  我的关注
     */
    public void attention(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        int uId = (int) req.getServletContext().getAttribute("uid");
        String index = req.getParameter("index");
        int indexInt = Integer.parseInt(index);
        ArrayList<User> userAttnList = userService.getUserAttn(uId,indexInt);
        int i =0;

        out.print("<table border='2' align='center'>");
        out.print("<tr>");
        out.print("<td>编号</td>");
        out.print("<td>用户姓名</td>");
        out.print("<td>操作</td>");
        out.print("<td>操作</td>");
        out.print("</tr>");
        for (User attnUser : userAttnList) {
            out.print("<tr>");
            out.print("<td>" + (i+1) + "</td>");
            out.print("<td>" + attnUser.getUserName() + "</td>");
            out.print("<td><a href=\"/psdn/user/checkAttn?attnId="+attnUser.getuId()+"&index=0\" target=\"right\">查看</a></td>");
            out.print("<td><a href=\"psdn/view_other_blog.html?aid="+attnUser.getuId()+"\" target=\"right\">取消关注</a></td>");
            out.print("</tr>");
            i ++;
        }
        out.print("<tr>");
        if (indexInt != 0){
            out.print("<td><a href=\"/psdn/user/attention?index="+(indexInt-6)+"\" target=\"right\">上一页</a></td>");
        }
        if (i == 6){
            out.print("<td><a href=\"/psdn/user/attention?index="+(indexInt+6)+"\" target=\"right\">下一页</a></td>");
        }
        out.print("</table>");
    }

    /**
     *  我的收藏内（查看用户功能）
     */
    public void checkAttn(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        int uId = Integer.parseInt(req.getParameter("attnId"));
        String index = req.getParameter("index");
        int indexInt = Integer.parseInt(index);
        ArrayList<Blog> blogArrayList = userService.checkBlog(uId, indexInt);
        int i =0;

        out.print("<table border='2' align='center'>");
        out.print("<tr>");
        out.print("<td>博客编号</td>");
        out.print("<td>博客标题</td>");
        out.print("<td>博客分类</td>");
        out.print("<td>博客收藏数</td>");
        out.print("<td>博客点赞数</td>");
        out.print("<td>操作</td>");
        out.print("</tr>");
        for (Blog blog : blogArrayList) {
            out.print("<tr>");
            out.print("<td>" + (i+1) + "</td>");
            out.print("<td>" + blog.getTitle()+ "</td>");
            out.print("<td>" + blog.getTag()+ "</td>");
            out.print("<td>" + blog.getnOfCon() + "</td>");
            out.print("<td>" + blog.getnOfLike() + "</td>");
            out.print("<td><a href=\"http://localhost:8080/psdn/view_other_blog.html?blogId="+blog.getbId()+"\" target=\"right\">查看博客</a></td>");
            out.print("</tr>");
            i ++;
        }
        out.print("<tr>");
        if (indexInt != 0){
            out.print("<td><a href=\"/psdn/user/checkAttn?index="+(indexInt-6)+"\" target=\"right\">上一页</a></td>");
        }
        if (i == 6){
            out.print("<td><a href=\"/psdn/user/checkAttn?index="+(indexInt+6)+"\" target=\"right\">下一页</a></td>");
        }
        out.print("</table>");
    }

    /**
     *  我的收藏
     */
    public void collection(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        String index = req.getParameter("index");
        int indexInt = Integer.parseInt(index);
        int uid = (int) req.getServletContext().getAttribute("uid");
        ArrayList<Blog> blogArrayList = userService.checkBlog(uid, indexInt);
        int i =0;

        out.print("<table border='2' align='center'>");
        out.print("<tr>");
        out.print("<td>博客编号</td>");
        out.print("<td>博客标题</td>");
        out.print("<td>博客分类</td>");
        out.print("<td>博客收藏数</td>");
        out.print("<td>博客点赞数</td>");
        out.print("<td>操作</td>");
        out.print("</tr>");
        for (Blog blog : blogArrayList) {
            out.print("<tr>");
            out.print("<td>" + (i+1) + "</td>");
            out.print("<td>" + blog.getTitle()+ "</td>");
            out.print("<td>" + blog.getTag()+ "</td>");
            out.print("<td>" + blog.getnOfCon() + "</td>");
            out.print("<td>" + blog.getnOfLike() + "</td>");
            out.print("<td><a href=\"http://localhost:8080/psdn/view_other_blog.html?blogId="+blog.getbId()+"\" target=\"right\">查看博客</a></td>");
            out.print("</tr>");
            i ++;
        }
        out.print("<tr>");
        if (indexInt != 0){
            out.print("<td><a href=\"/psdn/user/collection?index="+(indexInt-6)+"\" target=\"right\">上一页</a></td>");
        }
        if (i == 6){
            out.print("<td><a href=\"/psdn/user/collection?index="+(indexInt+6)+"\" target=\"right\">下一页</a></td>");
        }
        out.print("</table>");
    }

    /**
     *  我要举报
     */
    public void report(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        String title = req.getParameter("title");
        String imgUrl = req.getParameter("imgUrl");
        String content = req.getParameter("content");
        int uId = (int) req.getServletContext().getAttribute("uid");

        String mes = userService.updateReport(uId, title ,content ,imgUrl);

        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }

    /**
     *  发送好友请求
     */
    public void sendFriendRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json;charset=utf-8");

        String userName = req.getParameter("username");
        int uId = (int) req.getServletContext().getAttribute("uid");
        String mes = userService.sendFriendRequest(uId, userName);

        Map<String,String> map = new HashMap<>();
        map.put("mes", mes);
        resp.getWriter().print(JSON.toJSONString(map));
    }

    /**
     *  我的消息
     */
    public void message(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        int uId = (int) req.getServletContext().getAttribute("uid");
        ArrayList<UserMes> userMesArrayList = userService.getUserMes(uId);
        PrintWriter out = resp.getWriter();
        String userName;
        int i =0;

        out.print("<table border='2' align='center'>");
        out.print("<tr>");
        out.print("<td>编号</td>");
        out.print("<td>发送用户</td>");
        out.print("<td>信息类型</td>");
        out.print("<td>内容</td>");
        out.print("<td>操作</td>");
        out.print("<td>操作</td>");
        out.print("</tr>");
        for (UserMes mes : userMesArrayList) {
            userName = userService.getAuthorName(mes.getReqId());
            out.print("<tr>");
            out.print("<td>" + (i+1) + "</td>");
            out.print("<td>" + userName+ "</td>");
            if(mes.getType().equals("request")){
                out.print("<td> 好友请求 </td>");
                out.print("<td> ["+userName+"]:向你发送了好友请求 </td>");
                out.print("<td><a href=\"/psdn/user/accept?umid="+mes.getUmId()+"\" target=\"right\">接受</a></td>");
                out.print("<td><a href=\"/psdn/user/reject?umid="+mes.getUmId()+"\" target=\"right\">拒绝</a></td>");
            }else if(mes.getType().equals("accept")){
                out.print("<td> 提示信息 </td>");
                out.print("<td> ["+userName+"]:接受了你的好友请求 </td>");
                out.print("<td><a href=\"/psdn/user/read?umid="+mes.getUmId()+"\" target=\"right\">已读</a></td>");
                out.print("<td><a href=\"/psdn/user/read?umid="+mes.getUmId()+"\" target=\"right\">已读</a></td>");
            }else if(mes.getType().equals("delete")){
                out.print("<td> 提示信息 </td>");
                out.print("<td> ["+userName+"]:解除了与你的好友关系 </td>");
                out.print("<td><a href=\"/psdn/user/read?umid="+mes.getUmId()+"\" target=\"right\">已读</a></td>");
                out.print("<td><a href=\"/psdn/user/read?umid="+mes.getUmId()+"\" target=\"right\">已读</a></td>");
            }else {
                out.print("<td> 提示信息 </td>");
                out.print("<td> ["+userName+"]:拒绝了你的好友请求 </td>");
                out.print("<td><a href=\"/psdn/user/read?umid="+mes.getUmId()+"\" target=\"right\">已读</a></td>");
                out.print("<td><a href=\"/psdn/user/read?umid="+mes.getUmId()+"\" target=\"right\">已读</a></td>");
            }
            out.print("</tr>");
            i ++;
        }
        out.print("</table>");
    }

    /**
     *  我的消息【拒绝好友请求】
     */
    public void reject(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        int umId = Integer.parseInt(req.getParameter("umid"));
        String mes = userService.rejectFriendRequest(umId);

        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }

    /**
     *  我的消息【消息已读】
     */
    public void read(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        int umId = Integer.parseInt(req.getParameter("umid"));
        String mes = userService.readMes(umId);

        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }

    /**
     *  我的消息【添加好友】
     */
    public void accept(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        int umId = Integer.parseInt(req.getParameter("umid"));
        String mes = userService.addFriend(umId);

        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }

    /**
     *  我的好友
     */
    public void friend(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        int uId = (int) req.getServletContext().getAttribute("uid");
        ArrayList<User> friendList = userService.findFriend(uId);

        int i = 0;
        PrintWriter out = resp.getWriter();
        out.print("<br>");
        out.print("<br>");
        out.print("<br>");
        out.print("<br>");

        out.print("<table border='2' align='center'>");
        out.print("<tr>");
        out.print("<td>编号</td>");
        out.print("<td>用户昵称</td>");
        out.print("<td>操作</td>");
        out.print("<td>操作</td>");
        out.print("</tr>");
        for (User user : friendList) {
            out.print("<tr>");
            out.print("<td>" + (i+1) + "</td>");
            out.print("<td>" + user.getUserName()+ "</td>");
            out.print("<td><a href=\"/psdn/user/talk?uid="+user.getuId()+"\" target=\"right\">聊天</a></td>");
            out.print("<td><a href=\"/psdn/user/deleteFriend?uid="+user.getuId()+"\" target=\"right\">删除好友</a></td>");
            out.print("</tr>");
            i ++;
        }
        out.print("</table>");
    }

    /**
     *  我的好友：【删除好友】
     */
    public void deleteFriend(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        int uId1 = (int) req.getServletContext().getAttribute("uid");
        int uId2 = Integer.parseInt(req.getParameter("uid"));

        String mes = userService.deleteFriend(uId1, uId2);
        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }
}
