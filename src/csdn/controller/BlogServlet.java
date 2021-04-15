package csdn.controller;

import com.alibaba.fastjson.JSON;
import csdn.po.Blog;
import csdn.po.Comment;
import csdn.service.ServiceFactory;
import csdn.service.UserService;
import csdn.util.IDUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/blog/*")
public class BlogServlet extends BaseServlet {
    private UserService userService = ServiceFactory.getUserService();

    /**
     * 上传图片到服务器
     * @param req
     * @param resp 返回图片url
     * @throws IOException
     */
    public void imgUpload(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String savePath = "C:\\Users\\86135\\Desktop\\repository\\web\\img";
        savePath = savePath.replace("\\", "\\\\");
        File file = new File(savePath);
        String url = "";//返回存储路径

        //判断上传文件的保存目录是否存在
        if (!file.exists()) {
            //目录不存在需要创建目录
            file.mkdir();
        }
        //上传提示消息
        String message = "上传失败", str = "";
        try {
            //使用apache文件上传组件处理文件上传步骤
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //4、使用ServletFileUpload解析器上传数据，解析结果返回的是一个List<FileItem>集合
            //	每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(req);
            for (FileItem item : list) {
                String filename = item.getName();
                System.out.println(filename);
                if (filename == null || filename.trim().equals("")) {
                    continue;
                }
                filename = filename.substring(filename.lastIndexOf(".") + 1);
                //给文件重新取一个名字UUID
                filename = IDUtil.getId() + "." + filename;
                //获取item中的上传文件的输入流
                InputStream in = item.getInputStream();
                //创建一个文件输出流
                FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                //创建一个缓冲区
                byte[] buffer = new byte[1024];
                //判断输入流中的数据是否已经读完的标识
                int len = 0;
                //循环将输入流读入到缓冲区当中，
                while ((len = in.read(buffer)) > 0) {
                    //使用FileOutputStream输入流将缓冲区的数据写入到指定的目录(savePath + "\\" +filename)
                    out.write(buffer, 0, len);
                }
                //这三句代码之及其重要的，就是为了返回JSON数据做准备的
                str = "http://localhost:8080" + req.getContextPath() + "/img/" + filename;
                message = "上传成功";

                //关闭流
                in.close();
                out.close();
                //删除处理文件上传时生成的临时文件
                item.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String resultJSON = "{ \"url\" : \"" + str + "\"," +
                "\"msg\": \"" + message + "\"" +
                "}";
        resp.getWriter().write(resultJSON);//返回url地址
    }

    /**
     * 创建博客
     * @param req
     * @param resp 创建是否成功
     * @throws IOException
     */
    public void upload(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        String mes = "上传失败";

        String title = req.getParameter("title");
        String tag = req.getParameter("tag");
        String imgUrl = req.getParameter("imgUrl");
        String content = req.getParameter("content");
        String isPublic = req.getParameter("isPublic");
        int uId = (int) req.getServletContext().getAttribute("uid");

        if(userService.createBlog(new Blog(uId,content,tag,isPublic,title,imgUrl)) == 1){
            mes = "上传成功";
        }

        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }

    /**
     * 管理博客
     * @param req
     * @param resp
     * @throws IOException
     */
    public void checkBlog(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");

        int uId = (int) req.getServletContext().getAttribute("uid");
        ArrayList<Blog> blogArrayList = userService.checkBlog(uId);
        PrintWriter out;

        int i = 0;
        out = resp.getWriter();
        out.print("<table border='2' align='center'>");
        out.print("<tr>");
        out.print("<td>博客编号</td>");
        out.print("<td>博客标题</td>");
        out.print("<td>博客分类</td>");
        out.print("<td>是否公开</td>");
        out.print("<td>博客收藏数</td>");
        out.print("<td>博客点赞数</td>");
        out.print("<td>操作</td>");
        out.print("<td>操作</td>");
        out.print("</tr>");
        for (i = 0; i<blogArrayList.size(); i++) {
            Blog blog = blogArrayList.get(i);
            out.print("<tr>");
            out.print("<td>" + (i+1) + "</td>");
            out.print("<td>" + blog.getTitle()+ "</td>");
            out.print("<td>" + blog.getTag()+ "</td>");
            out.print("<td>" + blog.getPublic() + "</td>");
            out.print("<td>" + blog.getnOfCon() + "</td>");
            out.print("<td>" + blog.getnOfLike() + "</td>");
            out.print("<td><a href=\"http://localhost:8080/psdn/view_blog.html?blogId="+blog.getbId()+"\" target=\"right\">查看博客</a></td>");
            out.print("<td><a href='/psdn/blog/delete?blogId="+blog.getbId()+"'>删除博客</a></td>");
            out.print("</tr>");
        }
        out.print("</table>");
    }

    /**
     * 查看博客
     * @param req
     * @param resp
     * @throws IOException
     */
    public void viewBlog(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        int bId = Integer.parseInt(req.getParameter("blogId"));
        Blog blog = userService.viewBlog(bId);
        Map<String,String> map = new HashMap<>();

        StringBuffer imgUrl = new StringBuffer();
        imgUrl.append("[");
        imgUrl.append(blog.getImgUrl());
        imgUrl.append("]");
        map.put("imgUrl",imgUrl.toString());
        map.put("title",blog.getTitle());
        map.put("tag",blog.getTag());
        map.put("content",blog.getbContent());
        map.put("isPublic",blog.getPublic());
        map.put("likeNum", String.valueOf(blog.getnOfLike()));
        map.put("conNum", String.valueOf(blog.getnOfCon()));

        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSONString(map));
    }

    /**
     * 更新自己博客
     * @param req
     * @param resp
     * @throws IOException
     */
    public void updateBlog(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        String mes = "更改失败";

        String title = req.getParameter("title");
        String tag = req.getParameter("tag");
        String imgUrl = req.getParameter("imgUrl");
        String content = req.getParameter("content");
        String isPublic = req.getParameter("isPublic");
        int bId = Integer.parseInt(req.getParameter("blogId"));

        if(userService.createBlog(new Blog(bId, null, content, tag, null, null, isPublic, null, title, imgUrl)) == 1){
            mes = "更改成功";
        }

        PrintWriter out = resp.getWriter();
        out.print("<font style='color:red;font-size:40'>"+mes+"</font>");
    }

    /**
     * 检索他人博客
     * @param req
     * @param resp
     * @throws IOException
     */
    public void findBlog(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        String userName = req.getParameter("username");
        String title = req.getParameter("title");
        String tag = req.getParameter("tag");
        String index = req.getParameter("index");
        ArrayList<Blog> blogArrayList = userService.findBlog(userName,tag,title,index);
        int indexInt = Integer.parseInt(index);

        PrintWriter out;

        int i = 0;
        out = resp.getWriter();
        out.print("<table border='2' align='center'>");
        out.print("<tr>");
        out.print("<td>博客编号</td>");
        out.print("<td>博客标题</td>");
        out.print("<td>博客分类</td>");
        out.print("<td>博客收藏数</td>");
        out.print("<td>博客点赞数</td>");
        out.print("<td>操作</td>");
        out.print("</tr>");
        for (i = 0; i<blogArrayList.size(); i++) {
            Blog blog = blogArrayList.get(i);
            out.print("<tr>");
            out.print("<td>" + (i+1) + "</td>");
            out.print("<td>" + blog.getTitle()+ "</td>");
            out.print("<td>" + blog.getTag()+ "</td>");
            out.print("<td>" + blog.getnOfCon() + "</td>");
            out.print("<td>" + blog.getnOfLike() + "</td>");
            out.print("<td><a href=\"http://localhost:8080/psdn/view_other_blog.html?blogId="+blog.getbId()+"\" target=\"right\">查看博客</a></td>");
            out.print("</tr>");
        }
        out.print("<tr>");
        if (indexInt != 0){
            out.print("<td><a href=\"/psdn/blog/findBlog?username="+userName+"&title="+title+"&tag="+tag+"&index="+(indexInt-6)+"\" target=\"right\">上一页</a></td>");
        }
        if (i == 6){
            out.print("<td><a href=\"/psdn/blog/findBlog?username="+userName+"&title="+title+"&tag="+tag+"&index="+(indexInt+6)+"\" target=\"right\">下一页</a></td>");
        }
        out.print("</table>");
    }

    /**
     * 点赞博客
     * @param req
     * @param resp
     * @throws IOException
     */
    public void like(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/json;charset=UTF-8");
        int uId = (int) req.getServletContext().getAttribute("uid");
        int bId = Integer.parseInt(req.getParameter("blogId"));

        String mes = userService.changeLikeNum(uId,bId);
        Map map = new HashMap();
        map.put("mes",mes);
        resp.getWriter().print(JSON.toJSONString(map));
    }

    /**
     * 收藏博客
     * @param req
     * @param resp
     * @throws IOException
     */
    public void collection(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/json;charset=UTF-8");
        int uId = (int) req.getServletContext().getAttribute("uid");
        int bId = Integer.parseInt(req.getParameter("blogId"));

        String mes = userService.changeConNum(uId,bId);
        Map map = new HashMap();
        map.put("mes",mes);
        resp.getWriter().print(JSON.toJSONString(map));
    }
}
