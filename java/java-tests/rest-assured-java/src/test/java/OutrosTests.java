import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;

import java.io.File;

import org.junit.Test;

public class OutrosTests {
	@Test
	public void deveGerarUmCookie() {
		expect()
			.cookie("rest-assured", "funciona")
		.when()
			.get("/cookie/teste");
	}

	@Test
    public void deveGerarUmHeader() {
        expect()
        	.header("novo-header", "abc")
		.when()
		    .get("/cookie/teste");
	}
	
	public void deveFazerUploadArquivo() {
		given()
			.multiPart(new File("/path/para/arquivo")).
		when()
			.post("/upload");
	}
}
