package ru.sfedu.opencv;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.opencv.utils.ConfigUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;

import static ru.sfedu.opencv.Lab2.showImage;
import static ru.sfedu.opencv.constants.Constants.LAB2_IMAGE_PATH;
import static ru.sfedu.opencv.constants.Constants.LAB2_OUTPUT_PATH;

public class Lab2Test extends BaseTest {

    @Test
    @SneakyThrows
    @DisplayName("Показать картинку")
    public void testShowImage() {
        var srcImage = Imgcodecs.imread(ConfigUtils.getConfigProperty(LAB2_IMAGE_PATH));

        var type = srcImage.channels() > 1
                ? BufferedImage.TYPE_3BYTE_BGR
                :BufferedImage.TYPE_BYTE_GRAY;

        JFrame frame = showImage(srcImage, type);

        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB2_OUTPUT_PATH) + "image.jpg", srcImage);
        while (frame.isActive()) {}
    }
}
