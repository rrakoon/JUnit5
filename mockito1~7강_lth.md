
# 1. Mockito란

테스트를 작성하는 자바 개발자 50% 이상이 사용하는 프레임워크

대체제 : EasyMock, JMock

대게 외부 API의 동작을 예측해서 테스트를 함.

DAO, Repository객체를 Mock으로 만들고 서비스의 동작을 가정하에 어떻게 동작할것인지를 테스트.

- Mock
    - 진짜 객체와 비슷하게 동작하지만 프로그래머가 직접  객체의 행동을 관리하는 객체.
- Mokito
    - Mock객체를 쉽게 만들려고 관리하고 검증할 수 있는 프레임워크
- Spring Boot 2.2 이상에서는 프로젝트 생성시 spring-boot-starter-test에서 자동으로 Mokito를 추가 해준다.
- 스프링 부트를 사용하지 않는다면 따로 의존성을 추가한다.
    - Maven Repository
        - mokito-core  : mokito 제공 기본 라이브러리
        - mokito-junit-jupiter : JUnit 테스트 확장 구현체
    
    ```java
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation group: 'org.mockito', name: 'mockito-core', version: '4.3.1'
    // https://mvnrepository.com/artifact/org.mockito/mockito-junit-jupiter
    testImplementation group: 'org.mockito', name: 'mockito-junit-jupiter', version: '4.3.1'
    ```
    
