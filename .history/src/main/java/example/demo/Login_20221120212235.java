// src/main/java/example/demo/Login.java
package example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javafx.scene.chart.PieChart;

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
        printWriter.write("<pre><h3>");
        String method = req.getMethod();
        printWriter.write(method);
        printWriter.write("\n");
        // String getContextPath():获取虚拟目录(项目访问路径)：/webapp
        String contextPath = req.getContextPath();
        printWriter.write(contextPath);
        printWriter.write("\n");
        // StringBuffer
        // getRequestURL():获取URL(统一资源定位符)：http://localhost:8080/webapp/Login
        StringBuffer url = req.getRequestURL();
        printWriter.write(url.toString());
        printWriter.write("\n");
        // String getRequestURI()：获取URI(统一资源标识符)： /webapp/Login
        String uri = req.getRequestURI();
        printWriter.write(uri);
        printWriter.write("\n");
        // String getQueryString()：获取请求参数（GET方式）： username=admin
        String queryString = req.getQueryString();
        printWriter.write(queryString);
        printWriter.write("\n");

        printWriter.write(message);
        printWriter.write("</h3></pre>");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("Post方法" + message);
    }
}