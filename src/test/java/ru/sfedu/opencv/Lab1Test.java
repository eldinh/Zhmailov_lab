package ru.sfedu.opencv;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ru.sfedu.opencv.Lab1.getOpenCvVersion;
import static ru.sfedu.opencv.Lab1.getOperationSystemType;

@Slf4j
public class Lab1Test extends BaseTest {

    @Test
    @DisplayName("Метод для вывода операционной системы и версии open cv")
    public void testGetOperationSystemAndOpenCvVersion() {
        log.info("Operation system - {}", getOperationSystemType().name());
        log.info("CV version - {}", getOpenCvVersion());
    }
}
