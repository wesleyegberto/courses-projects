package com.github.wesleyegberto.javaactivemq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class Producer {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination fila = (Destination) context.lookup("financeiro");
		MessageProducer producer = session.createProducer(fila);

		Message message = session.createTextMessage("<pedido><id>666</id></pedido>");
		
		// Default (Persistível, Prioridade 4, Não expira)
		producer.send(message, DeliveryMode.PERSISTENT, 4, 0);

		session.close();
		connection.close();
		context.close();
	}
}
