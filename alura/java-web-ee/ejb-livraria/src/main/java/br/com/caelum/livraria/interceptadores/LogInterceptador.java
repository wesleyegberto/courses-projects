package br.com.caelum.livraria.interceptadores;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LogInterceptador {

	@AroundInvoke
	public Object intercepta(InvocationContext context) throws Exception {
		long msInicial = System.currentTimeMillis();
		Object o = context.proceed();
		long msFinal = System.currentTimeMillis();
		
		String nomeClasse = context.getMethod().getDeclaringClass().getSimpleName();
		String nomeMetodo = context.getMethod().getName();
		System.out.printf("Tempo gasto no %s.%s: %d\n", nomeClasse, nomeMetodo, (msFinal - msInicial));
		
		return o;
	}
}
