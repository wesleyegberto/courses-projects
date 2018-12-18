package br.com.caelum.vraptor.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.dao.UsuarioDAO;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.caelum.vraptor.model.Usuario;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class UsuarioController {
	private Result result;
	private UsuarioDAO usuarioDAO;
	private Validator validator;

	public UsuarioController() {
	}

	@Inject
	public UsuarioController(Result result, UsuarioDAO usuarioDAO, Validator validator) {
		this.result = result;
		this.usuarioDAO = usuarioDAO;
		this.validator = validator;
	}

	public void form() {
	}

	@Post
	@IncludeParameters 
	public void adiciona(@Valid Usuario usuario) {
		validator.onErrorRedirectTo(this).form();
		usuarioDAO.adiciona(usuario);
		// @IncludeParameters informa que todos os parâmetros 
		// devem ser adicionados no result
		// result.include("usuario", usuario);
		result.redirectTo(this).lista();
	}

	public void lista() {
		result.include("usuarios", usuarioDAO.lista());
	}
}