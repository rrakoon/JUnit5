package com.study.JUnit5.mokito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.study.JUnit5.domian.Member;
import com.study.JUnit5.domian.Study;
import com.study.JUnit5.member.MemberService;
import com.study.JUnit5.study.StudyRepository;
import com.study.JUnit5.study.StudyService;
import com.study.JUnit5.study.VerifyNoInteractionService;

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
