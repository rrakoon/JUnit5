package com.study.JUnit5;

import java.util.stream.Stream;

public class StringParams {
	static Stream<String> blankStrings() {
		return Stream.of(null, "", "  ");
	}
}