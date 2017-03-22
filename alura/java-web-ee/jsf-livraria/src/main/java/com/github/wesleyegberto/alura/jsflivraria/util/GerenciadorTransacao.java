package com.github.wesleyegberto.alura.jsflivraria.util;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
public class GerenciadorTransacao {

	@Inject
	private EntityManager manager;
	
	@AroundInvoke
	public Object executaTX(InvocationContext contexto) throws Exception {
	    manager.getTransaction().begin();
	    Object resultado = contexto.proceed();
	    manager.getTransaction().commit();
	    return resultado;
	}
}
