package com.study.JUnit5;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StudyTest8강 {

	@FastTest
	@DisplayName("JUnit 스터디 fast")
	void create_new_study() {
		System.out.println("fast method");
		
	}
	 
	@SlowTest
	@DisplayName("JUnit 스터디 slow") 
	void create_new_study1() {
		System.out.println("slow method");
		
	}
	
	@Test
	@DisplayName("JUnit 스터디") 
	void create_new_study2() {
		System.out.println("notag method");
		
	}

	@Test
	@Disabled
	void create1() {
		System.out.println("create1");
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	void create2() {
		System.out.println("create2");
		//Map<String, String> test_env = System.getenv();
		//System.out.println(test_env);
	}
	
	@Test
	@EnabledIfEnvironmentVariable(named ="TEST_ENV", matches ="rrakoon")
	void create3() {
		System.out.println("create3");
	}
	
	@DisplayName("스터디 테스트")
	@RepeatedTest(value = 10, name = "{displayName} "
			+ "{currentRepetition}/{totalRepetitions} " )
	void repeatTest(RepetitionInfo repeatInfo ) {
		System.out.println("test"+repeatInfo.getCurrentRepetition()+
				"/"+repeatInfo.getTotalRepetitions());
	}
	
	@DisplayName("스터디 테스트1")
	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(strings = {"회사", "가기", "싫다.."} )
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
