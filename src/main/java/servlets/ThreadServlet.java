package servlets;

import data.PostsDao;
import entities.Post;
import org.joda.time.Instant;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/thread")
public class ThreadServlet extends HttpServlet {
    private final PostsDao postsDao;

    @Inject
    public ThreadServlet(PostsDao postsDao) {
        this.postsDao = postsDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int threadId = Integer.parseInt(request.getParameter("id"));
        List<Post> posts = postsDao.selectPostsByThread(threadId);
        MessageFormatter postMessageFormatter = new MessageFormatter();
        request.setAttribute("posts", posts);
        request.setAttribute("threadId", threadId);
        request.setAttribute("formatter", postMessageFormatter);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/thread.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int threadId = Integer.parseInt(request.getParameter("thread-id"));
        String author = request.getParameter("author");
        String message = request.getParameter("message");
        Instant postDate = Instant.now();
        Post post = Post.builder()
                .setThreadId(threadId)
                .setPostTime(postDate)
                .setAuthor(author)
                .setMessage(message)
                .build();
        postsDao.insertPost(post);

        response.sendRedirect(response.encodeRedirectURL(String.format("/thread?id=%d", threadId)));
    }
}