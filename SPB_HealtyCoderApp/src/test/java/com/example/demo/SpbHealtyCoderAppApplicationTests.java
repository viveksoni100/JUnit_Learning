package com.example.demo;

import com.healthycoderapp.BMICalculator;
import com.healthycoderapp.Coder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpbHealtyCoderAppApplicationTests {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting DB Servers / Connections ...");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Stopping DB Servers / Connections ...");
    }

    @Test
    void contextLoads() {
    }

    //    @ValueSource(doubles = {70.0, 89.0, 95.0, 110.0})
    @ParameterizedTest(name = "weight={0}, height={1}")
    @CsvSource(value = {"89.0, 1.72", "95.0, 1.75", "110.0, 1.78"})
    void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {

        boolean recommended = BMICalculator.isDietRecommended(coderWeight, coderHeight);

        assertTrue(recommended);
    }

    @Test
    void should_ReturnFalse_When_DietNotRecommended() {

        double weight = 89.0;
        double height = 1.72;

        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        assertFalse(recommended);
    }

    @Test
    void should_ThrowAirithmeticException_When_HeightZero() {

        double weight = 0;
        double height = 0;

        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

        assertThrows(ArithmeticException.class, executable);
    }

    @Test
    void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {

        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));

        Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        assertAll(
                () -> assertEquals(1.82, coderWithWorstBMI.getHeight()),
                () -> assertEquals(98.0, coderWithWorstBMI.getWeight())
        );
    }

    @Test
    void should_ReturnNullWithWorstBMI_When_CoderListEmpty() {

        List<Coder> coders = new ArrayList<>();

        Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        assertNull(coderWithWorstBMI);
    }

    @Test
    void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {

        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));
        double[] expected = {18.52, 29.59, 19.53};

        double[] bmiScores = BMICalculator.getBMIScores(coders);

        assertArrayEquals(expected, bmiScores);
    }

}
