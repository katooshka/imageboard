package servlets;

import data.*;
import entities.Board;
import entities.Post;
import entities.ThreadPreview;
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

@WebServlet("/board")
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int boardId = Integer.parseInt(request.getParameter("id"));
        Board board = boardDao.getBoardById(boardId);
        List<ThreadPreview> threads = threadPreviewDao.getThreadsPreviews(boardId);
        MessageFormatter postMessageFormatter = new MessageFormatter();
        request.setAttribute("board", board);
        request.setAttribute("threads", threads);
        request.setAttribute("boardId", boardId);
        request.setAttribute("postMessageFormatter", postMessageFormatter);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/board.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int boardId = Integer.parseInt(request.getParameter("board-id"));
        Instant postTime = Instant.now();
        String author = request.getParameter("author");
        String message = request.getParameter("message");
        Post opPost = Post.builder()
                .setPostTime(postTime)
                .setAuthor(author)
                .setMessage(message)
                .build();
        threadDao.createNewThread(opPost, boardId);

        response.sendRedirect(response.encodeRedirectURL(String.format("/board?id=%d", boardId)));
    }
}

