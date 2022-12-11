package com.sbouhaddi.utils;

public class Power implements Operation {
	@Override
	public Double apply(Double a, Double b) {
		return Math.pow(a, b);
	}
}
