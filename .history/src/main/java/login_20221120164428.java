
import java.io.*;
import javax.servlet.http.*;

public class Login extends HttpServlet {
    private String message;

    public void init() {
        message = "欢迎登陆";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
    }
}