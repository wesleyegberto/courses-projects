package com.github.wesleyegberto.javaactivemq.queue;

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

public class ConsumidorListener {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageConsumer consumer = session.createConsumer(fila);

		Message message = consumer.receive();
		System.out.println("Recebendo msg: "+ message);
		
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message mensagem) {
				if(mensagem instanceof TextMessage) {
					try {
						System.out.println("Listener got: " + ((TextMessage) mensagem).getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Listener got: " + mensagem);
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
