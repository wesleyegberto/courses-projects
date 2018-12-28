package br.com.caelum.camel.tratamentoerros;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.processor.validation.SchemaValidationException;

public class RotaComTratamentoOnException {
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {

				onException(SchemaValidationException.class)
					.handled(true) // retira a mensagem da rota
					.maximumRedeliveries(3)
					.redeliveryDelay(2000)
					.onRedelivery(new Processor() {
			            @Override
			            public void process(Exchange exchange) throws Exception {
		                    int counter = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER);
		                    int max = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_MAX_COUNTER);
		                    System.out.println("Redelivery - " + counter + "/" + max );;
			            }
					})
					.to("file:erros");
				
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
