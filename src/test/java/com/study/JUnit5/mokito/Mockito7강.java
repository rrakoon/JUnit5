package com.study.JUnit5.mokito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.study.JUnit5.domian.Member;
import com.study.JUnit5.domian.Study;
import com.study.JUnit5.member.MemberService;
import com.study.JUnit5.study.StudyRepository;
import com.study.JUnit5.study.StudyService;
import com.study.JUnit5.study.VerifyNoInteractionService;

@ExtendWith(MockitoExtension.class)
public class Mockito7강 { // Mock 객체 만들기

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
		
		//BDD에 어울리지 않는 부분
		//when(memberService.findById(1L)).thenReturn(Optional.of(member));
		//when(studyRepository.save(study)).thenReturn(study);
		
		given(memberService.findById(1L)).willReturn(Optional.of(member));
		given(studyRepository.save(study)).willReturn(study);
		
		//When
		studyService.createNewStudy(1L, study);
		
		//Then
		assertEquals(member, study.getOwner());
		
		//BDD에 어울리지 않는 부분
		//verify(memberService, times(1)).notify(study);
		//verify(memberService, never()).validate(any());
		//verify(memberService, atLeast(2)).findById(1L); 
		//verifyNoInteractions(verifyNoInteraction);
		
		then(memberService).should(times(1)).notify(study);
		then(memberService).should(never()).validate(any());
		then(memberService).should(atLeast(2)).findById(1L); 
		then(verifyNoInteraction).shouldHaveNoInteractions();
		
		
		
		
	}
}
