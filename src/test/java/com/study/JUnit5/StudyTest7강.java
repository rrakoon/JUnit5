package com.study.JUnit5;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import java.util.Map;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

class StudyTest7강 {

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
