package ru.sfedu.opencv;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import ru.sfedu.opencv.utils.ConfigUtils;

import static ru.sfedu.opencv.constants.Constants.*;

public class Lab4 {

	public static void main(String[] args) {
		System.load(ConfigUtils.getConfigProperty( PATH_TO_NATIVE_LIB_LINUX));
	}

	public static void filterImage(String imagePath, int ksize) {
		var srcImage = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_COLOR);
		var dst = new Mat();

		// усредняющий
		Imgproc.blur(srcImage, dst, new Size(ksize, ksize));
		Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB4_OUTPUT_PATH) + "blur" + ksize + ".jpg", dst);

		var dst2 = new Mat();

		// Гауссовский фильтр
		var sigma = 0.3 * ((ksize - 1) * 0.5 -1) + 0.8;
		Imgproc.GaussianBlur(srcImage, dst2, new Size(ksize, ksize), 0);
		Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB4_OUTPUT_PATH) + "gaussian" + ksize + ".jpg", dst2);

		var dst3 = new Mat();

		// Медианное сглаживание
		Imgproc.medianBlur(srcImage, dst3, ksize);
		Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB4_OUTPUT_PATH) + "median" + ksize + ".jpg", dst3);

		var dst4 = new Mat();

		// Двусторонняя
		Imgproc.bilateralFilter(srcImage, dst4,ksize, 250, 50);
		Imgcodecs.imwrite(ConfigUtils.getConfigProperty(LAB4_OUTPUT_PATH) + "bileteral" + ksize + ".jpg", dst4);
	}

	public static void morphologyTest(String imagePath, int ksize, MORPH shape) {
		var outputPath = ConfigUtils.getConfigProperty(LAB4_OUTPUT_PATH);
		var srcImage = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_COLOR);

		var element = Imgproc.getStructuringElement(shape.getShape(), new Size(ksize, ksize));

		var dst = new Mat();
		Imgproc.erode(srcImage, dst, element);
		Imgcodecs.imwrite(outputPath + shape.name() +  "_erode_" + ksize + ".jpg", dst);

		var dst5 = new Mat();
		Imgproc.dilate(srcImage, dst5, element);
		Imgcodecs.imwrite(outputPath + shape.name() +  "_dilate_" + ksize + ".jpg", dst5);
	}
 }
