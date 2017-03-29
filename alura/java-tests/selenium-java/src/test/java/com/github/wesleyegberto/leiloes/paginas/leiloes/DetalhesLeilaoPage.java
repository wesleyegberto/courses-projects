package com.github.wesleyegberto.leiloes.paginas.leiloes;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DetalhesLeilaoPage {

	private static final int SLA_FEEDBACK_LANCE = 10;
	private WebDriver driver;

	public DetalhesLeilaoPage(WebDriver driver) {
		this.driver = driver;
	}

	public void visita() {
		driver.get("http://localhost:8080/leiloes");
		aguardaCarregamentoPaginaDetalhes();
	}

	public void aguardaCarregamentoPaginaDetalhes() {
		new WebDriverWait(driver, 5)
			.withMessage("Tempo de carregamento dos detalhes do leilão excedido")
			.until(presenceOfElementLocated(By.id("lancesDados")));
	}

	public void efetuaLance(String usuario, double valor) {
		Select slcUsuario = new Select(driver.findElement(By.name("lance.usuario.id")));
		WebElement txtValor = driver.findElement(By.name("lance.valor"));
		slcUsuario.selectByVisibleText(usuario);
		txtValor.sendKeys(String.valueOf(valor));
		driver.findElement(By.id("btnDarLance")).click();
	}

	public boolean existeLance(String usuario, double valorLance) {
		boolean encontrouUsuario = new WebDriverWait(driver, SLA_FEEDBACK_LANCE)
						.withMessage("Tempo de resposta de um lance excedido")
					    .until(textToBePresentInElementLocated(By.id("lancesDados"), usuario));
		if(encontrouUsuario) {
			return driver.getPageSource().contains(String.valueOf(valorLance));
		}
		return false;
	}

}
