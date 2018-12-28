package com.github.wesleyegberto.javaactivemq.messages.objects;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;

public class ConsumidorObjetos {
	public static void main(String[] args) throws Exception {
		// ActiveMQ 5.12+ não permite qualquer classe ser desserializada (devido à um exploit do Java)
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("objetositens");

		MessageConsumer consumer = session.createConsumer(fila);
		
		System.out.println("Pedindo objeto...");
		Message message = consumer.receive();
		
		if(message instanceof ObjectMessage) {
			ObjectMessage objMsg = (ObjectMessage) message;
			System.out.println("Objeto recebido: " + objMsg.getObject());
		} else {
			System.out.println("Não é um ObjectMessage: "+ message);
		}
			
		session.close();
		connection.close();
		context.close();
	}
}