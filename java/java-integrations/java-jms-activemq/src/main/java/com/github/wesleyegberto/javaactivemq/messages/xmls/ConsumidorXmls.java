package com.github.wesleyegberto.javaactivemq.messages.xmls;
import java.io.StringReader;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;

import com.github.wesleyegberto.javaactivemq.messages.Item;

public class ConsumidorXmls {
	public static void main(String[] args) throws Exception {
		// ActiveMQ 5.12+ não permite qualquer classe ser desserializada (devido à um exploit do Java)
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("xmlsitens");

		MessageConsumer consumer = session.createConsumer(fila);
		
		System.out.println("Pedindo XML...");
		Message message = consumer.receive();
		
		if(message instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) message;
			System.out.println("XML recebido: " + txtMsg.getText());
			Item item = JAXB.unmarshal(new StringReader(txtMsg.getText()), Item.class);
			System.out.println("Objeto umarshalled: " + item);
		} else {
			System.out.println("Não é um TextMessage: "+ message);
		}
			
		session.close();
		connection.close();
		context.close();
	}
}