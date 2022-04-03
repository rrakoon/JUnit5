package com.study.JUnit5.mokito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.study.JUnit5.member.MemberService;
import com.study.JUnit5.study.StudyRepository;
import com.study.JUnit5.study.StudyService;

@ExtendWith(MockitoExtension.class)
public class Mockito3강 { //Mock 객체 만들기
//	@Mock
//	MemberService memberService;
	
//	@Mock
//	StudyRepository studyRepository;
	
	@Test
	void createStudyService(@Mock MemberService memberService,@Mock	StudyRepository studyRepository ) {
//	void createStudyService() {
//		Static mockito 사용시 메서드만으로 사용가능.
//		MemberService memberService = mock(MemberService.class);
		
//	    구현체가 있는 경우 Mokito로 가짜로 생성해준다.
//		MemberService memberService = Mockito.mock(MemberService.class);

//		구현체가 없는 경우 직접 만들어준다.
//		MemberService memberService = new MemberService() {
//			@Override
//			public Optional<Member> findById(Long memberId){
//				return Optional.empty(); 
//			}
//		};
		

//		StudyRepository studyRepository = Mockito.mock(StudyRepository.class);

//		StudyRepository studyRepository = new StudyRepository() {
//
//			@Override
//			public List<Study> findAll() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public List<Study> findAll(Sort sort) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public List<Study> findAllById(Iterable<Long> ids) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public <S extends Study> List<S> saveAll(Iterable<S> entities) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public void flush() {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public <S extends Study> S saveAndFlush(S entity) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public <S extends Study> List<S> saveAllAndFlush(Iterable<S> entities) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public void deleteAllInBatch(Iterable<Study> entities) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void deleteAllByIdInBatch(Iterable<Long> ids) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void deleteAllInBatch() {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public Study getOne(Long id) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public Study getById(Long id) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public <S extends Study> List<S> findAll(Example<S> example) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public <S extends Study> List<S> findAll(Example<S> example, Sort sort) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public Page<Study> findAll(Pageable pageable) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public <S extends Study> S save(S entity) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public Optional<Study> findById(Long id) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public boolean existsById(Long id) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public long count() {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//
//			@Override
//			public void deleteById(Long id) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void delete(Study entity) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void deleteAllById(Iterable<? extends Long> ids) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void deleteAll(Iterable<? extends Study> entities) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void deleteAll() {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public <S extends Study> Optional<S> findOne(Example<S> example) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public <S extends Study> Page<S> findAll(Example<S> example, Pageable pageable) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public <S extends Study> long count(Example<S> example) {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//
//			@Override
//			public <S extends Study> boolean exists(Example<S> example) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public <S extends Study, R> R findBy(Example<S> example,
//					Function<FetchableFluentQuery<S>, R> queryFunction) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//		};
		
		StudyService studyService = new StudyService(memberService, studyRepository);
		
		assertNotNull(studyService);
		
	}
}
