package com.github.wesleyegberto.leiloes.paginas.leiloes;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.wesleyegberto.leiloes.paginas.CriadorCenarios;

public class LeiloesSystemTest {
	private FirefoxDriver driver;
	private LeiloesPage leiloesPage;

	final String USUARIO = "Odair Jose";
	private CriadorCenarios criadorCenarios;
	
	@Before
	public void init() {
		System.setProperty("webdriver.gecko.driver", "/opt/webdrivers/firefox/geckodriver");
		driver = new FirefoxDriver();
		
		// Efetua limpeza do banco (deve ser conversado com a equipe uma forma de fazer isso)
		driver.get("http://localhost:8080/apenas-teste/limpa");
		
		criadorCenarios = new CriadorCenarios(driver);
		criadorCenarios.comUsuario(USUARIO, "odairjose@mail.com");
		
		leiloesPage = new LeiloesPage(driver);
		leiloesPage.visita();
	}

	@After
	public void destroy() {
		driver.close();
		((RemoteWebDriver) driver).quit();
	}

	@Test
	public void deveCadastrarLeilao() {
		NovoLeilaoPage novoLeilaoPage = leiloesPage.novo();
		final String NOME_ITEM = "PlayStation 5";
		final double VALOR_ITEM = 1000.0;
		final boolean USADO = true;
		
		novoLeilaoPage.cadastra(NOME_ITEM, VALOR_ITEM, USUARIO, USADO);
		leiloesPage.aguardaCarregamentoPaginaListagem();
		
		assertTrue(leiloesPage.existeNaListagem(NOME_ITEM, VALOR_ITEM, USUARIO, USADO));
	}

	@Test
	public void naoDeveCadastrarLeilaoSemNome() {
		NovoLeilaoPage novoLeilaoPage = leiloesPage.novo();
		final String NOME_ITEM = "";
		final double VALOR_ITEM = 1000.0;
		final boolean USADO = true;
		
		novoLeilaoPage.cadastra(NOME_ITEM, VALOR_ITEM, USUARIO, USADO);
		novoLeilaoPage.aguardaRespostaCadastro();
		
		assertTrue(novoLeilaoPage.validacaoNomeApareceu());
	}
}
