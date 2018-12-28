package com.github.wesleyegberto.javaeetesting;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LoggerProducer {

	@Produces
	public Logger loggerProducer(InjectionPoint ip) {
		Class<?> clazz = ip.getMember().getDeclaringClass();
		return Logger.getLogger(clazz.getName());
	}
}
