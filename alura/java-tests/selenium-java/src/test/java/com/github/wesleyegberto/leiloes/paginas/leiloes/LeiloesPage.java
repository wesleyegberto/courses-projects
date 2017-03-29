package com.github.wesleyegberto.leiloes.paginas.leiloes;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LeiloesPage {
	private static final int SLA_PAGINA_LEILOES = 5;
	private static final int SLA_PAGINA_CADASTRO = 3;
	
	private WebDriver driver;

	public LeiloesPage(WebDriver driver) {
		this.driver = driver;
	}

	public void visita() {
		driver.get("http://localhost:8080/leiloes");
	}

	public NovoLeilaoPage novo() {
		driver.findElement(By.linkText("Novo Leilão")).click();
		aguardaCarregamentoPaginaCadastro();
		return new NovoLeilaoPage(driver);
	}

	public boolean existeNaListagem(String nome, double valor, String usuario, boolean usado) {
		String pageSource = driver.getPageSource();
		return pageSource.contains(nome)
				&& pageSource.contains(String.valueOf(valor))
				&& pageSource.contains(usuario)
				&& pageSource.contains(usado ? "Sim" : "Não");
	}

	public void aguardaCarregamentoPaginaListagem() {
		new WebDriverWait(driver, SLA_PAGINA_LEILOES)
			.withMessage("Tempo de carregamento dos leilões excedido")
			.until(textToBe(By.tagName("h1"), "Todos leilões"));
	}

	private void aguardaCarregamentoPaginaCadastro() {
		new WebDriverWait(driver, SLA_PAGINA_CADASTRO)
			.withMessage("Tempo de carregamento do cadastro de leilão excedido")
			.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
	}

	public DetalhesLeilaoPage exibeLeilaoNaPosicao(int posicao) {
		List<WebElement> elementos = driver.findElements(By.linkText("exibir"));
        elementos.get(posicao - 1).click();
        return new DetalhesLeilaoPage(driver);
	}
}
