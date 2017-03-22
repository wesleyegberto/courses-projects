package com.github.wesleyegberto.javaactivemq.queue;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.naming.InitialContext;

public class ConsumidorBrowser {
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");

		QueueBrowser browser = session.createBrowser((Queue) fila);
		
		Enumeration<?> queueEnumeration = browser.getEnumeration();
		if(queueEnumeration.hasMoreElements()) {
			System.out.println("Recebendo msg: " + queueEnumeration.nextElement());
		} else {
			System.out.println("A fila ta vazia");
		}

		session.close();
		connection.close();
		context.close();
	}
}
