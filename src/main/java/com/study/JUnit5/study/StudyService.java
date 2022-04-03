package com.study.JUnit5.study;

import java.util.Optional;

import com.study.JUnit5.domian.Member;
import com.study.JUnit5.domian.Study;
import com.study.JUnit5.member.MemberService;

public class StudyService {

	private final MemberService memberService;

	private final StudyRepository repository;

	public StudyService(MemberService memberService, StudyRepository repository) {
		//assert 키워드 사용으로 assertion 옵션 활성화 가능.
		//프로그램에 대한 가정을 테스트 하는데 사용.
		assert memberService != null;
		assert repository != null;
		this.memberService = memberService;
		this.repository = repository;
	}

	public Study createNewStudy(Long memberId, Study study) {
		//Java8의 Optional API 참조
		Optional<Member> member = memberService.findById(memberId);
		study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'")));
		
		Study newstudy = repository.save(study);
		memberService.notify(newstudy);
		memberService.notify(member.get());
		memberService.findById(memberId);
		
		return newstudy;
	}

}
