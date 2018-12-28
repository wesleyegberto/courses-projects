package com.github.wesleyegberto.javaactivemq.messages.objects;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;

import com.github.wesleyegberto.javaactivemq.messages.Item;

public class ProdutorObjetos {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination fila = (Destination) context.lookup("objetositens");
		MessageProducer producer = session.createProducer(fila);

		// serializa e envia
		ObjectMessage message = session.createObjectMessage(new Item(42, "Mochila"));
		producer.send(message);

		session.close();
		connection.close();
		context.close();
	}
}
