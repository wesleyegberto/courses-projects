package com.github.wesleyegberto.javaactivemq.messages.xmls;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;

import com.github.wesleyegberto.javaactivemq.messages.Item;

public class ProdutorXmls {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination fila = (Destination) context.lookup("xmlsitens");
		MessageProducer producer = session.createProducer(fila);

		StringWriter xml = new StringWriter();
		JAXB.marshal(new Item(42, "Mochila"), xml);
		TextMessage message = session.createTextMessage(xml.toString());
		producer.send(message);

		session.close();
		connection.close();
		context.close();
	}
}
