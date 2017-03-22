package com.github.wesleyegberto.javaactivemq.queue;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;

public class QueueReceiverTest {

    public static void main(String[] args) throws Exception {
        InitialContext ctx = new InitialContext();
        
		QueueConnectionFactory cf = (QueueConnectionFactory)ctx.lookup("ConnectionFactory");
        QueueConnection connection = cf.createQueueConnection();
        connection.start();

        QueueSession sessao = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue fila = (Queue) ctx.lookup("financeiro");
        QueueReceiver receiver = (QueueReceiver) sessao.createReceiver(fila );

        // Recebe apenas uma mensagem, pode receber um timeout de blocking
        Message message = receiver.receive(5000);
		System.out.println("Recebendo msg: "+ message);	

        sessao.close();
        connection.close();    
        ctx.close();
    }
}