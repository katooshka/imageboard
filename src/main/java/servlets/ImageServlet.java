package servlets;

import data.ImageDao;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    @Inject
    private ImageDao imageDao;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String file = request.getPathInfo().split("/")[1];
        if (imageDao.checkImageExists(file)) {
            String[] splitFile = file.split("\\.");
            String fileExtension = splitFile[splitFile.length - 1];
            response.setContentType("image/" + fileExtension);
            byte[] bytes = imageDao.getImageBytes(file);
            response.getOutputStream().write(bytes);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
