package reader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import model.Produto;

/**
 * Efetua a leitura de XML utilizando SAX.
 * Efetua a leitura do documento por evento, ou seja,
 * a cada ação: Leu abertura de tag, fechamento, conteúdo, etc.
 */
public class LeitorStreamHandler extends DefaultHandler {
	
	public static void main(String[] args) throws Exception {
		XMLReader reader = XMLReaderFactory.createXMLReader();
		LeitorStreamHandler handler = new LeitorStreamHandler();
		reader.setContentHandler(handler);
		
		reader.parse(new InputSource(new FileInputStream("src/venda.xml")));
		
		handler.produtos.forEach(System.out::println);
	}

	List<Produto> produtos = new ArrayList<>();
	Produto produto;
	StringBuilder conteudo;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if("produto".equals(qName)) {
			produto = new Produto();
		}
		conteudo = new StringBuilder();
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		conteudo.append(new String(ch, start, length));
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if("produto".equals(qName)) {
			produtos.add(produto);
		} else if("nome".equals(qName)) {
			produto.setNome(conteudo.toString());
		} else if("preco".equals(qName)) {
			produto.setPreco(Double.parseDouble(conteudo.toString()));
		}
	}
}
