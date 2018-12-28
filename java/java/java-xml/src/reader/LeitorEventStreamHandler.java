package reader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;

import model.Produto;

/**
 * Leitura semelhante ao SAX, porém utiliza um lista de eventos ao invés de um
 * handler, o que permite avançar e retrocedor durante a leitura.
 */
public class LeitorEventStreamHandler {
	public static void main(String[] args) throws Exception {
		InputStream arquivo = new FileInputStream("src/venda.xml");
		XMLInputFactory factory = XMLInputFactory.newInstance();

		List<Produto> produtos = new ArrayList<>();
		XMLEventReader eventos = factory.createXMLEventReader(arquivo);
		
		while (eventos.hasNext()) {
			XMLEvent event = eventos.nextEvent();

			if (event.isStartElement() && "produto".equals(event.asStartElement().getName().getLocalPart())) {
				produtos .add(leProduto(eventos));
			}
		}
		
		produtos.forEach(System.out::println);
	}

	private static Produto leProduto(XMLEventReader eventos) throws Exception {
		Produto produto = new Produto();

		while (eventos.hasNext()) {
			XMLEvent event = eventos.nextEvent();
			if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("produto")) {
				break;
			}

			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("nome")) {
				event = eventos.nextEvent();
				String nome = event.asCharacters().getData();
				produto.setNome(nome);
			} else if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("preco")) {
				event = eventos.nextEvent();
				Double preco = Double.parseDouble(event.asCharacters().getData());
				produto.setPreco(preco);
			}
		}

		return produto;
	}
}
