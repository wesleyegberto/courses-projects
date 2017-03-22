package br.com.caelum.vraptor.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.dao.UsuarioDAO;
import br.com.caelum.vraptor.model.Usuario;
import br.com.caelum.vraptor.seguranca.Open;
import br.com.caelum.vraptor.seguranca.UsuarioLogado;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class LoginController {
	private UsuarioLogado usuarioLogado;
	private UsuarioDAO usuarioDao;
	private Validator validator;
	private Result result;

	@Inject
	public LoginController(UsuarioLogado usuarioLogado, UsuarioDAO usuarioDao, Validator validator, Result result) {
		this.usuarioLogado = usuarioLogado;
		this.usuarioDao = usuarioDao;
		this.validator = validator;
		this.result = result;
	}

	public LoginController() {
	}

	@Open
	public void form() {
	}

	@Open
	@Post
	public void login(String login, String senha) {
		Usuario usuario = usuarioDao.busca(login, senha);
		if (usuario != null) {
			usuarioLogado.fazLogin(usuario);
			result.redirectTo(IndexController.class).index();
		} else {
			validator.add(new SimpleMessage("login_invalido", "Login ou senha incorretos"));
			validator.onErrorRedirectTo(this).form();
		}
	}

	public void desloga() {
		usuarioLogado.desloga();
		result.redirectTo(this).form();
	}
}