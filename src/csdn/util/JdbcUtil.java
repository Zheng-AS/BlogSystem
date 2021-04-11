package csdn.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Iterator;
import java.util.Map;

public class JdbcUtil {
        public JdbcUtil(){ }
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //final String URL = "jdbc:mysql:localhost:3306/";

        /**
         * 静态代码块 当每次创建此类时自动获取连接且不用重复获取连接
         */
        static {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        /**
         * 获取数据库连接对象
         * @return 连接对象
         * @throws SQLException 抛出异常
         */
        public  static Connection getConnection() throws SQLException {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/blog_system?useUnicode=true&characterEncoding=utf-8", "root", "tuofeiruou");
        }

        public Connection getConnection(HttpServletRequest request){
            //1.通过请求对象，得到全局作用域对象
            ServletContext application = request.getServletContext();
            //2.从全局作用域对象得到Map
            Map map = (Map) application.getAttribute("key1");
            //3.从map得到一个处于空闲状态Connection
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()){
                con = (Connection) iterator.next();
                boolean flag = (boolean)map.get(con);
                if(false == true){
                    map.put(con,false);
                    break;
                }
            }
            return con;
        }

        public PreparedStatement createStatement(String sql, HttpServletRequest request){
            try {
                ps = getConnection(request).prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ps;
        }

        public PreparedStatement createStatement(String sql){
            try {
                con = getConnection();
                ps = con.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ps;
        }

        /**
         * 关闭资源
         */
        public void close(){
            if (rs != null){
                try{
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }/**
         * 关闭资源
         */
        public void close(ResultSet rs){
            this.rs = rs;
            this.close();
        }

        public void commit() throws SQLException {
            con.commit();
        }

        public void close(HttpServletRequest request){
            if (rs != null){
                try{
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            ServletContext application = request.getServletContext();
            Map map = (Map) application.getAttribute("key1");
            map.put(con,true);
        }
}
