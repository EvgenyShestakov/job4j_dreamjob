package ru.job4j.dream.servlet;

import org.junit.Test;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.DbStore;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class MockitoServletTests {
    @Test
    public void whenPostServletGet() throws IOException, ServletException {
        PostServlet postServlet = new PostServlet();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        req.setAttribute("posts",  DbStore.instOf().findAllPosts());
        when(req.getRequestDispatcher("posts.jsp")).thenReturn(dispatcher);
        postServlet.doGet(req, resp);
        verify(req, times(1)).getRequestDispatcher("posts.jsp");
        verify(dispatcher).forward(req, resp);
    }

    @Test
    public void whenPostServletPost() throws IOException {
        PostServlet postServlet = new PostServlet();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("name of the post");
        DbStore.instOf().saveCandidate(new Candidate(Integer.parseInt(req.
                getParameter("id")), req.getParameter("name")));
        postServlet.doPost(req, resp);
        verify(resp, times(1)).sendRedirect(req.getContextPath() + "/posts.do");

    }

    @Test
    public void whenCandidateServletGet() throws IOException, ServletException {
        CandidateServlet candidateServlet = new CandidateServlet();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        req.setAttribute("candidates",  DbStore.instOf().findAllCandidates());
        when(req.getRequestDispatcher("candidates.jsp")).thenReturn(dispatcher);
        candidateServlet.doGet(req, resp);
        verify(req, times(1)).getRequestDispatcher("candidates.jsp");
        verify(dispatcher).forward(req, resp);
    }

    @Test
    public void whenCandidateServletPost() throws IOException {
        CandidateServlet candidateServlet = new CandidateServlet();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("name of the candidate");
        DbStore.instOf().saveCandidate(new Candidate(Integer.parseInt(req.
                getParameter("id")), req.getParameter("name")));
        candidateServlet.doPost(req, resp);
        verify(resp, times(1)).sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
