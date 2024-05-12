package ru.sfedu.opencv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.opencv.utils.ConfigUtils;

import java.util.List;

import static ru.sfedu.opencv.Lab3.*;
import static ru.sfedu.opencv.constants.Constants.*;

public class Lab3Test extends BaseTest {

    @Test
    @DisplayName("Проверка метода обработки изображения с помощью операторов Собеля и Лапласа")
    public void testSobelAndLaplacianKernelImage() {
        var imagePath = ConfigUtils.getConfigProperty(LAB3_IMAGE_PATH);

        var mat = createSobelAndLaplacianKernelImage(imagePath, CvType.CV_32F, 2, 1);

        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB3_OUTPUT_PATH) + "solbel.jpg", mat);
    }

    @Test
    @DisplayName("Проверка метода отзеркаливания изображений")
    public void testCreateMirrorKernelImage() {
        var imagePath = ConfigUtils.getConfigProperty(LAB3_IMAGE_PATH);

        Mat mat = createMirrorKernelImage(imagePath);

        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB3_OUTPUT_PATH) + "mirroredImg.jpg", mat);
    }

    @Test
    @DisplayName("Проверка метода изменения размера изображения")
    public void testCreateChangedSizeImage() {
        var imagePath = ConfigUtils.getConfigProperty(LAB3_IMAGE_PATH);

        var mat = createChangedSizeImage(imagePath, 100, 100);
        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB3_OUTPUT_PATH) + "sizeChangedImage.jpg", mat);

    }

    @Test
    @DisplayName("Проверка метода для повтора изображения")
    public void testCreateRepeatedImage() {
        var imagePath = ConfigUtils.getConfigProperty(LAB3_IMAGE_PATH);

        var imread = repeatImage(imagePath, 3, 3);
        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB3_OUTPUT_PATH) + "repeated.jpg", imread);
    }

    @Test
    @DisplayName("Проверка метода для объединения изображений")
    public void testConcatImageMethod() {

        var mat1 = createChangedSizeImage(ConfigUtils.getConfigProperty(LAB3_IMAGE_PATH), 400, 800);
        var mat2 = createChangedSizeImage(ConfigUtils.getConfigProperty(LAB3_IMAGE2_PATH), 400, 800);

        var hmat = new Mat();
        Core.hconcat(List.of(mat1, mat2), hmat);

        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB3_OUTPUT_PATH) + "hConcat.jpg", hmat);

        var vmat = new Mat();
        Core.vconcat(List.of(mat1, mat2), vmat);

        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB3_OUTPUT_PATH) + "vConcat.jpg", vmat);
    }

    @Test
    @DisplayName("Проверка метода для вращения исходного изображения")
    public void testCreateRotatedImage() {
        var imagePath = ConfigUtils.getConfigProperty(LAB3_IMAGE_PATH);
        var rotatedImage = createRotatedImage(imagePath, -75, true);
        var rotatedImage1 = createRotatedImage(imagePath, -75, false);

        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB3_OUTPUT_PATH) + "rorated.jpg", rotatedImage);
        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB3_OUTPUT_PATH) + "rorated1.jpg", rotatedImage1);
    }

    @Test
    @DisplayName("Проверерка метода для сдвига исходного изображения")
    public void testMoveImageFromCenter() {
        var imagePath = ConfigUtils.getConfigProperty(LAB3_IMAGE_PATH);

        var mat = moveImageFromCenter(imagePath, 0, 1000);
        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB3_OUTPUT_PATH) + "moved.jpg", mat);
    }

    @Test
    public void testChangeImagePerspective() {
        var imagePath = ConfigUtils.getConfigProperty(LAB3_IMAGE_PATH);

        Mat mat = transformImagePerspective(imagePath);

        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB3_OUTPUT_PATH) + "persp.jpg", mat);


    }
}
