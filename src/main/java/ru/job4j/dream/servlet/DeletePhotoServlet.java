package ru.job4j.dream.servlet;

import ru.job4j.dream.store.DbStore;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeletePhotoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        for (File file : new File(DbStore.instOf().getProperties().
                getProperty("path")).listFiles()) {
            String[] strings = file.getName().split("\\.");
            if (strings[0].equals(id)) {
                file.delete();
                break;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
