package com.github.wesleyegberto.javaeetesting.home;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public class HomeIT {
	
	@Drone
	WebDriver browser;

	@Test
	public void should_have_tittle_Java_EE() {
		browser.get("http://airhacks.com");
		String expected = "Java EE";
		assertThat(browser.getTitle(), containsString(expected));
	}
}
