// src/main/java/example/demo/Login.java
package example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/password")
public class Login extends HttpServlet {
    private String message;

    public void init() {
        message = "欢迎登陆";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // String getMethod():获取请求方式：GET
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();
        String method = req.getMethod();
        printWriter.write(method);
        printWriter.write("\n");
        // String getContextPath():获取虚拟目录(项目访问路径)：/webapp
        String contextPath = req.getContextPath();
        // StringBuffer
        // getRequestURL():获取URL(统一资源定位符)：http://localhost:8080/webapp/
        StringBuffer url = req.getRequestURL();
        // String getRequestURI()：获取URI(统一资源标识符)： /request-demo/req1
        String uri = req.getRequestURI();
        // String getQueryString()：获取请求参数（GET方式）： username=zhangsan
        String queryString = req.getQueryString();

        resp.getWriter().write(message);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("Post方法" + message);
    }
}