package com.study.JUnit5;

public class Study {

	private StudyStatus status = StudyStatus.DRAFT;
	//private StudyStatus status;

	private int limit;
	
	
	public Study(int limit) {
		if(limit < 0) {
			throw new IllegalArgumentException("limit은 0보다 커야함");
		}
		this.limit = limit;
	}

	public Object getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}

	public int getLimit() {
		return limit;
	}

	
}
