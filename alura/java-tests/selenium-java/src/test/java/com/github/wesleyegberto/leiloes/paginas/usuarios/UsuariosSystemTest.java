package com.github.wesleyegberto.leiloes.paginas.usuarios;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.wesleyegberto.leiloes.paginas.CriadorCenarios;

public class UsuariosSystemTest {

	private WebDriver driver;
	private UsuariosPage usuariosPage;

	@Before
	public void init() {
		// seta o diretório do WebDriver
		System.setProperty("webdriver.gecko.driver", "/opt/webdrivers/firefox/geckodriver");
		
		// Cria o WebDriver
		driver = new FirefoxDriver();
		
		// Efetua limpeza do banco (deve ser conversado com a equipe uma forma de fazer isso)
		driver.get("http://localhost:8080/apenas-teste/limpa");

		// Padrão Page Object permite centralizarmos a manipulação dos elementos
		// específicos de cada tela, assim podemos reutilizar também na criação de cenários
		usuariosPage = new UsuariosPage(driver);
		usuariosPage.visita();
	}

	@After
	public void destroy() {
		driver.close();
		// O close() não fecha o browser
		((RemoteWebDriver) driver).quit();
	}

	@Test
	public void naTelaDeListagemUsuarioDeveNavegarParaCadastroUsuario() {
		usuariosPage.novo();
		// Sem Page Object esse código seria espalhado por toda a classe
		// e por outras classes que precise criar um usuário
		assertNotNull(driver.findElement(By.name("usuario.nome")));
		assertNotNull(driver.findElement(By.name("usuario.email")));
	}

	@Test
	public void deveAdicionarUmUsuario() {
		final String NOME = "Ronaldo Luiz de Albuquerque";
		final String EMAIL = "ronaldo2009@terra.com.br";
		NovoUsuarioPage novoUsuarioPage = usuariosPage.novo();
		novoUsuarioPage.cadastra(NOME, EMAIL);
		// Aguarda um tempo até que a condição do until() seja verdadeira
		// ou ocorra o timeout
		new WebDriverWait(driver, 5)
			.withMessage("Tempo de resposta de um cadastro excedido")
			.until(textToBe(By.tagName("h1"), "Todos os Usuários"));
		assertTrue(usuariosPage.existeNaListagem(NOME, EMAIL));
	}

	@Test
	public void naoDeveAdicionarUsuarioSemNomeMasComEmail() {
		NovoUsuarioPage novoUsuarioPage = usuariosPage.novo();
		novoUsuarioPage.cadastra("", "joseluis@terra.com.br");
		new WebDriverWait(driver, 2)
			.withMessage("Tempo de resposta excedido")
			.until(elementToBeClickable(By.id("btnSalvar")));
		assertTrue(novoUsuarioPage.validacaoNomeApareceu());
	}

	@Test
	public void naoDeveAdicionarUsuarioSemNomeSemEmail() {
		NovoUsuarioPage novoUsuarioPage = usuariosPage.novo();
		novoUsuarioPage.cadastra("", "");
		new WebDriverWait(driver, 2)
			.withMessage("Tempo de resposta excedido")
			.until(elementToBeClickable(By.id("btnSalvar")));
		
		assertTrue(novoUsuarioPage.validacaoNomeApareceu());
		assertTrue(novoUsuarioPage.validacaoEmailApareceu());
	}

	@Test
	public void deveExcluirUmUsuarioCadastrado() {
		final String NOME = "Usuario Para Exclusao";
		final String EMAIL = "emailusuario@exclusao.com.br";
		new CriadorCenarios(driver).comUsuario(NOME, EMAIL);
		
		usuariosPage.excluiUsuarioNaPosicao(1);
		usuariosPage.aguardaRetornoExclusa();
		
		assertFalse(usuariosPage.existeNaListagem(NOME, EMAIL));
	}

	@Test
	public void deveAlterarUmUsuarioCadastrado() {
		final String NOME = "Odair Alteracao";
		final String EMAIL = "emailusuario@alteracao.com.br";
		new CriadorCenarios(driver).comUsuario(NOME, EMAIL);
		String NOME_ALTERADO = "Odair Jose Alterado";
		String EMAIL_ALTERADO = "odair.jose@alterado.com";
		
		EdicaoUsuarioPage edicaoUsuarioPage = usuariosPage.editarUsuarioPosicao(1);
		UsuariosPage usuariosPage = edicaoUsuarioPage.altera(NOME_ALTERADO, EMAIL_ALTERADO);
		usuariosPage.aguardaCarregamentoPaginaListagem();
		
		assertFalse(usuariosPage.existeNaListagem(NOME, EMAIL));
		assertTrue(usuariosPage.existeNaListagem(NOME_ALTERADO, EMAIL_ALTERADO));
	}
}