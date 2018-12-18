package com.github.wesleyegberto.leiloes.paginas.leiloes;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.wesleyegberto.leiloes.paginas.CriadorCenarios;

public class LancesSystemTest {
	private FirefoxDriver driver;

	private static final String NOME_USUARIO_DONO = "Odair Jose";
	private static final String NOME_USUARIO_LANCE = "Jose Odair";

	private LeiloesPage leiloesPage;

	@Before
	public void init() {
		System.setProperty("webdriver.gecko.driver", "/opt/webdrivers/firefox/geckodriver");
		driver = new FirefoxDriver();
		// Efetua limpeza do banco (deve ser conversado com a equipe uma forma de fazer isso)
		driver.get("http://localhost:8080/apenas-teste/limpa");
		preparaCenario();
		
		leiloesPage = new LeiloesPage(driver);
		leiloesPage.visita();
	}

	private void preparaCenario() {
		new CriadorCenarios(driver)
			.comUsuario(NOME_USUARIO_DONO, "odairjose@mail.com")
			.comUsuario(NOME_USUARIO_LANCE, "joseodair@mail.com")
			.comLeilao("PlayStation 5", 500, NOME_USUARIO_DONO, true);
	}

	@After
	public void destroy() {
		driver.close();
		((RemoteWebDriver) driver).quit();
	}
	
	@Test
	public void usuarioPodeDarLance() {
		DetalhesLeilaoPage detalhesPage = leiloesPage.exibeLeilaoNaPosicao(1);
		detalhesPage.aguardaCarregamentoPaginaDetalhes();
		double valorLance = 600.0;
		detalhesPage.efetuaLance(NOME_USUARIO_LANCE, valorLance);
		assertTrue(detalhesPage.existeLance(NOME_USUARIO_LANCE, valorLance));
	}
}
