import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.jayway.restassured.RestAssured;


import leiloes.LeiloesIT;
import usuarios.UsuariosIT;

@RunWith(Suite.class)
@SuiteClasses({ UsuariosIT.class, LeiloesIT.class })
public class LeilaoSuit {

	@BeforeClass
	public static void prepara() {
		System.out.println("Preparando");

		// Default
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}
}
