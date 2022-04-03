package com.study.JUnit5.study;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.JUnit5.domian.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
