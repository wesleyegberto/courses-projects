package br.com.caelum.livraria.bean;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class LivrariaException extends Exception {
	private static final long serialVersionUID = -2492727106330660361L;
	
}