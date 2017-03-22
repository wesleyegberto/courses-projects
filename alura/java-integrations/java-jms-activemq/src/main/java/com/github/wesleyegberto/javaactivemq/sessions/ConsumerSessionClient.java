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

public class ConsumerSessionClient {
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
					System.out.println("Session Client - Sleeping");
					Thread.sleep(10);
					System.out.println("Listener: " + ((TextMessage) mensagem).getText());
					// Não chamando acknowledge() a mensagem é devolvida à fila e
					// o mesmo consumer pode recebê-la novamente somente após se reconectar
					mensagem.acknowledge();
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
