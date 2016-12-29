package com.github.wesleyegberto.javaeetesting;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiTestRunner.class)
public class ProductManagerIT {

	@Inject
	ProductManager productManager;
	
	@Test
	public void should_inject() {
		assertNotNull(productManager);
		String expected = "up";
		String current = productManager.status();
		assertThat(current, is(expected));
	}
}
