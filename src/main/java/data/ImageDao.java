package data;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

public class ImageDao {

    private static final int IMAGE_NAME_BOUND = 1000000;

    @Resource(mappedName = "imagesStorageFolder")
    private String imageStoragePath;

    public boolean checkImageExists(String fileName) {
        return Files.exists(Paths.get(imageStoragePath, fileName));
    }

    public byte[] getImageBytes(String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(imageStoragePath, fileName));
    }

    public void saveImage(String imageName, byte[] imageContent) throws IOException {
        checkArgument(!checkImageExists(imageName));
        Files.write(Paths.get(imageStoragePath, imageName), imageContent);
    }

    public String findFreeImageName(String originalFileName) {
        String[] split = originalFileName.split("\\.");
        String extension = split[split.length - 1];
        Random random = new Random();
        String fileName;
        do {
            fileName = String.valueOf(random.nextInt(IMAGE_NAME_BOUND)) + "." + extension;
        } while (Files.exists(Paths.get(imageStoragePath, fileName)));
        return fileName;
    }
}
