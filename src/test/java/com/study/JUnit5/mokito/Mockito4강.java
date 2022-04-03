package com.study.JUnit5.mokito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.study.JUnit5.domian.Member;
import com.study.JUnit5.member.MemberService;
import com.study.JUnit5.study.StudyRepository;
import com.study.JUnit5.study.StudyService;

@ExtendWith(MockitoExtension.class)
public class Mockito4강 { // Mock 객체 만들기

	@Test
	void createStudyService(@Mock MemberService memberService
			              , @Mock StudyRepository studyRepository) {
		StudyService studyService = new StudyService(memberService, studyRepository);
		assertNotNull(studyService);
		
		//Optional<Member> optional = memberService.findById(1L);
		//assertNull(optional);  // Optional.empty 리턴
		//memberService.validate(2L); //아무일도 발생 x. 에러를 던지지 않음.
		
		Member member = new Member();
		member.setId(1L);
		member.setEmail("rrakoon@gmail.com");
		
		//OngoingStubbing
		//Id가 1L인 경우에만 리턴하는 것.
		//when(memberService.findById(1L)).thenReturn(Optional.of(member));
		//ArgumentMatchers.any() => 아무 파라미터로 받는다.
		when(memberService.findById(any())).thenReturn(Optional.of(member));
		
		//Study study = new Study(10,"mockito");
		
		assertEquals("rrakoon@gmail.com", memberService.findById(1L).get().getEmail());
		assertEquals("rrakoon@gmail.com", memberService.findById(2L).get().getEmail());
		
		//Stubber
		doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
		
		assertThrows(IllegalArgumentException.class, ()->{
			memberService.validate(1L);
		});
		
		memberService.validate(2L);
		
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
}
