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

class StudyTest5강 {

	@Test
	@DisplayName("스터디 만들기") // 이모지, 공백, 한글가능
	void create_new_study() {
		// 테스트 실행환경이 로컬인 경우에만 테스트
		// String test_evn = System.getenv("TEST_ENV");
		// getenv : 환경변수를 읽어오는 함수
		String test_evn = System.getenv("JAVA_HOME");
		String java_home = "C:\\Program Files\\Java\\jdk1.8.0_231";
		String mave_home = "C:\\__Programing\\apache-maven-3.6.3";
		
		assumingThat(java_home.equalsIgnoreCase(test_evn), () -> {
			System.out.println(java_home);
			Study actual = new Study(10);
			assertThat(actual.getLimit()).isGreaterThan(0);
		});
		
		assumingThat(mave_home.equalsIgnoreCase(test_evn), () -> {
			System.out.println(mave_home);
			Study actual = new Study(10);
			assertThat(actual.getLimit()).isGreaterThan(0);
		});
		
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
	@EnabledOnOs(OS.MAC)
	void create3() {
		System.out.println("create3");
	}
	
	@Test
	@EnabledOnOs({OS.WINDOWS, OS.MAC})
	void create4() {
		System.out.println("create4");
	}
	
	@Test
	@EnabledOnJre(JRE.JAVA_8)
	void create5() {
		System.out.println("create5");
	}
	
	@Test
	@EnabledOnJre(JRE.JAVA_11)
	void create6() {
		System.out.println("create6");
	}
	
	@Test
	@EnabledIfEnvironmentVariable(named ="TEST_ENV", matches ="rrakoon")
	void create7() {
		System.out.println("create7");
	}

	@BeforeAll 
	static void befoeAll() {
		System.out.println("beforeAll");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("afterAll");
	}

	@BeforeEach
	void beforeEach() {
		System.out.println("beforeEach");
	}

	@AfterEach
	void afterEach() {
		System.out.println("afterEach");
	}

}
