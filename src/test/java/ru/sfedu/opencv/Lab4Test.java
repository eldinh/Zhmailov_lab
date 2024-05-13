package ru.sfedu.opencv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.sfedu.opencv.constants.Constants.MORPH;
import ru.sfedu.opencv.utils.ConfigUtils;

import java.util.Arrays;
import java.util.List;

import static ru.sfedu.opencv.Lab4.filterImage;
import static ru.sfedu.opencv.Lab4.morphologyTest;
import static ru.sfedu.opencv.constants.Constants.LAB4_IMAGE_PATH;

public class Lab4Test extends BaseTest {

	@ParameterizedTest
	@ValueSource(ints = {1, 3, 5, 7, 9})
	@DisplayName("Проверка метода фильтрации 4 способами")
	public void testFilterImage(int ksize) {
		filterImage(ConfigUtils.getConfigProperty(LAB4_IMAGE_PATH), ksize);
	}

	@ParameterizedTest(name = "Размер ядра-{0}. Форма-{1}")
	@MethodSource("provideParamsForMorphology")
	@DisplayName("Проверка метода морфологических операций")
	public void testMorphologyTest(int ksize, MORPH morph) {

		morphologyTest(ConfigUtils.getConfigProperty(LAB4_IMAGE_PATH), ksize, morph);
	}

	private static List<Arguments> provideParamsForMorphology() {
		List<Integer> ksizeList = List.of(
			3, 5, 7, 9, 13, 15
		);

		return ksizeList.stream()
			.flatMap( ksize ->
				Arrays.stream(MORPH.values())
					.map(morph -> Arguments.of(ksize, morph))
			)
			.toList();

	}
}
