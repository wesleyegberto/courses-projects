package com.github.wesleyegberto.leiloes.paginas;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import com.github.wesleyegberto.leiloes.paginas.leiloes.LeiloesPage;
import com.github.wesleyegberto.leiloes.paginas.usuarios.NovoUsuarioPage;
import com.github.wesleyegberto.leiloes.paginas.usuarios.UsuariosPage;

public class CriadorCenarios {

	private WebDriver driver;

	public CriadorCenarios(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Cria um usuário para ser utilizado no cenário.
	 */
	public CriadorCenarios comUsuario(final String NOME, final String EMAIL) {
		UsuariosPage usuariosPage = new UsuariosPage(driver);
		usuariosPage.visita();
		NovoUsuarioPage novoUsuarioPage = usuariosPage.novo();
		novoUsuarioPage.cadastra(NOME, EMAIL);
		usuariosPage.aguardaCarregamentoPaginaListagem();
		assertTrue(usuariosPage.existeNaListagem(NOME, EMAIL));
		return this;
	}

	/** 
	 * Cria um leilão para ser utilizado no cenário.
	 */
	public CriadorCenarios comLeilao(String nome, double valorInicial, String usuario, boolean usado) {
		LeiloesPage leiloesPage = new LeiloesPage(driver);
		leiloesPage.visita();
		leiloesPage.novo().cadastra(nome, 500, usuario, usado);
		leiloesPage.aguardaCarregamentoPaginaListagem();
		return this;
	}
}
