package com.github.wesleyegberto.javaeetesting;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ProductManagerIT {

	@Inject
	ProductManager productManager;
	
	@Deployment
	public static WebArchive deploy() {
		return ShrinkWrap.create(WebArchive.class)
				.addClasses(ProductManager.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
		
	@Test
	public void should_inject() {
		assertNotNull(productManager);
		String expected = "up";
		String current = productManager.status();
		assertThat(current, is(expected));
	}
}
