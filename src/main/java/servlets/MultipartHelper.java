package servlets;

import com.google.common.io.ByteStreams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;

public class MultipartHelper {

    public static class Image {
        private final String imageName;
        private final byte[] imageContent;

        public Image(String imageName, byte[] imageContent) {
            this.imageName = imageName;
            this.imageContent = imageContent;
        }

        public String getImageName() {
            return imageName;
        }

        public byte[] getImageContent() {
            return imageContent;
        }
    }

    public Optional<Image> getImageFromRequest(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("picture");
        if (imageExists(filePart)) {
            String fileName = getSubmittedFileName(filePart);
            byte[] imageContent = ByteStreams.toByteArray(filePart.getInputStream());
            return Optional.of(new Image(fileName, imageContent));
        } else {
            return Optional.empty();
        }
    }

    private boolean imageExists(Part filePart) {
        String submittedName = getSubmittedFileName(filePart);
        return submittedName != null && !submittedName.isEmpty();
    }

    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }
}
