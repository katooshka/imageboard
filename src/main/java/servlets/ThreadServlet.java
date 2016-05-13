package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/thread")
public class ThreadServlet extends HttpServlet {

    String threadId = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        threadId = request.getParameter("id");
        List<Post> posts = PostsDao.selectPostsByThread(request.getParameter("id"));
        for (Post post : posts) {
            post.setMessage(addLinks(post.getMessage()));
        }
        request.setAttribute("posts", posts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/thread.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameterMap());
        String name = request.getParameter("name");
        String text = request.getParameter("message_input");
        Date now = new Date();
        PostsDao.insertNewPost(new Post(name, text, now, Integer.parseInt(threadId)));
        PostsDao.updateThreadLength(Integer.parseInt(threadId));
        response.setContentType("text/html; charset=UTF-8");
        request.setAttribute("posts", PostsDao.selectPostsByThread(request.getParameter("id")));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/thread.jsp");
        dispatcher.forward(request, response);
    }

    private String addLinks(String post) {
        return post.replaceAll(">>([0-9]+)", "<a class=\"postLink\" href=\"#$1\" ref_id=\"post$1\">&gt;&gt;$1</a>");
    }
}