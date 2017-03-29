package com.github.wesleyegberto.leiloes.paginas.usuarios;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UsuariosPage {

	private static final int SLA_PAGINA_CADASTRO = 3;
	private static final int SLA_PAGINA_USUARIOS = 3;
	private WebDriver driver;

	public UsuariosPage(WebDriver driver) {
		this.driver = driver;
	}

	public void visita() {
		driver.get("http://localhost:8080/usuarios");
		aguardaCarregamentoPaginaListagem();
	}

	public NovoUsuarioPage novo() {
		driver.findElement(By.linkText("Novo Usuário")).click();
		aguardaCarregamentoPaginaCadastro();
		return new NovoUsuarioPage(driver);
	}

	public boolean existeNaListagem(String nome, String email) {
		return driver.getPageSource().contains(nome)
				&& driver.getPageSource().contains(email);		
	}

	public void excluiUsuarioNaPosicao(int posicao) {
		driver.findElements(By.tagName("button")).get(posicao-1).click();
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public EdicaoUsuarioPage editarUsuarioPosicao(int posicao) {
		driver.findElements(By.linkText("editar")).get(posicao-1).click();
		aguardaCarregamentoPaginaCadastro();
		return new EdicaoUsuarioPage(driver);
	}

	public void aguardaCarregamentoPaginaListagem() {
		new WebDriverWait(driver, SLA_PAGINA_USUARIOS)
			.withMessage("Tempo de carregamento dos usuários excedido")
			.until(textToBe(By.tagName("h1"), "Todos os Usuários"));
	}

	public void aguardaCarregamentoPaginaCadastro() {
		new WebDriverWait(driver, SLA_PAGINA_CADASTRO)
			.withMessage("Tempo de carregamento do cadastro excedido")
			.until(ExpectedConditions.elementToBeClickable(By.id("btnSalvar")));
	}

	public void aguardaRetornoExclusa() {
		new WebDriverWait(driver, 3)
			.withMessage("Tempo de retorno de exclusão excedido")
			.until(numberOfElementsToBe(By.xpath("//table/tbody/tr"), 1));
	}

}
