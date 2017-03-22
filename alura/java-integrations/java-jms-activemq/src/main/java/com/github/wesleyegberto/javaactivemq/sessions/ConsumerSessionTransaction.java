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

public class ConsumerSessionTransaction {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		// Sem o true é lançado exception por não ter criado sessão
		Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageConsumer consumer = session.createConsumer(fila);

		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message mensagem) {
				try {
					System.out.println("Session Transaction - Sleeping");
					Thread.sleep(10000);
					TextMessage textMsg = (TextMessage) mensagem;
					System.out.println("Listener: " + textMsg.getText());
					if(!textMsg.getText().contains("666")) {
						// Se não chamar Session.commit() a mensagem é devolvida à fila e
						// o mesmo consumer pode recebê-la novamente somente após se reconectar
						System.out.println("Commitando");
						session.commit();
					} else {
						// Dando rollback a mensagem volta à fila, mas pode ser enviado para
						// este mesmo consumer durante uma mesma conexão
						System.out.println("Rolling back");
						session.rollback();
					}
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
