package reader;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import model.Venda;

public class LeitorJaxB {
	public static void main(String[] args) throws Exception {
		JAXBContext contexto = JAXBContext.newInstance(Venda.class);
		Unmarshaller unmarshaller = contexto.createUnmarshaller();
		Venda venda = (Venda) unmarshaller.unmarshal(new File("src/venda.xml"));
		
		System.out.println(venda);
	}
}
