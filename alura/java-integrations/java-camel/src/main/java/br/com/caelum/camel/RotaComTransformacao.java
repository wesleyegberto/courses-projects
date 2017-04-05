package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaComTransformacao {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// <component>:<endereco>[?opcoesFormatoQueyString]
				from("file:pedidos?noop=true&fileName=1_pedido.xml")

				// Log da Mensagem original
				.log("${id} - ${body}")
				
				// Transforma o XML em JSON
				.marshal()
					.xmljson()
					
				// Log da Mensagem transformada
				.log("${id} - ${body}")
				
				// altera o nome de destino usando o nome original sem extensão
			    .setHeader("CamelFileName", simple("${file:name.noext}-${header.CamelSplitIndex}.json"))
				.to("file:pedidos_processados");
			}
		});
		// Inicia o processamento
		context.start();
		
		Thread.sleep(3000);
		
		context.stop();
	}
}
