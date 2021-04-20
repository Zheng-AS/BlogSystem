package csdn.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VisitorFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = null;
        //1.调用请求对象读取请求包中请求行中URL，了解用户访问的资源文件是谁
        String uri = String.valueOf(request.getRequestURL());
        //2.如果本次请求资源文件与登录相关【login.html   或 LoginServlet 或 register.html】此时应无条件放行
        if(uri.indexOf("login") != -1 || "http://localhost:8080/psdn/".equals(uri) || uri.indexOf("register") != -1){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //3.如果本次请求资源文件与进入博客页面相关【view_other_blog.html 等】此时应无条件放行
        if(uri.indexOf("view_other_blog.html") != -1 || uri.indexOf("index.html") != -1 || uri.indexOf("top.html") != -1 || uri.indexOf("left.html") != -1 ||uri.indexOf("tips.html") != -1){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //4.如果本次请求资源文件与查看博客相关信息相关【查询博客 查看博客 查看博客评论】此时应无条件放行
        if(uri.indexOf("blog/findBlog") != -1 || uri.indexOf("blog/viewBlog") != -1 || uri.indexOf("comment/viewComment") != -1){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //5.如果本次请求资源文件与查看博客图片相关【img/ 或其他资源】此时应无条件放行
        if(uri.indexOf(".jpg") != -1 || uri.indexOf(".js") != -1 || uri.indexOf(".css") != -1 || uri.indexOf(".png") != -1){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        //6.如果本次请求访问的是其他资源文件，需要得到用户在服务端HttpSession
        session = request.getSession(false);

        if(session != null){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //7.做拒绝请求
        request.getRequestDispatcher("tips.html").forward(servletRequest,servletResponse);
    }
}
