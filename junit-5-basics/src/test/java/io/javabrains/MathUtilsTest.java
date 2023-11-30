package io.javabrains;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/*
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
it is a default behaviour of JUnit to create new instance of a class before each method
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/* With PER_CLASS there is no need to set static for beforeAll & afterAll as class instance will be created at first*/
public class MathUtilsTest {
    MathUtils mathUtils;
    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeAll
    static void beforeAll() { //it has to be static because beforeAll is executed before the instance is created
        System.out.println("Running Before All");
    }

    @BeforeEach
    void init(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        mathUtils = new MathUtils();
    }

    @AfterEach
    void afterEach() {
        System.out.println("Running After Each");
        testReporter.publishEntry("Running Test: "+testInfo.getDisplayName()+"\t linked to tags:"+ testInfo.getTags());
    }

    @AfterAll
    static void afterAll() { //it has to be static because afterAll is executed before the instance is created
        System.out.println("Running After All");
    }

    @Test
    @DisplayName("Testing Add Method")
    void testAdd() {
        int expected = 2;
        int actual = mathUtils.add(1, 1);
        assertEquals(expected, actual);
    }

    @Nested
    @DisplayName("Testing Nested Add Method")
    @Tag("Arithmetic")
    class TestAddNested {

        @Test
        @DisplayName("When add two positive numbers")
        void testAddPositive() {
            assertEquals(2, mathUtils.add(1, 1), "Should return positive sum");
        }

        @Test
        @DisplayName("When add two negative numbers")
        void testAddNegative() {
            assertEquals(-2, mathUtils.add(-1, -1), "Should return negative sum");
        }
    }

    @Test
    @DisplayName("Testing assertAll Multiply Method")
    @Tag("Arithmetic")
    void testMultiply() {
        assertAll(
                () -> assertEquals(-2, mathUtils.multiply(2, -1)),
                () -> assertEquals(0, mathUtils.multiply(2, 0)),
                () -> assertEquals(2, mathUtils.multiply(2, 1))
        );
    }

    @Test
    @DisplayName("Testing Multiply Method")
    @Tag("Arithmetic")
    void testDivide() {
        assertThrows(ArithmeticException.class, () -> mathUtils.divide(1, 0), "Divide by zero must throw ArithmeticException");
    }

    @RepeatedTest(3)
    @DisplayName("Testing Area of Circle")
    @Tag("Circle")
    void testComputeCircleArea(RepetitionInfo repetitionInfo) {
        System.out.println("Total of repetitions: " + repetitionInfo.getTotalRepetitions());
        System.out.println("Current repetition No: " + repetitionInfo.getCurrentRepetition());
        double input = 15;
        assertEquals(Math.PI * (input * input), mathUtils.computeCircleArea(input), "Should return area of circle against the input");
    }

    @Test
    @DisplayName("Disabled Test")
    @Disabled
    @Tag("AnnotationPractice")
    void testDisabledTest() {
        fail("This test should not run");
    }

    @Test
    @DisplayName("Enabled if JDK 11")
    @EnabledOnJre(JRE.JAVA_11)
    @Tag("AnnotationPractice")
    void testEnabledOnJre11() {
        System.out.println("This test should run on JRE 11 only");
    }

    @Test
    @DisplayName("Enabled if JDK 08")
    @EnabledOnJre(JRE.JAVA_8)
    @Tag("AnnotationPractice")
    void testEnabledOnJre08() {
        System.out.println("This test should run on JRE 08 only");
    }

    @Test
    @DisplayName("Disabled Test")
    @EnabledOnOs(OS.LINUX)
    @Tag("AnnotationPractice")
    void testEnabledOnOs() {
        System.out.println("This test should run on Linux only");
    }

    @Test
    @DisplayName("Assumption Test")
    @Tag("AnnotationPractice")
    void testAssumptions() {
        boolean serverRunning = false;
        assumeTrue(serverRunning); //only run this test if serverRunning gets true
        System.out.println("This test should run when particular condition gets true");
    }
}