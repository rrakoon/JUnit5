package com.study.JUnit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

	@Test
	@DisplayName("JUnit5 스터디")
	void create_new_study() {
		Study study = new Study();
		assertNotNull(study);
		System.out.println("create");
	}
	
	@Test
	@Disabled
	void create1() {
		System.out.println("create1");
	}
	
	@Test
	void create2() {
		System.out.println("create2");
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
