package servlets;

import data.BoardDao;
import data.ImageDao;
import data.PostsDao;
import data.ThreadDao;
import entities.Post;
import org.joda.time.Instant;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/thread")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 10)
public class ThreadServlet extends HttpServlet {
    @Inject
    private MultipartHelper multipartHelper;

    @Inject
    private PostsDao postsDao;

    @Inject
    private BoardDao boardDao;

    @Inject
    private ThreadDao threadDao;

    @Inject
    ImageDao imageDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int threadId = Integer.parseInt(request.getParameter("id"));
        List<Post> posts = postsDao.selectPostsByThread(threadId);
        MessageFormatter postMessageFormatter = new MessageFormatter();
        request.setAttribute("board", threadDao.getBoardByThreadId(threadId));
        request.setAttribute("links", boardDao.getBoards());
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
        Integer threadId = Integer.parseInt(request.getParameter("thread-id"));
        Instant postTime = Instant.now();
        String author = request.getParameter("author");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");


        Optional<MultipartHelper.Image> imageOptional = multipartHelper.getImageFromRequest(request);
        String imagePath = null;
        if (imageOptional.isPresent()) {
            imagePath = imageDao.findFreeImageName(imageOptional.get().getImageName());
            imageDao.saveImage(imagePath, imageOptional.get().getImageContent());
        }

        Post post = Post.builder()
                .setThreadId(threadId)
                .setPostTime(postTime)
                .setAuthor(author)
                .setSubject(subject)
                .setMessage(message)
                .setImagePath(imagePath)
                .build();

        postsDao.insertPost(post);
        response.sendRedirect(response.encodeRedirectURL(String.format("/thread?id=%d", threadId)));
    }
}