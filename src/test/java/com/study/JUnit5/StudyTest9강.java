package com.study.JUnit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Month;
import java.util.stream.Stream;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class StudyTest9강 {

	@DisplayName("스터디 테스트")
	@RepeatedTest(value = 10, name = "{displayName} " + "{currentRepetition}/{totalRepetitions} ")
	void repeatTest(RepetitionInfo repeatInfo) {
		System.out.println("test" + repeatInfo.getCurrentRepetition() + "/" + repeatInfo.getTotalRepetitions());
	}

	@DisplayName("스터디 테스트1")
	@ParameterizedTest(name = "{index} : {displayName} message={0}")
	@ValueSource(strings = { "회사", "가기", "싫다..", "" })
	// @EmptySource
	// @NullSource
	@NullAndEmptySource
	void repeatTest1(String message) {
		System.out.println(message);
	}
	
	@DisplayName("스터디 테스트2")
	@ParameterizedTest(name = "{index} : {displayName} message={0}")
	@ValueSource(ints = { 10,20,40 })
	void repeatTest2(int limits) {
		System.out.println(limits);
	}
	
	//명시적 타입 변환
	//하나의 Argument만 변환
	static class StudyConverter extends SimpleArgumentConverter{

		@Override
		protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
			// TODO Auto-generated method stub
			assertEquals(Study.class, targetType, "Study클래스만 컨버트 가능");
			
			return new Study(Integer.parseInt(source.toString()));
		}
		
	}
	
	@DisplayName("스터디 테스트3")
	@ParameterizedTest(name = "{index} : {displayName} message={0}")
	@ValueSource(ints = { 100,90,50 })
	void repeatTest3(@ConvertWith(StudyConverter.class) Study study) {
		System.out.println(study.getLimit());
	}
	
	@DisplayName("스터디 테스트4")
	@ParameterizedTest(name = "{index} : {displayName} message={0}")
	@CsvSource({"100, 'JUnit테스트'", "200, '스프링'"})
	void repeatTest4(int limit, String name) {
		Study study = new Study(limit, name);
		System.out.println(study);
	}
	
	@DisplayName("스터디 테스트5")
	@ParameterizedTest(name = "{index} : {displayName} message={0}")
	@CsvSource({"100, 'JUnit테스트'", "200, '스프링'"})
	void repeatTest5(ArgumentsAccessor argumentsAccessor) {
		Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
		System.out.println(study);
	}
	
	//인자값 조합
	static class StudyAggregator implements ArgumentsAggregator{

		@Override
		public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
				throws ArgumentsAggregationException {
			return new Study(accessor.getInteger(0), accessor.getString(1));
		}
		
	}
	
	@DisplayName("스터디 테스트6")
	@ParameterizedTest(name = "{index} : {displayName} message={0}")
	@CsvSource({"100, 'JUnit테스트'", "200, '스프링'"})
	void repeatTest6(@AggregateWith(StudyAggregator.class) Study study) {
		System.out.println(study);
	}

	@DisplayName("Enum테스트")
	@ParameterizedTest
	@EnumSource(Month.class) // passing all 12 months
	void getValueForAMonth_IsAlwaysBetweenOneAndTwelve(Month month) {
		int monthNumber = month.getValue();
		assertTrue(monthNumber >= 1 && monthNumber <= 12);
	}

	@DisplayName("Enum테스트1")
	@ParameterizedTest
	@EnumSource(value = Month.class, names = { "FEBRUARY", "APRIL", "JUNE", "SEPTEMBER", "NOVEMBER" })
	void someMonths_Are30DaysLong(Month month) {
		final boolean isALeapYear = false;
		assertEquals(30, month.length(isALeapYear));
	}

	@ParameterizedTest
	@EnumSource(value = Month.class, names = { "APRIL", "JUNE", "SEPTEMBER",
			"NOVEMBER" }, mode = EnumSource.Mode.EXCLUDE)
	void exceptFourMonths_OthersAre31DaysLong(Month month) {
		final boolean isALeapYear = false;
		assertEquals(31, month.length(isALeapYear));
	}

	@ParameterizedTest
	@MethodSource("provideStringsForIsBlank") // needs to match an existing method.
	void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected) {
		assertEquals(expected, Strings.isBlank(input));
	}

	// a static method that returns a Stream of Arguments
	private static Stream<Arguments> provideStringsForIsBlank() { // argument source method
		return Stream.of(
				Arguments.of(null, true)
				, Arguments.of("", true)
				, Arguments.of("  ", true)
				, Arguments.of("not blank", false));
	}
	
	 @ParameterizedTest
	 @MethodSource("com.study.JUnit5.StringParams#blankStrings") // 클래스 외부의 source method
     void isBlank_ShouldReturnTrueForNullOrBlankStringsExternalSource(String input) {
         assertTrue(Strings.isBlank(input));
     }

//	@BeforeAll 
//	static void befoeAll() {
//		System.out.println("beforeAll");
//	}
//
//	@AfterAll
//	static void afterAll() {
//		System.out.println("afterAll");
//	}
//
//	@BeforeEach
//	void beforeEach() {
//		System.out.println("beforeEach");
//	}
//
//	@AfterEach
//	void afterEach() {
//		System.out.println("afterEach");
//	}

}
