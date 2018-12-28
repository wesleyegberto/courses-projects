package com.github.wesleyegberto.javaactivemq.sessions;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class ConsumerSessionAutoLostingMessage {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageConsumer consumer = session.createConsumer(fila);
		
		// Quando retornar o acknowledge já foi enviado
		// Então temos obrigação de processar sem erro pois não 
		// será gerado rollback
		Message messageAccepted = consumer.receive();
		
		if(messageAccepted != null) {
			throw new RuntimeException("Mensagem perdida: Foi confirmada antes de processar, mas ocorreu um erro e não temos como avisar que precisa ser reprocessada =/");
		}

		session.close();
		connection.close();
		context.close();
	}
}