- Mokito 레퍼런스
    - [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

# 2. Mock 객체 만들기

구현체가 없는 경우 Moking을 해야된다. 있는 경우는 그냥 사용.

- Mockito.mock() 사용.
    - 대상객체 객체명 = Mockito.mock(대상객체.class);
    
    ```java
    import static org.mockito.Mockito.mock;
    
    //Static mockito 사용시 메서드만으로 사용가능.
    TestService testService= mock(TestService .class);
    		
    //    구현체가 있는 경우 Mokito로 가짜로 생성해준다.
    //		MemberService memberService = Mockito.mock(MemberService.class);
    
    //   구현체가 없을시 직접 구현해서 사용하는 방법.		
    //		MemberService memberService = new MemberService() {
    //			@Override
    //			public Optional<Member> findById(Long memberId){
    //				return Optional.empty(); 
    //			}
    //		};
    ```
    
- @Mock 어노테이션 사용
    - JUnit5 확장으로 MockitoExtention을 사용해야한다
    - 필드, 메서드 메개변수
    
    ```java
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.Mock;
    import org.mockito.Mockito;
    import org.mockito.junit.jupiter.MockitoExtension;
    
    @ExtendWith(MockitoExtension.class)
    public class Test{
    	@Mock
    	TestService testService;
    
    	@Test
    	void mockitoTestService(){
    		TestService testService = new TestService(testService){
    			assertNull(testService);
    		}
    	}
    }
    ```
    
- 전체 클래스가 아닌 해당 클래스에서만 Mock객체를 사용하는 경우.
    - 메서드에 Mock어노테이션을 파라미터로 사용.
    
    ```java
    @ExtendWith(MockitoExtension.class)
    public class Test{
    
    	@Test
    	void mockitoTestService(@Mock TestService testService;{
    		TestService testService = new TestService(testService){
    			assertNull(testService);
    		}
    	}
    }
    ```
    

# 3. Mock 객체 Stubbing

### Stubbing?

- stub이라고도 하며 토막의 남은 부분이란 뜻을 가짐.
    - Test Stub은 테스트 중에 만들어진 호출에 대해 미리 준비된 답을 제공하는 것.
    - 만들어진 Mock 객체의 메서드를 실행시 어떤값을 리턴할지를 정의하는것으로 생각됨.
- Mock 객체의 행동을 조작하는 것.
    - Null을 리턴 (Optional 타입은 Optional.empty 리턴)
    - Primitive 타입은 기본 Primitive 값. (boolean, byte, int, long, float, double, char ....)
    - Collection은 비어있는 Collection
    - void 메서드는 exception을 던지지 않고 아무런 일도 발생 x.
- Mockito에서는 when() 메서드로 Stubbing 지원.
    - when 이후 어떤 동작 제어할지는 메서드 체이닝 형태로 작성.
    - Stubbing 사용 방법으로는 OngoingStubbing, Stubber를 쓰는 방법이 있다.
1. **OngoingStubbing**
    1. when에 넣은 메서드의 리턴값을 정의해주는 메서드.
    2. `when(stubbing method).OngoingStubbing method;`
    
    ![image](https://user-images.githubusercontent.com/33479944/161433248-f1fa9c0f-9e5a-4db1-a582-6ef7ab77d7ca.png)
    
    ```java
    @ExtendWith(MockitoExtension.class)
    public class Test{
    
    	@Test
    	void mockitoTestService(@Mock TestService testService;{
    	Member member = new Member();
    		member.setId(1L);
    		member.setEmail("rrakoon@gmail.com");
    		
    		//OngoingStubbing
    		//Id가 1L인 경우에만 리턴하는 것.
    		//when(memberService.findById(1L)).thenReturn(Optional.of(member));
    		//ArgumentMatchers.any() => 아무 파라미터로 받는다.
    		when(memberService.findById(any())).thenReturn(Optional.of(member));
    		
    		assertEquals("rrakoon@gmail.com", memberService.findById(1L).get().getEmail());
    		assertEquals("rrakoon@gmail.com", memberService.findById(2L).get().getEmail());
    }
    ```
    
2. **Stubber**
    1. `Stubber method.when(스터빙할 클래스).스터빙 메서드;`
    
    ![image](https://user-images.githubusercontent.com/33479944/161433284-ee415983-8311-43a4-9f08-0002194e6e8e.png)
    
    ```java
    @ExtendWith(MockitoExtension.class)
    public class Test{
    
    	@Test
    	void mockitoTestService(@Mock TestService testService;{
    	Member member = new Member();
    		member.setId(1L);
    		member.setEmail("rrakoon@gmail.com");
    		
    		//OngoingStubbing
    		//Id가 1L인 경우에만 리턴하는 것.
    		//when(memberService.findById(1L)).thenReturn(Optional.of(member));
    		//ArgumentMatchers.any() => 아무 파라미터로 받는다.
    		when(memberService.findById(any())).thenReturn(Optional.of(member));
    		
    		assertEquals("rrakoon@gmail.com", memberService.findById(1L).get().getEmail());
    		assertEquals("rrakoon@gmail.com", memberService.findById(2L).get().getEmail());
    
    		//Stubber
    		doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
    		
    		assertThrows(IllegalArgumentException.class, ()->{
    			memberService.validate(1L);
    		});
    		
    		memberService.validate(2L);
    }
    ```
    

- 동일한 매개변수로 여러번 호출 될떄 각기 다르게 조작
    
    ```java
    @ExtendWith(MockitoExtension.class)
    public class Test{
    
    	@Test
    	void mockitoTestService(@Mock TestService testService;{
    	Member member = new Member();
    		member.setId(1L);
    		member.setEmail("rrakoon@gmail.com");
    		
    		// 같은 매개변수로 여러번 호출될때 다르게 행동 조작
    		when(memberService.findById(any()))
    			.thenReturn(Optional.of(member))
    			.thenThrow(new RuntimeException())
    			.thenReturn(Optional.empty());
    		
    		//1번째 호출
    		Optional<Member> byID = memberService.findById(1L);
    		assertEquals("rrakoon@gmail.com", byID.get().getEmail());
    		
    		//2번쨰 호출
    		assertThrows(RuntimeException.class, ()->{
    			memberService.findById(2L);
    		});
    		
    		//3번째 호출
    		assertEquals(Optional.empty(), memberService.findById(3L));
    		
    }
    ```
    

참조 :

- stubbing : [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#2](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#2)
- argument matchers : [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#2](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#2)
- void method exception : [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#5](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#5)
- stubbing consecutive calls  : [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#10](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#10)

# 4. Mock 객체 확인(Verifying)

Mock 객체가 사용되는지 확인하는 방법 4가지.

`verify(확인 객체, veryify 메서드).확인메서드(매개변수);`

1. 특정 메서드가 특정 매개변수로 호출 되었는지 여부.
    - [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#exact_verification](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#exact_verification)
    
    ![image](https://user-images.githubusercontent.com/33479944/161433313-d7cd0713-0ff5-4c0f-9362-1d87ee2079c0.png)
    
    1. 몇번 호출 되었는지 여부 확인. - times
    2. 최소/최대 몇번 호출되었는지 확인. - atLeast / atMost
    3. 전혀 호출 되지 않았는지 확인. - never
    
    ```java
    @ExtendWith(MockitoExtension.class)
    public class Mockito6강 { // Mock 객체 만들기
    
    	@Test
    	void createStudyService(@Mock MemberService memberService
    			              , @Mock StudyRepository studyRepository) {
    		StudyService studyService = new StudyService(memberService, studyRepository);
    		assertNotNull(studyService);
    		
    		Member member = new Member();
    		member.setId(1L);
    		member.setEmail("rrakoon@gmail.com");
    		
    		Study study = new Study(10,"mockito");
    		
    		when(memberService.findById(1L)).thenReturn(Optional.of(member));
    		when(studyRepository.save(study)).thenReturn(study);
    		
    		studyService.createNewStudy(1L, study);
    		
    		assertEquals(member, study.getOwner());
    		
    		//times(호출횟수)
    		verify(memberService, times(1)).notify(study);
    		//전혀 호출되지 않은지 체크
    		verify(memberService, never()).validate(any());
    		//최소 호출 확인
    		verify(memberService, atLeast(2)).findById(1L);
    		
    	}
    }
    ```
    
2. 어떤 순서로 호출되었는지
    - [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#in_order_verification](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#in_order_verification)
    - Mockito InOrder활용.
        - 실행순서가 틀리면 에러 벹음
        
        ```java
        memberService.notify(study);     // 1번
        memberService.notify(member.get()); // 2번
        ---------------------------------
        @ExtendWith(MockitoExtension.class)
        public class Mockito6강 { // Mock 객체 만들기
        
        	@Test
        	void createStudyService(@Mock MemberService memberService
        			              , @Mock StudyRepository studyRepository) {
        		StudyService studyService = new StudyService(memberService, studyRepository);
        		assertNotNull(studyService);
        		
        		Member member = new Member();
        		member.setId(1L);
        		member.setEmail("rrakoon@gmail.com");
        		
        		Study study = new Study(10,"mockito");
        		
        		when(memberService.findById(1L)).thenReturn(Optional.of(member));
        		when(studyRepository.save(study)).thenReturn(study);
        		
        		studyService.createNewStudy(1L, study);
        		
        		assertEquals(member, study.getOwner());
        		
        		//실행 순서가 틀리면 에러 벹음
        		InOrder inOrder = inOrder(memberService);
        		inOrder.verify(memberService).notify(member);
        		inOrder.verify(memberService).notify(study);
        		
        	}
        }
        ```
        
        ![image](https://user-images.githubusercontent.com/33479944/161433326-8ccc16e7-2bcf-4f81-8258-8b14571661db.png)
        
    - verifyNoMoreInteractions(T mock)
        - 선언한 verify이후 해당 Mock이 실행되면 fail
        
        ```java
        memberService.notify(study);
        memberService.notify(member.get());
        memberService.findById(memberId);
        ----------------------
        @ExtendWith(MockitoExtension.class)
        public class Mockito6강 { // Mock 객체 만들기
        
        	@Test
        	void createStudyService(@Mock MemberService memberService
        			              , @Mock StudyRepository studyRepository) {
        		StudyService studyService = new StudyService(memberService, studyRepository);
        		assertNotNull(studyService);
        		
        		Member member = new Member();
        		member.setId(1L);
        		member.setEmail("rrakoon@gmail.com");
        		
        		Study study = new Study(10,"mockito");
        		
        		when(memberService.findById(1L)).thenReturn(Optional.of(member));
        		when(studyRepository.save(study)).thenReturn(study);
        		
        		studyService.createNewStudy(1L, study);
        		
        		assertEquals(member, study.getOwner());
        		
        		//times(호출횟수)
        		verify(memberService, times(1)).notify(study);
        		
        		//해당서비스는 notify(study)이후 2메서드를 실행하므로 실패 
        		verifyNoMoreInteractions(memberService);
        		
        	}
        }
        ```
        
3.  특정 시점 이후 실행되는지 여부 확인
    - [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#finding_redundant_invocations](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#finding_redundant_invocations)
    - verifyNoInteractions(T mock)
        - 테스트 내에서 Mock을 실행하면 fali
        
        ```java
        @ExtendWith(MockitoExtension.class)
        public class Mockito6강 { // Mock 객체 만들기
        
        	@Mock 
        	VerifyNoInteractionService verifyNoInteraction;
        	
        	@Test
        	void createStudyService(@Mock MemberService memberService
        			              , @Mock StudyRepository studyRepository) {
        		StudyService studyService = new StudyService(memberService, studyRepository);
        		assertNotNull(studyService);
        		
        		Member member = new Member();
        		member.setId(1L);
        		member.setEmail("rrakoon@gmail.com");
        		
        		Study study = new Study(10,"mockito");
        		
        		when(memberService.findById(1L)).thenReturn(Optional.of(member));
        		when(studyRepository.save(study)).thenReturn(study);
        		
        		studyService.createNewStudy(1L, study);
        		
        		assertEquals(member, study.getOwner());
        		
        		//times(호출횟수)
        		verify(memberService, times(1)).notify(study);
        		
        		//verifyNoMoreInteractions(memberService);
        		
        		//전혀 호출되지 않은지 체크
        		verify(memberService, never()).validate(any());
        		//최소 호출 확인
        		verify(memberService, atLeast(2)).findById(1L); 
        		
        		//실행 순서가 틀리면 에러 벹음
        		InOrder inOrder = inOrder(memberService);
        		inOrder.verify(memberService).notify(study);
        		inOrder.verify(memberService).notify(member);
        		
        		//실행 안되는 Mock확인.
        		verifyNoInteractions(verifyNoInteraction);
        	}
        }
        ```
        
4.  특정 시간내 호출
    - [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#verification_timeout](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#verification_timeout)
=======
# 1. Mockito란

테스트를 작성하는 자바 개발자 50% 이상이 사용하는 프레임워크

대체제 : EasyMock, JMock

대게 외부 API의 동작을 예측해서 테스트를 함.

DAO, Repository객체를 Mock으로 만들고 서비스의 동작을 가정하에 어떻게 동작할것인지를 테스트.

- Mock
    - 진짜 객체와 비슷하게 동작하지만 프로그래머가 직접  객체의 행동을 관리하는 객체.
- Mokito
    - Mock객체를 쉽게 만들려고 관리하고 검증할 수 있는 프레임워크
- Spring Boot 2.2 이상에서는 프로젝트 생성시 spring-boot-starter-test에서 자동으로 Mokito를 추가 해준다.
- 스프링 부트를 사용하지 않는다면 따로 의존성을 추가한다.
    - Maven Repository
        - mokito-core  : mokito 제공 기본 라이브러리
        - mokito-junit-jupiter : JUnit 테스트 확장 구현체
    
    ```java
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation group: 'org.mockito', name: 'mockito-core', version: '4.3.1'
    // https://mvnrepository.com/artifact/org.mockito/mockito-junit-jupiter
    testImplementation group: 'org.mockito', name: 'mockito-junit-jupiter', version: '4.3.1'
    ```
    
- Mokito 레퍼런스
    - [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

# 2. Mock 객체 만들기

구현체가 없는 경우 Moking을 해야된다. 있는 경우는 그냥 사용.

- Mockito.mock() 사용.
    - 대상객체 객체명 = Mockito.mock(대상객체.class);
    
    ```java
    import static org.mockito.Mockito.mock;
    
    //Static mockito 사용시 메서드만으로 사용가능.
    TestService testService= mock(TestService .class);
    		
    //    구현체가 있는 경우 Mokito로 가짜로 생성해준다.
    //		MemberService memberService = Mockito.mock(MemberService.class);
    
    //   구현체가 없을시 직접 구현해서 사용하는 방법.		
    //		MemberService memberService = new MemberService() {
    //			@Override
    //			public Optional<Member> findById(Long memberId){
    //				return Optional.empty(); 
    //			}
    //		};
    ```
    
- @Mock 어노테이션 사용
    - JUnit5 확장으로 MockitoExtention을 사용해야한다
    - 필드, 메서드 메개변수
    
    ```java
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.Mock;
    import org.mockito.Mockito;
    import org.mockito.junit.jupiter.MockitoExtension;
    
    @ExtendWith(MockitoExtension.class)
    public class Test{
    	@Mock
    	TestService testService;
    
    	@Test
    	void mockitoTestService(){
    		TestService testService = new TestService(testService){
    			assertNull(testService);
    		}
    	}
    }
    ```
    
- 전체 클래스가 아닌 해당 클래스에서만 Mock객체를 사용하는 경우.
    - 메서드에 Mock어노테이션을 파라미터로 사용.
    
    ```java
    @ExtendWith(MockitoExtension.class)
    public class Test{
    
    	@Test
    	void mockitoTestService(@Mock TestService testService;{
    		TestService testService = new TestService(testService){
    			assertNull(testService);
    		}
    	}
    }
    ```
    

# 3. Mock 객체 Stubbing

### Stubbing?

- stub이라고도 하며 토막의 남은 부분이란 뜻을 가짐.
    - Test Stub은 테스트 중에 만들어진 호출에 대해 미리 준비된 답을 제공하는 것.
    - 만들어진 Mock 객체의 메서드를 실행시 어떤값을 리턴할지를 정의하는것으로 생각됨.
- Mock 객체의 행동을 조작하는 것.
    - Null을 리턴 (Optional 타입은 Optional.empty 리턴)
    - Primitive 타입은 기본 Primitive 값. (boolean, byte, int, long, float, double, char ....)
    - Collection은 비어있는 Collection
    - void 메서드는 exception을 던지지 않고 아무런 일도 발생 x.
- Mockito에서는 when() 메서드로 Stubbing 지원.
    - when 이후 어떤 동작 제어할지는 메서드 체이닝 형태로 작성.
    - Stubbing 사용 방법으로는 OngoingStubbing, Stubber를 쓰는 방법이 있다.
1. **OngoingStubbing**
    1. when에 넣은 메서드의 리턴값을 정의해주는 메서드.
    2. `when(stubbing method).OngoingStubbing method;`
    
    ![image](https://user-images.githubusercontent.com/33479944/161433248-f1fa9c0f-9e5a-4db1-a582-6ef7ab77d7ca.png)
    
    ```java
    @ExtendWith(MockitoExtension.class)
    public class Test{
    
    	@Test
    	void mockitoTestService(@Mock TestService testService;{
    	Member member = new Member();
    		member.setId(1L);
    		member.setEmail("rrakoon@gmail.com");
    		
    		//OngoingStubbing
    		//Id가 1L인 경우에만 리턴하는 것.
    		//when(memberService.findById(1L)).thenReturn(Optional.of(member));
    		//ArgumentMatchers.any() => 아무 파라미터로 받는다.
    		when(memberService.findById(any())).thenReturn(Optional.of(member));
    		
    		assertEquals("rrakoon@gmail.com", memberService.findById(1L).get().getEmail());
    		assertEquals("rrakoon@gmail.com", memberService.findById(2L).get().getEmail());
    }
    ```
    
2. **Stubber**
    1. `Stubber method.when(스터빙할 클래스).스터빙 메서드;`
    
    ![image](https://user-images.githubusercontent.com/33479944/161433284-ee415983-8311-43a4-9f08-0002194e6e8e.png)
    
    ```java
    @ExtendWith(MockitoExtension.class)
    public class Test{
    
    	@Test
    	void mockitoTestService(@Mock TestService testService;{
    	Member member = new Member();
    		member.setId(1L);
    		member.setEmail("rrakoon@gmail.com");
    		
    		//OngoingStubbing
    		//Id가 1L인 경우에만 리턴하는 것.
    		//when(memberService.findById(1L)).thenReturn(Optional.of(member));
    		//ArgumentMatchers.any() => 아무 파라미터로 받는다.
    		when(memberService.findById(any())).thenReturn(Optional.of(member));
    		
    		assertEquals("rrakoon@gmail.com", memberService.findById(1L).get().getEmail());
    		assertEquals("rrakoon@gmail.com", memberService.findById(2L).get().getEmail());
    
    		//Stubber
    		doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
    		
    		assertThrows(IllegalArgumentException.class, ()->{
    			memberService.validate(1L);
    		});
    		
    		memberService.validate(2L);
    }
    ```
    

- 동일한 매개변수로 여러번 호출 될떄 각기 다르게 조작
    
    ```java
    @ExtendWith(MockitoExtension.class)
    public class Test{
    
    	@Test
    	void mockitoTestService(@Mock TestService testService;{
    	Member member = new Member();
    		member.setId(1L);
    		member.setEmail("rrakoon@gmail.com");
    		
    		// 같은 매개변수로 여러번 호출될때 다르게 행동 조작
    		when(memberService.findById(any()))
    			.thenReturn(Optional.of(member))
    			.thenThrow(new RuntimeException())
    			.thenReturn(Optional.empty());
    		
    		//1번째 호출
    		Optional<Member> byID = memberService.findById(1L);
    		assertEquals("rrakoon@gmail.com", byID.get().getEmail());
    		
    		//2번쨰 호출
    		assertThrows(RuntimeException.class, ()->{
    			memberService.findById(2L);
    		});
    		
    		//3번째 호출
    		assertEquals(Optional.empty(), memberService.findById(3L));
    		
    }
    ```
    

참조 :

- stubbing : [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#2](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#2)
- argument matchers : [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#2](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#2)
- void method exception : [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#5](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#5)
- stubbing consecutive calls  : [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#10](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#10)

# 4. Mock 객체 확인(Verifying)

Mock 객체가 사용되는지 확인하는 방법 4가지.

`verify(확인 객체, veryify 메서드).확인메서드(매개변수);`

1. 특정 메서드가 특정 매개변수로 호출 되었는지 여부.
    - [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#exact_verification](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#exact_verification)
    
    ![image](https://user-images.githubusercontent.com/33479944/161433313-d7cd0713-0ff5-4c0f-9362-1d87ee2079c0.png)
    
    1. 몇번 호출 되었는지 여부 확인. - times
    2. 최소/최대 몇번 호출되었는지 확인. - atLeast / atMost
    3. 전혀 호출 되지 않았는지 확인. - never
    
    ```java
    @ExtendWith(MockitoExtension.class)
    public class Mockito6강 { // Mock 객체 만들기
    
    	@Test
    	void createStudyService(@Mock MemberService memberService
    			              , @Mock StudyRepository studyRepository) {
    		StudyService studyService = new StudyService(memberService, studyRepository);
    		assertNotNull(studyService);
    		
    		Member member = new Member();
    		member.setId(1L);
    		member.setEmail("rrakoon@gmail.com");
    		
    		Study study = new Study(10,"mockito");
    		
    		when(memberService.findById(1L)).thenReturn(Optional.of(member));
    		when(studyRepository.save(study)).thenReturn(study);
    		
    		studyService.createNewStudy(1L, study);
    		
    		assertEquals(member, study.getOwner());
    		
    		//times(호출횟수)
    		verify(memberService, times(1)).notify(study);
    		//전혀 호출되지 않은지 체크
    		verify(memberService, never()).validate(any());
    		//최소 호출 확인
    		verify(memberService, atLeast(2)).findById(1L);
    		
    	}
    }
    ```
    
2. 어떤 순서로 호출되었는지
    - [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#in_order_verification](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#in_order_verification)
    - Mockito InOrder활용.
        - 실행순서가 틀리면 에러 벹음
        
        ```java
        memberService.notify(study);     // 1번
        memberService.notify(member.get()); // 2번
        ---------------------------------
        @ExtendWith(MockitoExtension.class)
        public class Mockito6강 { // Mock 객체 만들기
        
        	@Test
        	void createStudyService(@Mock MemberService memberService
        			              , @Mock StudyRepository studyRepository) {
        		StudyService studyService = new StudyService(memberService, studyRepository);
        		assertNotNull(studyService);
        		
        		Member member = new Member();
        		member.setId(1L);
        		member.setEmail("rrakoon@gmail.com");
        		
        		Study study = new Study(10,"mockito");
        		
        		when(memberService.findById(1L)).thenReturn(Optional.of(member));
        		when(studyRepository.save(study)).thenReturn(study);
        		
        		studyService.createNewStudy(1L, study);
        		
        		assertEquals(member, study.getOwner());
        		
        		//실행 순서가 틀리면 에러 벹음
        		InOrder inOrder = inOrder(memberService);
        		inOrder.verify(memberService).notify(member);
        		inOrder.verify(memberService).notify(study);
        		
        	}
        }
        ```
        
        ![image](https://user-images.githubusercontent.com/33479944/161433326-8ccc16e7-2bcf-4f81-8258-8b14571661db.png)
        
    - verifyNoMoreInteractions(T mock)
        - 선언한 verify이후 해당 Mock이 실행되면 fail
        
        ```java
        memberService.notify(study);
        memberService.notify(member.get());
        memberService.findById(memberId);
        ----------------------
        @ExtendWith(MockitoExtension.class)
        public class Mockito6강 { // Mock 객체 만들기
        
        	@Test
        	void createStudyService(@Mock MemberService memberService
        			              , @Mock StudyRepository studyRepository) {
        		StudyService studyService = new StudyService(memberService, studyRepository);
        		assertNotNull(studyService);
        		
        		Member member = new Member();
        		member.setId(1L);
        		member.setEmail("rrakoon@gmail.com");
        		
        		Study study = new Study(10,"mockito");
        		
        		when(memberService.findById(1L)).thenReturn(Optional.of(member));
        		when(studyRepository.save(study)).thenReturn(study);
        		
        		studyService.createNewStudy(1L, study);
        		
        		assertEquals(member, study.getOwner());
        		
        		//times(호출횟수)
        		verify(memberService, times(1)).notify(study);
        		
        		//해당서비스는 notify(study)이후 2메서드를 실행하므로 실패 
        		verifyNoMoreInteractions(memberService);
        		
        	}
        }
        ```
        
3.  특정 시점 이후 실행되는지 여부 확인
    - [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#finding_redundant_invocations](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#finding_redundant_invocations)
    - verifyNoInteractions(T mock)
        - 테스트 내에서 Mock을 실행하면 fali
        
        ```java
        @ExtendWith(MockitoExtension.class)
        public class Mockito6강 { // Mock 객체 만들기
        
        	@Mock 
        	VerifyNoInteractionService verifyNoInteraction;
        	
        	@Test
        	void createStudyService(@Mock MemberService memberService
        			              , @Mock StudyRepository studyRepository) {
        		StudyService studyService = new StudyService(memberService, studyRepository);
        		assertNotNull(studyService);
        		
        		Member member = new Member();
        		member.setId(1L);
        		member.setEmail("rrakoon@gmail.com");
        		
        		Study study = new Study(10,"mockito");
        		
        		when(memberService.findById(1L)).thenReturn(Optional.of(member));
        		when(studyRepository.save(study)).thenReturn(study);
        		
        		studyService.createNewStudy(1L, study);
        		
        		assertEquals(member, study.getOwner());
        		
        		//times(호출횟수)
        		verify(memberService, times(1)).notify(study);
        		
        		//verifyNoMoreInteractions(memberService);
        		
        		//전혀 호출되지 않은지 체크
        		verify(memberService, never()).validate(any());
        		//최소 호출 확인
        		verify(memberService, atLeast(2)).findById(1L); 
        		
        		//실행 순서가 틀리면 에러 벹음
        		InOrder inOrder = inOrder(memberService);
        		inOrder.verify(memberService).notify(study);
        		inOrder.verify(memberService).notify(member);
        		
        		//실행 안되는 Mock확인.
        		verifyNoInteractions(verifyNoInteraction);
        	}
        }
        ```
        
4.  특정 시간내 호출
    - [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#verification_timeout](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#verification_timeout)
    - 참고만 하는 정도로 넘어감.
  # 5. Mockito BDD Style API

## 5.1 TDD / BDD ??

### 5.1.1 TDD(Test Driven Development)

- 테스트 주도로 개발하는 것으로 테스트를 먼저 작성하고 테스트가 정상적으로 돌아갈때까지 테스트 하면서 코드를 작성하는 작업.
- 짧은 개발 서클을 반복으로 개발하며 해당 케이스를 통과하는 코드 작성 후 리펙토링으로 반복적으로 작성하는 방식.
    - 테스트 하기 쉽게 만들어진 코드를 만들며 모듈의 단순 명확함을 높이며 모듈간 결합도를 낮춰 유지보수가 용이하게 작성하는 것.
    - 다만 테스트 주도하에 개발을 진행하기에 생산성에서는 오래 걸린다.
- Mock객체에 원하는 동작을 미리 정하고 이를 기반으로 테스트한다.
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/08b90d5f-3736-431f-8333-b7ece0de24f5/Untitled.png)
    

### 5.1.2 BDD(Behavior Driven Development)

- 행동 주도 개발로 사용자의 행동을 생각하고 테스트하면서 개발한다는 것.
- BDD는 TDD의 한종류이며 비즈니스 요구사항에 초점을 두어 테스트 케이스를 개발하는 방식.
- 잘짠 TDD라고도한다.
- 미리 작성된 요구사항이나 기획서에 맞춰 테스트케이스를 작성하는 것을 의미.
- 테스트 대상의 상태 변화를 테스트 하는 것.

### 5.2 결론

- TDD와 BDD는 상호 보완관계이며, 프로젝트에서 BDD 테스트 케이스로 시나리오 검증 후 해당 시나리오에서 사용하는 모듈은 TDD 테스트케이스로 검증하는 방법이 가장 좋다함.
    
   ![image](https://user-images.githubusercontent.com/33479944/161987887-6159d5b4-6dff-4562-944c-3679b6a045c6.png)

    

### 5.3 행동에 대한 스펙

- Mockito는 BDDMockito 클래스를 통해 BDD 스타일로 API를 제공함.
- 메서드 명도 행위처럼 보이는 메서드명으로 작성하는것이 원론이나 @DisplayName을 붙여놓고 해도 무방.
- Given / When / Then으로 구성됨.
    - Given :  테스트 메서드에 시나리오 진행에 필요한 조건을 미리 설정하는 단계. 
    주어진 상황을 의미함.
    - When : 시나리오 진행시 필요한 변화를 명시.
    실제 하는행동을 의미함.
    - Then : 시나리오 완료시 예상되는 결과 명시.
    그 행동의 결과를 의미함.
- Mock객체에서는 when().thenReturn(), verify()를 사용한다.
`import static org.mockito.Mockito.*;`
- BDDMock객체에서는 given().willReturn(), then.should()를 사용한다.
    
    `import static org.mockito.BDDMockito.*;`
    
    ```java
    	@Mock 
    	VerifyNoInteractionService verifyNoInteraction;
    	
    	@Test
    	void createStudyService(@Mock MemberService memberService
    			              , @Mock StudyRepository studyRepository) {
    		
    		//Given
    		StudyService studyService = new StudyService(memberService, studyRepository);
    		assertNotNull(studyService);
    		
    		Member member = new Member();
    		member.setId(1L);
    		member.setEmail("rrakoon@gmail.com");
    		
    		Study study = new Study(10,"mockito");
    		
    		//TDD
    		//when(memberService.findById(1L)).thenReturn(Optional.of(member));
    		//when(studyRepository.save(study)).thenReturn(study);
    		
    		//BDD
    		given(memberService.findById(1L)).willReturn(Optional.of(member));
    		given(studyRepository.save(study)).willReturn(study);
    		
    		//When
    		studyService.createNewStudy(1L, study);
    		
    		//Then
    		assertEquals(member, study.getOwner());
    		
    		//TDD
    		//verify(memberService, times(1)).notify(study);
    		//verify(memberService, never()).validate(any());
    		//verify(memberService, atLeast(2)).findById(1L); 
    		//verifyNoInteractions(verifyNoInteraction);
    		
    		//BDD
    		then(memberService).should(times(1)).notify(study);
    		then(memberService).should(never()).validate(any());
    		then(memberService).should(atLeast(2)).findById(1L); 
    		then(verifyNoInteraction).shouldHaveNoInteractions();
    ```
    

- 참고 :
    - [https://javadoc.io/static/org.mockito/mockito-core/3.2.0/org/mockito/BDDMockito.html](https://javadoc.io/static/org.mockito/mockito-core/3.2.0/org/mockito/BDDMockito.html)
    - [https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#BDD_behavior_verification](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#BDD_behavior_verification)