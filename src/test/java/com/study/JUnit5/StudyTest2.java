package com.study.JUnit5;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.*;

class StudyTest2 {

	@Test
	@DisplayName("JUnit5 스터디")
	void create_new_study() {
		Study study = new Study(10);
		// 모든 확인구문
		assertAll(
				() -> assertNotNull(study),
				() -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
						() -> "스터디 처음 생성시 상태값이 " + StudyStatus.DRAFT + "상태다."),
				() -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능인원 0보다 크다")
		);
		
		// 예외 확인
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
		String message = exception.getMessage();
		//assertEquals("limit은 0보다 커야함", message);
		assertEquals("limit은 0보다 커야함", exception.getMessage());

		// 특정시간 실행완료 확인
		assertTimeout(Duration.ofMillis(100), () -> {
			new Study(10);
			Thread.sleep(300);
		});

		// 테스트가 실행됬을때 성공/실패든 무조건 실행
		// assertEquals(StudyStatus.DRAFT, study.getStatus(),"스터디 처음 생성시 상태값이 DRAFT가
		// 되어야한다.");
		// 람다식으로도 가능.
		// 람다식으로 작성시 실패시에만 동작.
//		assertEquals(StudyStatus.DRAFT, study.getStatus()
//				, () -> "스터디 처음 생성시 상태값이 "+ StudyStatus.DRAFT+"상태다.");
		// assertTrue(1<2);
		// assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능인원 0보다 크다");

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
