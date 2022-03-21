package com.study.JUnit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class StudyTest9강 {

	@DisplayName("스터디 테스트")
	@RepeatedTest(value = 10, name = "{displayName} "
			+ "{currentRepetition}/{totalRepetitions} " )
	void repeatTest(RepetitionInfo repeatInfo ) {
		System.out.println("test"+repeatInfo.getCurrentRepetition()+
				"/"+repeatInfo.getTotalRepetitions());
	}
	
	@DisplayName("스터디 테스트1")
	@ParameterizedTest(name = "{index} : {displayName} message={0}")
	@ValueSource(strings = {"회사", "가기", "싫다.."} )
	//@EmptySource
	//@NullSource
	@NullAndEmptySource
	void repeatTest1(String message) {
		System.out.println(message);
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
