package br.com.caelum.camel.tratamentoerros;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaComTratamentoErrorHandler {
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				
				// Configuração de erros
				errorHandler(
					// envia para pasta erros
					deadLetterChannel("file:erros")
					.useOriginalMessage() // salva a mensagem original
					.maximumRedeliveries(3)
					.redeliveryDelay(1000) // 2s entre as tentativas
					.onRedelivery(new Processor() {            
			            @Override
			            public void process(Exchange exchange) throws Exception {
			                int counter = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER);
			                int max = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_MAX_COUNTER);
			                System.out.println("Redelivery - " + counter + "/" + max );
			            }
			        })
					// efetuar log após as tentativas esgotarem
					.logExhaustedMessageHistory(true)
				);
				
				from("file:pedidos?noop=true&fileName=4_pedido.xml")
				// endpoint utilizando componente de validação com XSD
				.to("validator:pedido.xsd")
				// continua o processamento se estiver ok
				.to("mock:soap");
			}
		});
		// Inicia o processamento
		context.start();
		Thread.sleep(3000);
		context.stop();
	}
}
