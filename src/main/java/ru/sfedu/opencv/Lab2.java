package ru.sfedu.opencv;

import org.opencv.core.Mat;
import ru.sfedu.opencv.utils.ConfigUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static ru.sfedu.opencv.constants.Constants.PATH_TO_NATIVE_LIB_LINUX;

public class Lab2 {

    public static void main(String[] args) {
        System.load(ConfigUtils.getConfigProperty(PATH_TO_NATIVE_LIB_LINUX));
//        showImage();
    }

    public static JFrame showImage(Mat mat, int imageType) {
        var bufferSize = mat.channels() * mat.cols() * mat.rows();

        var buffer = new byte[bufferSize];
        mat.get(0, 0, buffer);

        var image = new BufferedImage(mat.cols(), mat.rows(), imageType);
        final var targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);

        var icon = new ImageIcon(image);

        var frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(image.getWidth() + 50, image.getHeight() + 50);
        var label = new JLabel();

        label.setIcon(icon);
        frame.add(label);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        return frame;
    }

}
