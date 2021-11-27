package ru.job4j.dream.servlets;

import ru.job4j.dream.store.DbStore;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeletePhotoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        for (File file : new File(DbStore.instOf().getProperties().
                getProperty("path")).listFiles()) {
            String[] strings;
            strings = file.getName().split("/");
            String s = strings[strings.length - 1].substring(0, 1);
            if (s.equals(id)) {
                file.delete();
                break;
            }
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/candidates.do");
        dispatcher.forward(req, resp);
    }
}
