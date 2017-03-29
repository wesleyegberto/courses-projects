package com.github.wesleyegberto.leiloes.paginas.leiloes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NovoLeilaoPage {

	private WebDriver driver;

	public NovoLeilaoPage(WebDriver driver) {
		this.driver = driver;
	}

	public LeiloesPage cadastra(String nome, double valor, String usuario, boolean usado) {
		WebElement txtNome = driver.findElement(By.name("leilao.nome"));
		WebElement txtValor = driver.findElement(By.name("leilao.valorInicial"));
		Select slcUsuario = new Select(driver.findElement(By.name("leilao.usuario.id")));
		txtNome.sendKeys(nome);
		txtValor.sendKeys(String.valueOf(valor));
		slcUsuario.selectByVisibleText(usuario);
		if (usado) {
			driver.findElement(By.name("leilao.usado")).click();
		}
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		return new LeiloesPage(driver);
	}

	public boolean validacaoNomeApareceu() {
		return driver.getPageSource().contains("Nome obrigatorio");
	}

	public void aguardaRespostaCadastro() {
		new WebDriverWait(driver, 3)
			.withMessage("Tempo de resposta de um cadastro excedido")
			.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
	}

}
