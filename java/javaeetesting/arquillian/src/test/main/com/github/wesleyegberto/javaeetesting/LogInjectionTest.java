package com.github.wesleyegberto.javaeetesting;

import java.util.logging.Logger;

import javax.inject.Inject;

public class LogInjectionTest {

	@Inject
	Logger log;
	
	public Logger getLog() {
		return log;
	}
}
