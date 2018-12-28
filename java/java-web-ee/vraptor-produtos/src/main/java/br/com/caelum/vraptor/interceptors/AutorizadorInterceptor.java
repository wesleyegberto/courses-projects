package br.com.caelum.vraptor.interceptors;

import javax.inject.Inject;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.annotations.Public;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.LoginController;
import br.com.caelum.vraptor.controller.UsuarioLogado;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;

@Intercepts
public class AutorizadorInterceptor {

	private final UsuarioLogado usuarioLogado;
	private final Result result;
	private final Validator validator;
	private final ControllerMethod method;
	
	public AutorizadorInterceptor() {
		this(null, null, null, null);
	}
	
	@Inject
	public AutorizadorInterceptor(UsuarioLogado usuarioLogado, Result result, Validator validator, ControllerMethod method) {
		this.usuarioLogado = usuarioLogado;
		this.result = result;
		this.validator = validator;
		this.method = method;
	}
	
	@Accepts
	public boolean accepts() {
	    return !method.containsAnnotation(Public.class);
	}

	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		if(!usuarioLogado.temSessaoAtiva()) {
			validator.add(new I18nMessage("usuario", "autenticacao.semsessao"));
			result.redirectTo(LoginController.class).formulario();
			return;
		}
		// continua a corrente de interceptadores
		stack.next();
	}
}
