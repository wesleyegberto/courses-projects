package br.com.caelum.xstream;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class ConverterTest {

	@Test
	public void testPrecoConverter() {
		String resultadoEsperado = "<produto codigo=\"1587\">\n" +
		        "  <nome>geladeira</nome>\n" +
		        "  <preco>R$ 1.000,00</preco>\n" +
		        "  <descrição>geladeira duas portas</descrição>\n" +
		        "</produto>";
		
		Produto produto = new Produto("geladeira", 1000.0, "geladeira duas portas", 1587);
		
		XStream xstream = criaXStream();
		xstream.registerLocalConverter(Produto.class, "preco", new PrecoSimplesConverter());
		
		String xmlGerado = xstream.toXML(produto);
		
		assertEquals(xmlGerado, resultadoEsperado);
	}

	private XStream criaXStream() {
		XStream xstream = new XStream();
		xstream.alias("produto", Produto.class);
        xstream.aliasField("descrição", Produto.class, "descricao");
        xstream.useAttributeFor(Produto.class, "codigo");
		return xstream;
	}
	
	@Test
	public void deveUsarUmConversorDiferente() {
		String resultadoEsperado = "<compra estilo=\"novo\">\n" 
				+ "  <id>15</id>\n"
		        + "  <fornecedor>guilherme.silveira@caelum.com.br</fornecedor>\n"
		        + "  <endereco>\n"
		        + "    <linha1>Rua Vergueiro 3185</linha1>\n"
		        + "    <linha2>8 andar - Sao Paulo - SP</linha2>\n"
		        + "  </endereco>\n"
		        + "  <produtos>\n" 
		        + "    <produto codigo=\"1587\">\n"
		        + "      <nome>geladeira</nome>\n"
		        + "      <preco>1000.0</preco>\n"
		        + "      <descrição>geladeira duas portas</descrição>\n"
		        + "    </produto>\n"
		        + "    <produto codigo=\"1587\">\n"
		        + "      <nome>geladeira</nome>\n"
		        + "      <preco>1000.0</preco>\n"
		        + "      <descrição>geladeira duas portas</descrição>\n"
		        + "    </produto>\n"
		        + "  </produtos>\n" 
		        + "</compra>";
		  
		List<Produto> produtos = new ArrayList<Produto>();
		produtos.add(new Produto("geladeira", 1000, "geladeira duas portas", 1587));
		produtos.add(new Produto("geladeira", 1000, "geladeira duas portas", 1587));
		Compra compra = new Compra(15, produtos);
		 
		XStream xstream = new XStream();
		xstream.alias("compra", Compra.class);
		xstream.alias("produto", Produto.class);
		xstream.aliasField("descrição", Produto.class, "descricao");
		xstream.useAttributeFor(Produto.class, "codigo");
		xstream.registerConverter(new CompraDiferenteConverter());
		
		String xmlGerado = xstream.toXML(compra);

		assertEquals(resultadoEsperado, xmlGerado);
		 
        Compra deserializada = (Compra) xstream.fromXML(xmlGerado);
        assertEquals(compra, deserializada);
	 }
}
