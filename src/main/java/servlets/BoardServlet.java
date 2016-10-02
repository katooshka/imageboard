package servlets;

import data.BoardDao;
import data.ImageDao;
import data.ThreadDao;
import data.ThreadPreviewDao;
import entities.Board;
import entities.Post;
import entities.ThreadPreview;
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

import static com.google.common.base.Preconditions.checkArgument;

@WebServlet("/board")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 10)
public class BoardServlet extends HttpServlet {
    private final ThreadPreviewDao threadPreviewDao;
    private final ThreadDao threadDao;
    private final BoardDao boardDao;

    @Inject
    public BoardServlet(ThreadPreviewDao threadPreviewDao,
                        ThreadDao threadDao,
                        BoardDao boardDao) {
        this.threadPreviewDao = threadPreviewDao;
        this.threadDao = threadDao;
        this.boardDao = boardDao;
    }

    @Inject
    private MultipartHelper multipartHelper;

    @Inject
    ImageDao imageDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int boardId = Integer.parseInt(request.getParameter("id"));
        Board board = boardDao.getBoardById(boardId);
        List<ThreadPreview> threads = threadPreviewDao.getThreadsPreviews(boardId);
        MessageFormatter postMessageFormatter = new MessageFormatter();
        request.setAttribute("links", boardDao.getBoards());
        request.setAttribute("board", board);
        request.setAttribute("threads", threads);
        request.setAttribute("boardId", boardId);
        request.setAttribute("postMessageFormatter", postMessageFormatter);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/board.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int boardId = Integer.parseInt(request.getParameter("board-id"));
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

        Post opPost = Post.builder()
                .setPostTime(postTime)
                .setAuthor(author)
                .setSubject(subject)
                .setMessage(message)
                .setImagePath(imagePath)
                .build();

        threadDao.createNewThread(opPost, boardId);
        response.sendRedirect(response.encodeRedirectURL(String.format("/board?id=%d", boardId)));
    }
}

