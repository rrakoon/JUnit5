package com.study.JUnit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class StudyTest2 {

	@Test
	void create() {
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
