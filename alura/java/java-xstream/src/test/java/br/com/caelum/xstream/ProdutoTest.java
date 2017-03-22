package br.com.caelum.xstream;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class ProdutoTest {

    @Test
    public void deveGerarXmlComNomePrecoEDescricaoAdequados() {
        String resultadoEsperado = "<produto codigo=\"100\">\n" +
                "  <nome>geladeira</nome>\n" +
                "  <preco>1000.0</preco>\n" +
                "  <descricao>geladeira duas portas</descricao>\n" +
                "</produto>";

        Produto geladeira = new Produto("geladeira", 1000, "geladeira duas portas", 100);

        XStream xstream = new XStream();
        xstream.alias("produto", Produto.class);
        xstream.useAttributeFor(Produto.class, "codigo");

        String xmlGerado = xstream.toXML(geladeira);

        assertEquals(resultadoEsperado, xmlGerado);
    }

    @Test
    public void deveGerarXmlComNomePrecoEDescricaoAcentuada() {
        String resultadoEsperado = "<produto codigo=\"100\">\n" +
                "  <nome>geladeira</nome>\n" +
                "  <preco>1000.0</preco>\n" +
                "  <descrição>geladeira duas portas</descrição>\n" +
                "</produto>";

        Produto geladeira = new Produto("geladeira", 1000, "geladeira duas portas", 100);

        XStream xstream = new XStream();
        xstream.alias("produto", Produto.class);
        xstream.useAttributeFor(Produto.class, "codigo");
        xstream.aliasField("descrição", Produto.class, "descricao");

        String xmlGerado = xstream.toXML(geladeira);

        assertEquals(resultadoEsperado, xmlGerado);
    }
}