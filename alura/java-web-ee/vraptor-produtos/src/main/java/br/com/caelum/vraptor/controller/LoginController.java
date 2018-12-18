package br.com.caelum.vraptor.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.annotations.Public;
import br.com.caelum.vraptor.dao.UsuarioDao;
import br.com.caelum.vraptor.model.Usuario;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class LoginController {

	private final UsuarioDao dao;
	private final UsuarioLogado usuarioLogado;
	private final Result result;
	private final Validator validator;
	
	public LoginController() {
		this(null, null, null, null);
	}
	
	@Inject
	public LoginController(UsuarioDao dao, UsuarioLogado usuarioLogado, Result result, Validator validator) {
		this.dao = dao;
		this.usuarioLogado = usuarioLogado;
		this.result = result;
		this.validator = validator;
	}

	@Get @Public
	public void formulario() {
	}
	
	@Post @Public
	public void autentica(Usuario usuario) {
		if(!dao.existe(usuario)) {
			validator.add(new I18nMessage("usuario", "usuario.invalido"));
			validator.onErrorUsePageOf(this).formulario();
		}
		usuarioLogado.setUsuario(usuario);
		result.redirectTo(ProdutoController.class).lista();
	}
}
