package br.com.caelum.xstream;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class CompraTest {

    @Test
    public void deveSerializarCadaUmDosProdutosDeUmaCompra() {
        String resultadoEsperado = "<compra>\n"+
            "  <id>15</id>\n"+
            "  <produtos>\n"+
            "    <produto codigo=\"1587\">\n"+
            "      <nome>geladeira</nome>\n"+
            "      <preco>1000.0</preco>\n"+
            "      <descrição>geladeira duas portas</descrição>\n"+
            "    </produto>\n"+
            "    <produto codigo=\"1588\">\n"+
            "      <nome>ferro de passar</nome>\n"+
            "      <preco>100.0</preco>\n"+
            "      <descrição>ferro com vaporizador</descrição>\n"+
            "    </produto>\n"+
            "  </produtos>\n"+
            "</compra>";
        
        Compra compra = compraComGeladeiraEFerro();
        
        XStream xstream = xstreamParaCompraEProduto();
        
        String xmlGerado = xstream.toXML(compra);

        assertEquals(resultadoEsperado, xmlGerado);
    }

    @Test
    public void deveDeserializarCompra() {
        String xmlOrigem = "<compra>\n"+
            "  <id>15</id>\n"+
            "  <produtos>\n"+
            "    <produto codigo=\"1587\">\n"+
            "      <nome>geladeira</nome>\n"+
            "      <preco>1000.0</preco>\n"+
            "      <descrição>geladeira duas portas</descrição>\n"+
            "    </produto>\n"+
            "    <produto codigo=\"1588\">\n"+
            "      <nome>ferro de passar</nome>\n"+
            "      <preco>100.0</preco>\n"+
            "      <descrição>ferro com vaporizador</descrição>\n"+
            "    </produto>\n"+
            "  </produtos>\n"+
            "</compra>";
        
        XStream xstream = xstreamParaCompraEProduto();
        Compra compraEsperada = compraComGeladeiraEFerro();
        
        Compra compraDeserializada = (Compra) xstream.fromXML(xmlOrigem);

        assertEquals(compraDeserializada, compraEsperada);
    }
    
    private XStream xstreamParaCompraEProduto() {
        XStream xstream = new XStream();
        xstream.alias("compra", Compra.class);
        xstream.alias("produto", Produto.class);
        xstream.aliasField("descrição", Produto.class, "descricao");
        xstream.useAttributeFor(Produto.class, "codigo");
        return xstream;
    }

    private Compra compraComGeladeiraEFerro() {
        Produto geladeira = geladeira();
        Produto ferro = ferro();
        List<Produto> produtos = new ArrayList<Produto>();
        produtos.add(geladeira);
        produtos.add(ferro);
        return new Compra(15, produtos);
    }

    private Produto ferro() {
        return new Produto("ferro de passar", 100, "ferro com vaporizador", 1588);
    }

    private Produto geladeira() {
        return new Produto("geladeira", 1000, "geladeira duas portas", 1587);
    }
    
    private Compra compraDuasGeladeirasIguais() {
        Produto geladeira = geladeira();
        List<Produto> produtos = new ArrayList<Produto>();
        produtos.add(geladeira);
        produtos.add(geladeira);
        return new Compra(15, produtos);
    }
    
    @Test
    public void deveSerializarDuasGeladeirasIguais() {
        String resultadoEsperado = "<compra>\n" 
                + "  <id>15</id>\n"
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

        Compra compra = compraDuasGeladeirasIguais();
        XStream xstream = xstreamParaCompraEProduto();
        xstream.setMode(XStream.NO_REFERENCES);
        String xmlGerado = xstream.toXML(compra);
        assertEquals(resultadoEsperado, xmlGerado);
    }
}