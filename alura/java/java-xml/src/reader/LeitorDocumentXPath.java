package reader;
import java.util.List;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import model.Produto;

public class LeitorDocumentXPath {
	public static void main(String[] args) throws Exception {

		List<Produto> produtos = new ArrayList<Produto>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("src/venda.xml");

		XPath xPath = XPathFactory.newInstance().newXPath();

		String expression = "/venda/produtos/produto[2]";

		XPathExpression xPathExpression = xPath.compile(expression);
		NodeList lista = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);

		for (int i = 0; i < lista.getLength(); i++) {
			Element produto = (Element) lista.item(i);

			String nome = produto.getElementsByTagName("nome").item(0).getTextContent();
			Double preco = Double.parseDouble(produto.getElementsByTagName("preco").item(0).getTextContent());

			Produto prod = new Produto();
			prod.setNome(nome);
			prod.setPreco(preco);

			produtos.add(prod);
		}

		System.out.println(produtos);

	}
}
