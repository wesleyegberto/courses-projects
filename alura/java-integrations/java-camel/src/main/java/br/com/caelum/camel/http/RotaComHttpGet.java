package br.com.caelum.camel.http;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaComHttpGet {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// <component>:<endereco>[?opcoesFormatoQueyString]
				from("file:pedidos?noop=true&fileName=1_pedido.xml")
				
				// cria variáveis utilizando o XML
				.setHeader("pedidoId", xpath("/pedido/id/text()")) // acessar atraves de header
				.setProperty("clienteId", xpath("/pedido/pagamento/email-titular/text()")) // acessar atraves de property
				
				// Divida as mensagens utilizando apenas os itens
			    .split()
		        	.xpath("/pedido/itens/item")
				
	        	// cria variaveis usando o resultado do split
		        	
				// Filtra por XPath o resultado do split
				.filter()
					.xpath("/item/formato[text()='EBOOK']")
					
				.setProperty("ebookId", xpath("/item/livro/codigo/text()"))
				
				// Transforma o XML em JSON
				.marshal()
					.xmljson()
					
				// Log da Mensagem
				.log("${id} - ${body}")
			    
				.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
				
				// Adiciona query parameters
				.setHeader(Exchange.HTTP_QUERY, simple("clienteId=${property.clienteId}&pedidoId=${header.pedidoId}&ebookId=${property.ebookId}"))
				
				.to("http4://localhost:8080/webservices/ebook/item");
			}
		});
		// Inicia o processamento
		context.start();
		
		Thread.sleep(3000);
		
		context.stop();
	}
}
