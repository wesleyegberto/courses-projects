package com.github.wesleyegberto.leiloes.paginas.usuarios;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NovoUsuarioPage {

	private WebDriver driver;

	public NovoUsuarioPage(WebDriver driver) {
		this.driver = driver;
	}

	public void visita() {
		driver.get("http://localhost:8080/usuarios/new");
	}

	public UsuariosPage cadastra(String nome, String email) {
		WebElement textNome = driver.findElement(By.name("usuario.nome"));
		WebElement textEmail = driver.findElement(By.name("usuario.email"));
		textNome.sendKeys(nome);
		textEmail.sendKeys(email);
		driver.findElement(By.id("btnSalvar")).click();
		return new UsuariosPage(driver);
	}

	public boolean validacaoNomeApareceu() {
		return driver.getPageSource().contains("Nome obrigatorio");
	}

	public boolean validacaoEmailApareceu() {
		return driver.getPageSource().contains("E-mail obrigatorio");
	}
}
