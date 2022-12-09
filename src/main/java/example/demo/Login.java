// src/main/java/example/demo/Login.java
package example.demo;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Login",
        urlPatterns = "/password"
)
public class Login extends HttpServlet {
    private String message;

    public void init() {
        message = "欢迎登陆";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("<pre><h3>");
        // String getMethod():获取请求方式：GET
        String method = req.getMethod();
        printWriter.write("method:" + method);
        printWriter.write("\n");
        // String getContextPath():获取虚拟目录(项目访问路径)：/webapp
        String contextPath = req.getContextPath();
        printWriter.write("contextPath:" + contextPath);
        printWriter.write("\n");
        // StringBuffer
        // getRequestURL():获取URL(统一资源定位符)：http://localhost:8080/webapp/Login
        StringBuffer url = req.getRequestURL();
        printWriter.write("url:" + url.toString());
        printWriter.write("\n");
        // String getRequestURI()：获取URI(统一资源标识符)： /webapp/Login
        String uri = req.getRequestURI();
        printWriter.write("uri:" + uri);
        printWriter.write("\n");
        // String getQueryString()：获取请求参数（GET方式）： username=admin
        String queryString = req.getQueryString();
        printWriter.write("queryString:" + queryString);
        printWriter.write("\n");

        printWriter.write(message);

        //获取ServletConfig
        ServletConfig servletConfig = getServletConfig();
        String name = servletConfig.getServletName();
        printWriter.write("Servlet的名称是"+name);
        printWriter.write("\n");
        String arg1 = servletConfig.getInitParameter("arg1");
        printWriter.write("arg1：的值为"+arg1);
        printWriter.write("\n");

        // 获得ServletContext
        ServletContext servletContext = servletConfig.getServletContext();
        String arg2 = servletContext.getInitParameter("arg2");
        printWriter.write("arg2：的值为"+arg2);

        printWriter.write("</h3></pre>");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("Post方法" + message);
    }
}
