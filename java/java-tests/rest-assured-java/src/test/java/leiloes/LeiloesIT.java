package leiloes;

import static org.junit.Assert.*;

import org.junit.Before;

import static com.jayway.restassured.RestAssured.*;

import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import models.Leilao;
import models.Usuario;

public class LeiloesIT {

	private Usuario usuarioMauricio;
	private Leilao leilao;

	@Before
	public void init() {
        usuarioMauricio = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        leilao = new Leilao(1L, "Geladeira", 800.0, usuarioMauricio, false);
	}
	
	@Test
	public void deveRetornarLeilaoPeloId() {
		JsonPath path = given().header("Accept", "application/json")
							.parameter("leilao.id", 1)
						.when()
			        		.get("/leiloes/show")
						.andReturn().jsonPath();
		
		Leilao leilaoObtido = path.getObject("leilao", Leilao.class);
		
		assertEquals(leilao, leilaoObtido);
	}
	
    @Test
    public void deveRetornarQuantidadeDeLeiloes() {
        XmlPath path = given()
		                	.header("Accept", "application/xml")
		                .when()
		            		.get("/leiloes/total")
		                .andReturn().xmlPath();

        int total = path.getInt("int");

        assertEquals(2, total);
    }
    
    @Test
    public void deveInserirLeiloes() {
        Usuario mauricio = new Usuario(1l, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Leilao leiloes = new Leilao(1l, "Geladeira", 800.0, mauricio, false);

        XmlPath retorno = 
                given()
                    .header("Accept", "application/xml")
                    .contentType("application/xml")
                    .body(leiloes)
                .expect()
                    .statusCode(200)
                .when()
                    .post("/leiloes")
                .andReturn()
                    .xmlPath();

        Leilao resposta = retorno.getObject("leilao", Leilao.class);

        assertEquals("Geladeira", resposta.getNome());
    }
}
