package br.com.caelum.vraptor.interceptors;

import javax.inject.Inject;

import br.com.caelum.vraptor.AfterCall;
import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.AcceptsForPackages;

@Intercepts
// Intercepta os métodos das classes do pacote
@AcceptsForPackages(value = { "br.com.caelum.vraptor.controller" })
public class LogInterceptor {

	private final ControllerMethod method;
	
	public LogInterceptor() {
		this(null);
	}
	
	@Inject
	public LogInterceptor(ControllerMethod method) {
		this.method = method;
	}

	@BeforeCall
	public void beforeCall() {
		System.out.println("Chamando " + method.getMethod().getName());
	}
	
	@AfterCall
	public void afterCall() {
		System.out.println("Finalizou " + method.getMethod().getName());
	}
}
