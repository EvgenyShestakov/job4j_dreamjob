package ru.job4j.dream.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Edit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws IOException {
        String param = req.getParameter("key");
        if ("post".equals(param)) {
            resp.sendRedirect(req.getContextPath() + "/post/edit.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/candidate/edit.jsp");
        }
    }
}
