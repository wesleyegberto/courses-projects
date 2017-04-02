package br.com.caelum.livraria.bean;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class VerificadorEstoque {

	@Schedule(hour = "*", minute = "*/30")
	public void verificaEstoque() {
		System.out.println("Verificando estoque dos livros...");
	}
}
