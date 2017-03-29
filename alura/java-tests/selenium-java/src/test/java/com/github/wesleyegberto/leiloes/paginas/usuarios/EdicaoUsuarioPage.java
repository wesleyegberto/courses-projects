package com.github.wesleyegberto.leiloes.paginas.usuarios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EdicaoUsuarioPage {

	private WebDriver driver;

	public EdicaoUsuarioPage(WebDriver driver) {
		this.driver = driver;
	}

	public UsuariosPage altera(String nome, String email) {
		WebElement textNome = driver.findElement(By.name("usuario.nome"));
		WebElement textEmail = driver.findElement(By.name("usuario.email"));
		textNome.clear();
		textNome.sendKeys(nome);
		textEmail.clear();
		textEmail.sendKeys(email);
		driver.findElement(By.id("btnSalvar")).click();
		return new UsuariosPage(driver);
	}
}
