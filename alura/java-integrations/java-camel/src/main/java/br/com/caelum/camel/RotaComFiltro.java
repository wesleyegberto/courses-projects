package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaComFiltro {
	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// <component>:<endereco>[?opcoesFormatoQueyString]
				from("file:pedidos?noop=true&fileName=1_pedido.xml")
				// Divida as mensagens utilizando apenas os itens
			    .split()
		        	.xpath("/pedido/itens/item")
				
				// Filtra por XPath o resultado do split
				.filter()
					.xpath("/item/formato[text()='EBOOK']")

				// Log da Mensagem
				.log("${id} - ${body}")
				
				.to("file:pedidos_processados");
			}
		});
		// Inicia o processamento
		context.start();
		
		Thread.sleep(3000);
		
		context.stop();
	}
}
