package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        List<ThreadPreview> threads = PostsDao.selectThreadPreviews(request.getParameter("name"));
        for (ThreadPreview thread : threads) {
            for (Post post : thread.getTailPosts()){
                post.setMessage(addLinks(post.getMessage()));
            }
        }
        request.setAttribute("threads", threads);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/board.jsp");
        dispatcher.forward(request, response);
    }

    private String addLinks(String post) {
        return post.replaceAll(">>([0-9]+)", "<a class=\"postLink\" href=\"#$1\" ref_id=\"post$1\">&gt;&gt;$1</a>");
    }
}

