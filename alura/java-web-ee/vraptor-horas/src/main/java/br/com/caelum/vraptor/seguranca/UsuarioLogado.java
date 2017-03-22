package br.com.caelum.vraptor.seguranca;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import br.com.caelum.vraptor.model.Usuario;

@Named
@SessionScoped
public class UsuarioLogado implements Serializable {
	private static final long serialVersionUID = -759588047565301700L;
	private Usuario usuario;

	public void fazLogin(Usuario usuario) {
		this.usuario = usuario;
	}

	public void desloga() {
		this.usuario = null;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public boolean isLogado() {
		return this.usuario != null;
	}
}