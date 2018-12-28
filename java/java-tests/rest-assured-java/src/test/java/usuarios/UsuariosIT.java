package usuarios;

import static org.junit.Assert.*;

import java.util.List;

import static com.jayway.restassured.RestAssured.*;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import models.Usuario;


public class UsuariosIT {

	private Usuario USUARIO_MAURICIO;
	private Usuario USUARIO_GUILHERME;

	@Before
	public void init() {
        USUARIO_MAURICIO = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        USUARIO_GUILHERME = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");
	}
	
	@Test
	public void deveRetornarListaUsuariosEmXml() {
		XmlPath path = given()
							.header("Accept", "application/xml") 
						.when()
	                		.get("/usuarios")
						.andReturn().xmlPath();
		
		//Usuario usuario1 = path.getObject("list.usuario[0]", Usuario.class);
		//Usuario usuario2 = path.getObject("list.usuario[1]", Usuario.class);
		List<Usuario> usuarios = path.getList("list.usuario", Usuario.class);
		
		assertEquals(USUARIO_MAURICIO, usuarios.get(0));
		assertEquals(USUARIO_GUILHERME, usuarios.get(1));
	}
	
    @Test
    public void deveRetornarListaUsuariosEmJson() {
        JsonPath path = given()
		                	.header("Accept", "application/json")
		                .when()
		                	.get("/usuarios")
		                .andReturn().jsonPath();

        Usuario[] usuarios = path.getObject("list", Usuario[].class);

        assertEquals(USUARIO_MAURICIO, usuarios[0]);
        assertEquals(USUARIO_GUILHERME, usuarios[1]);
    }

    @Test
    public void deveRetornarUsuarioPeloId() {
        JsonPath path = given()
                			.header("Accept", "application/json")
            				.parameter("usuario.id", 1)
        				.when()
        					.get("/usuarios/show")
    					.andReturn().jsonPath();

        Usuario usuario = path.getObject("usuario", Usuario.class);

        assertEquals(USUARIO_MAURICIO, usuario);
    }
    
    @Test
    public void deveAdicionarERemoverUmUsuario() {
        Usuario joao = new Usuario("Joao da Silva", "joao@dasilva.com");

        XmlPath retorno = 
            given()
                .header("Accept", "application/xml")
                .contentType("application/xml")
                .body(joao)
            .expect()
                .statusCode(200)
            .when()
                .post("/usuarios")
            .andReturn()
                .xmlPath();

        Usuario resposta = retorno.getObject("usuario", Usuario.class);
        assertEquals("Joao da Silva", resposta.getNome());
        assertEquals("joao@dasilva.com", resposta.getEmail());

        given()
        	.contentType("application/xml")
        	.body(resposta)
    	.expect()
    		.statusCode(200)
		.when()
			.delete("/usuarios/deleta");
    }
}
