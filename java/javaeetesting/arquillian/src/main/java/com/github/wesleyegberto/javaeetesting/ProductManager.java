package com.github.wesleyegberto.javaeetesting;

import java.util.logging.Logger;

import javax.inject.Inject;

public class ProductManager {
	
	@Inject
	Logger LOG;
	
	public String status() {
		return "up";
	}
}
