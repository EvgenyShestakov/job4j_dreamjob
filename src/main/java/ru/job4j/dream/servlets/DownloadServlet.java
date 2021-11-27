package ru.job4j.dream.servlets;

import ru.job4j.dream.store.MemStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws IOException {
        File users = null;
        String id = req.getParameter("name");
        for (File file : new File(MemStore.instOf().getProperties().
                getProperty("path")).listFiles()) {
            String[] strings = file.getName().split("/");
            if (strings[strings.length - 1].substring(0, 1).equals(id)) {
                users = file;
                break;
            }
        }
        try (FileInputStream stream = new FileInputStream(users)) {
            resp.getOutputStream().write(stream.readAllBytes());
        }
    }
}