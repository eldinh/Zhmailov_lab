package ru.sfedu.opencv;

import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import ru.sfedu.opencv.utils.ConfigUtils;

import java.util.*;

import static ru.sfedu.opencv.constants.Constants.LAB5_OUTPUT_PATH;
import static ru.sfedu.opencv.constants.Constants.PATH_TO_NATIVE_LIB_LINUX;

@Slf4j
public class Lab5 {

    public static final Random RANDOM = new Random();

    public static void main(String[] args) {
        System.load(ConfigUtils.getConfigProperty(PATH_TO_NATIVE_LIB_LINUX));
    }

    public static Mat defineRectangles(Mat srcImage, int height, int width) {
        var outputPath = ConfigUtils.getConfigProperty(LAB5_OUTPUT_PATH);

        var grayMatImage = new Mat();
        Imgproc.cvtColor(srcImage, grayMatImage, Imgproc.COLOR_BGR2GRAY);
        Imgcodecs.imwrite(outputPath + "grayImage.jpg", grayMatImage);

        var denoisingImage = new Mat();
        Photo.fastNlMeansDenoising(grayMatImage, denoisingImage);
        Imgcodecs.imwrite(outputPath + "denoisingImage.jpg", grayMatImage);

        var histogramEqualizationImageMat = new Mat();
        Imgproc.equalizeHist(denoisingImage, histogramEqualizationImageMat);
        Imgcodecs.imwrite(outputPath + "histogramImage.jpg", histogramEqualizationImageMat);

        var morhologicalOpeningImageMat = new Mat();
        var kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,5));
        Imgproc.morphologyEx(histogramEqualizationImageMat, morhologicalOpeningImageMat, Imgproc.MORPH_RECT,  kernel);

        Imgcodecs.imwrite(outputPath + "morphologicalOpening.jpg", morhologicalOpeningImageMat);

        var substractImage = new Mat();
        Core.subtract(histogramEqualizationImageMat, morhologicalOpeningImageMat, substractImage);
        Imgcodecs.imwrite(outputPath + "substractImage.jpg", substractImage);

        var thresholdImage = new Mat();
        var threshold = Imgproc.threshold(
                denoisingImage,
                thresholdImage,
                20, // в зависимости от картиночки этот параметр стоит менять, зависит от контраста цветов самой картинки
                255,
                Imgproc.THRESH_BINARY
        );
        thresholdImage.convertTo(thresholdImage, CvType.CV_8U);

        Imgcodecs.imwrite(outputPath + "thresholdImage.jpg", thresholdImage);
//
//        var edgeImage = new Mat();
//        Imgproc.Canny(thresholdImage, edgeImage, threshold, threshold * 3, 3, true);
//        Imgcodecs.imwrite(outputPath + "edgeImage.jpg", thresholdImage);

        var contours = new ArrayList<MatOfPoint>();
        var hierarchy = new Mat();
        Imgproc.findContours(thresholdImage, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));
        var rectanglesContours = new ArrayList<MatOfPoint>();
        for (int i = 0; i < contours.size(); i++) {
            if (i == 0) {
                continue;
            }
            var contour = contours.get(i);
            var point2f = new MatOfPoint2f();
            var approxContour2f = new MatOfPoint2f();
            var approxContour = new MatOfPoint();

            contour.convertTo(point2f, CvType.CV_32FC2);

            var arcLength = Imgproc.arcLength(point2f, true);
            Imgproc.approxPolyDP(point2f, approxContour2f, 0.01 * arcLength, true);

            approxContour2f.convertTo(approxContour, CvType.CV_32S);
            var rect = Imgproc.boundingRect(approxContour);

            if (rect.height < height || rect.width < width || point2f.total() != 4) {
                continue;
            }
            log.info("total - {} height - {}, width - {}, hash - {}",contour.hashCode() ,point2f.total(), rect.height, rect.width);
            rectanglesContours.add(contour);
            Imgproc.putText(
                    srcImage,
                    "" + contour.hashCode(),
                    new Point(rect.x + 1 , rect.y + (rect.height / 1.5)),
                    Imgproc.FONT_ITALIC,
                    0.5,
                    new Scalar(0, 0, 0),
                    1
            );
        }
        Imgproc.drawContours(srcImage, rectanglesContours, -1, new Scalar(0, 255, 0), 3);
        return srcImage;
    }

    public static Mat floodImage(Mat srcImage, Point seedPoint, Scalar scalarVal,  Scalar loDiff, Scalar upDiff) {
        var maskMat = new Mat();

        Imgproc.floodFill(
                srcImage,
                maskMat,
                seedPoint,
                Objects.isNull(scalarVal)
                        ? new Scalar(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255))
                        : scalarVal,
                new Rect(),
                Objects.isNull(loDiff)
                        ? new Scalar(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255))
                        : loDiff,
                Objects.isNull(upDiff)
                        ? new Scalar(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255))
                        : upDiff,
                Imgproc.FLOODFILL_FIXED_RANGE
        );

        return srcImage;
    }

    public static Mat purDownAndUp(Mat mat, int count) {
        Mat mask = mat.clone();
        for (int i = 0; i < count; i++) {
            Imgproc.pyrDown(mask, mask);
            Imgproc.pyrUp(mask, mask);
        }
        return mask;
    }
}
