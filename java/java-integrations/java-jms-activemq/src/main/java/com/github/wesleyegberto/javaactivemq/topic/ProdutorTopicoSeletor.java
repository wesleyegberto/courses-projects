package com.github.wesleyegberto.javaactivemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class ProdutorTopicoSeletor {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topico = (Destination) context.lookup("loja");
        MessageProducer producer = session.createProducer(topico);

        Message message = session.createTextMessage("<pedido><id>222</id></pedido>");
        // Seta as propriedades no header
        message.setStringProperty("tipoProduto", "Fisico");
        message.setBooleanProperty("fisico", true);
        
        producer.send(message);

        session.close();
        connection.close();
        context.close();
    }
}