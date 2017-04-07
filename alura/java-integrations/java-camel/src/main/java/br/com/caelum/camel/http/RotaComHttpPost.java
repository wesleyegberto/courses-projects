package br.com.caelum.camel.http;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaComHttpPost {

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
				// Transforma o XML em JSON
				.marshal()
					.xmljson()
				.log("${id} - ${body}")
				// Padrão
				.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
				.to("http4://localhost:8080/webservices/ebook/item");
			}
		});
		// Inicia o processamento
		context.start();
		
		Thread.sleep(3000);
		
		context.stop();
	}
}
