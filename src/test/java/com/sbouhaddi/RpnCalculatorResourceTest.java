package com.sbouhaddi;

import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sbouhaddi.model.RpnStack;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class RpnCalculatorResourceTest {

	@Test
	public void testRpnStackMocking() {

		PanacheMock.mock(RpnStack.class);
		// Mocked classes always return a default value
		Assertions.assertEquals(0, RpnStack.count());
		// Now let's specify the return value
		Mockito.when(RpnStack.count()).thenReturn(23L);
		Assertions.assertEquals(23, RpnStack.count());
		// Now let's call the original method
		Mockito.when(RpnStack.count()).thenCallRealMethod();
		Assertions.assertEquals(0, RpnStack.count());

		// Mock only with specific parameters
		RpnStack rpn = new RpnStack();
		Mockito.when(RpnStack.findById(12L)).thenReturn(rpn);
		Assertions.assertSame(rpn, RpnStack.findById(12L));
		Assertions.assertNull(RpnStack.findById(42L));
		
		// We can even mock your custom methods
		rpn.setStack(new Stack<Double>());
        Mockito.when(RpnStack.applyOperationToAstack(Mockito.anyString(), Mockito.any())).thenReturn(rpn);
        Assertions.assertTrue(rpn.getStack().isEmpty());

	}

}