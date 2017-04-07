package br.com.caelum.camel.jms;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaConsumindoJMS {
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();

		// configurando e setando o broker ActiveMQ
		context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
		
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				// configura o handler para enviar para uma DLQ (Dead Letter Queue)
				errorHandler(
					deadLetterChannel("activemq:queue:pedidos.DLQ")
					.logExhaustedMessageHistory(true)
					.maximumRedeliveries(3)
					.redeliveryDelay(500)
					.onRedelivery(new Processor() {
				        @Override
				        public void process(Exchange exchange) throws Exception {
				            int counter = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER);
				            int max = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_MAX_COUNTER);
				            System.out.println("Redelivery - " + counter + "/" + max );
	                	}
			        })
				);
				
				// consome a queue pedidos do JMS
				from("activemq:queue:pedidos")
					.log("Pattern: ${exchange.pattern}")
					.bean(JMSListener.class)
					.log("${body}")
				.to("validator:pedido.xsd")
				.to("file:pedidos_jms");
			}
		});
		
		context.start();
		Thread.sleep(3000);
		context.stop();
	}
}
