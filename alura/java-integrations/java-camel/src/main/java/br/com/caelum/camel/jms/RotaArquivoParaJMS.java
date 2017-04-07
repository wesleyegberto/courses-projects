package br.com.caelum.camel.jms;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaArquivoParaJMS {
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		// configurando e setando o broker ActiveMQ
		context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:pedidos?noop=true")
				// log do nome do arquivo processado
				.log("Enviando arquivo")
				// envia para a queue de pedidos
				.to("activemq:queue:pedidos");
			}
		});
		
		context.start();
		Thread.sleep(3000);
		context.stop();
	}
}
