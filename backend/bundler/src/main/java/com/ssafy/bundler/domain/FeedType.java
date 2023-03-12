package com.ssafy.bundler.domain;

public enum FeedType {

	CARD(Values.CARD),
	BUNDLE(Values.BUNDLE);

	private String value;

	FeedType(String val) {
		if (!this.name().equals(val))
			throw new IllegalArgumentException("Incorrect use of FeedType.");
	}

	public static class Values {
		public static final String CARD = "CARD";
		public static final String BUNDLE = "BUNDLE";
	}

}
