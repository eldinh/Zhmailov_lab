package ru.sfedu.opencv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.opencv.utils.ConfigUtils;

import static ru.sfedu.opencv.Lab5.*;
import static ru.sfedu.opencv.constants.Constants.*;

public class Lab5Test extends BaseTest {

    @Test
    @DisplayName("Проверка метода заливки изображений")
    public void testFloodImage() {
        var initVal = 100;
        var path = ConfigUtils.getConfigProperty(LAB5_IMAGE_TO_FLOOD_PATH);
        Mat srcImage = Imgcodecs.imread(path);

        var seedPoint = new Point(0, 0);
        var newScalarVal = new Scalar(0, 255, 0);
        var loScalarDiff = new Scalar(initVal, initVal, initVal);
        var upScalarDiff = new Scalar(initVal, initVal, initVal);

        Mat image = floodImage(srcImage, seedPoint, newScalarVal, loScalarDiff, upScalarDiff);

        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB5_OUTPUT_PATH) + "floodImage.jpg", image);
    }

    @Test
    @DisplayName("Проверка метода для понижающей и повышающей пирамиды")
    public void testPurDownAndUp() {
        Mat srcImage = Imgcodecs.imread(ConfigUtils.getConfigProperty(LAB5_IMAGE_TO_PUR_PATH));

        Mat mat = purDownAndUp(srcImage, 100);
        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB5_OUTPUT_PATH) + "PURED.jpg", mat);
    }

    @Test
    @DisplayName("Проверка метода для идентификации прямоугольника")
    public void testFindRectangle() {
        Mat srcImage = Imgcodecs.imread(ConfigUtils.getConfigProperty(LAB5_IMAGE_TO_FIND_RECT_PATH));

        Mat mat = defineRectangles(srcImage, 4, 2);
        Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB5_OUTPUT_PATH) + "rectangles.jpg", mat);
    }
}
