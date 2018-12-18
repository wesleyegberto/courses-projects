package com.github.wesleyegberto.alura.springmvc.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// Controller que observa os outros oferecendo recursos
@ControllerAdvice
public class ExceptionHandlerController {

	// Podemos criar handler genérico ou em cada controller
	@ExceptionHandler
	public ModelAndView handleException(Exception ex) {
		ex.printStackTrace();
		return new ModelAndView("error", "exception", ex);
	}
}
