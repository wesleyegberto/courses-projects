package reader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Efetua leitura de XML utilizando Document. 
 * Efetua leitura do documento em memória.
 */
public class LeitorNaMemoriaXml {
	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true); // utiliza o atributo do XML que define o XSD
		factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
		
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document document = builder.parse("src/venda.xml");
		String moeda = document.getDocumentElement().getAttribute("moeda");
		System.out.println(moeda);
		
		Element tagFormaDePagamento = (Element) document.getElementsByTagName("formaDePagamento").item(0);
		String formaDePagamento = tagFormaDePagamento.getTextContent();

		System.out.println(formaDePagamento);
		
		NodeList produtos = document.getElementsByTagName("produto");

		System.out.println("Produtos:");
		for(int i =0; i < produtos.getLength(); i++) {
		    Element produto = (Element) produtos.item(i);
		    String nome = produto.getElementsByTagName("nome").item(0).getTextContent();
		    String preco = produto.getElementsByTagName("preco").item(0).getTextContent();

		    System.out.println("Nome do produto: "+ nome);
		    System.out.println("Preco do produto: "+ preco);
		    System.out.println("-----------");
		}
	}
}
