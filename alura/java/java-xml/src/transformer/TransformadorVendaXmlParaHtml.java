package transformer;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class TransformadorVendaXmlParaHtml {
	public static void main(String[] args) throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();

		InputStream template = new FileInputStream("src/xmlParaHtml.xsl");
		StreamSource stylesheet = new StreamSource(template);
		Transformer transformer = factory.newTransformer(stylesheet);

		InputStream vendas = new FileInputStream("src/venda.xml");
		StreamSource source = new StreamSource(vendas);
		StreamResult result = new StreamResult("src/saida.html");
		
		transformer.transform(source, result);
		
		System.out.println("Transformação concluída");
	}
}
