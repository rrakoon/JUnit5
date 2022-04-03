package com.study.JUnit5.member;

import java.util.Optional;

import com.study.JUnit5.domian.Member;
import com.study.JUnit5.domian.Study;

public interface MemberService {
	//Member findById(Long memberId) throws MemberNotFoundException;
	Optional<Member> findById(Long memberId) throws MemberNotFoundException;
	
	void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
