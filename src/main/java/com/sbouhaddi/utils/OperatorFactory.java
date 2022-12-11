package com.sbouhaddi.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OperatorFactory {
	static Map<String, Operation> operationMap = new HashMap<String, Operation>();
	static {
		operationMap.put("+", new Addition());
		operationMap.put("/", new Division());
		operationMap.put("-", new Substraction());
		operationMap.put("*", new Multiplication());
		operationMap.put("^", new Power());

	}

	public static Optional<Operation> getOperation(String operator) {
		return Optional.ofNullable(operationMap.get(operator));
	}
}
