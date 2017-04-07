package br.com.caelum.camel.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

public class JMSListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		System.out.println("Mensagem recebida: " + message);
	}

}
