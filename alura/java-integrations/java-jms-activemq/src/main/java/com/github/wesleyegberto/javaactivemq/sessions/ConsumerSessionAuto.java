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

public class ConsumerSessionAuto {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageConsumer consumer = session.createConsumer(fila);

		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message mensagem) {
				if(mensagem instanceof TextMessage) {
					try {
						System.out.println("Session Auto - Sleeping");
						Thread.sleep(10000);
						System.out.println("Listener: " + ((TextMessage) mensagem).getText());
						// Quando lançado exceção, o MoM tenta reenviar a mensagem ao mesmo consumidor
						// throw new RuntimeException("Não quero processar");
						// Chamar o método Message.acknowledge() não causa nada
					} catch (InterruptedException | JMSException e) {
						System.out.println("\tErro =)");
					}
				} else {
					System.out.println("Listener: " + mensagem);
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
