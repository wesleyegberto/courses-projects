package com.github.wesleyegberto.javaactivemq.sessions;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class ConsumerSessionClientRecover {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageConsumer consumer = session.createConsumer(fila);

		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message mensagem) {
				try {
					System.out.println("Session Client Recover - Sleeping");
					Thread.sleep(500);
					System.out.println("Listener: " + ((TextMessage) mensagem).getText());
					// Chamando recover() o MoM tenta reenviar a mensagem ao mesmo consumidor
					session.recover();
				} catch (InterruptedException | JMSException e) {
					System.out.println("\tErro =)");
				}
			}
		});
		
		try(Scanner sc = new Scanner(System.in)) {
			sc.nextLine();
		}

		session.close();

		connection.close();
		context.close();
	}
}
