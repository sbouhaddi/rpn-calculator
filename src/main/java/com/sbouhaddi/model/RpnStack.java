package com.sbouhaddi.model;

import java.util.Stack;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sbouhaddi.utils.Operation;
import com.sbouhaddi.utils.OperatorFactory;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class RpnStack extends PanacheEntityBase {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	@Lob
	@NotNull
	private Stack<Double> stack;

	public static RpnStack applyOperationToAstack(String operation, RpnStack rpnStack) {

		Operation targetOperation = OperatorFactory.getOperation(operation)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Operator"));
		log.info("operation to execute" + operation);
		Stack<Double> activeStack = rpnStack.getStack();
		double secondOperand = activeStack.pop();
		double firstOperand = activeStack.pop();
		activeStack.push(targetOperation.apply(firstOperand, secondOperand));
		rpnStack.setStack(activeStack);
		RpnStack.persist(rpnStack);
		return rpnStack;

	}

}
