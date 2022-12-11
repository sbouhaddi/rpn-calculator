package com.sbouhaddi;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Stack;

import org.junit.jupiter.api.Test;

import com.sbouhaddi.api.RpnCalculatorResource;
import com.sbouhaddi.model.RpnStack;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;

@QuarkusIntegrationTest
@TestHTTPEndpoint(RpnCalculatorResource.class)
public class RpnCalculatorResourceIT {
    
	@Test
	public void getAllStacks() {
		given().when().get("/stack").then().statusCode(200);
	}

	@Test
	public void getByIdNotFound() {
		given().when().get("/stack/{id}", 987654321).then().statusCode(404);
	}

	@Test
	public void createNewStack() {

		RpnStack stack = createStack();
		RpnStack rpnStack = given().contentType(ContentType.JSON).body(stack).post("/stack").then().statusCode(201)
				.extract().as(RpnStack.class);
		assertThat(rpnStack).isNotNull();
	}

	@Test
	public void createStackFailNoStack() {
		RpnStack stack = createStack();
		stack.setStack(null);
		given().contentType(ContentType.JSON).body(stack).post("/stack").then().statusCode(400);
	}

	private RpnStack createStack() {
		RpnStack rpnStack = new RpnStack();
		Stack<Double> stack = new Stack<>();
		rpnStack.setStack(stack);
		return rpnStack;
	}
}
