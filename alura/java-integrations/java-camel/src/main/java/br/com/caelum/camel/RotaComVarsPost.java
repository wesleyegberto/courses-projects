package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaComVarsPost {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// <component>:<endereco>[?opcoesFormatoQueyString]
				from("file:pedidos?noop=true&fileName=1_pedido.xml")
				
				// cria variáveis utilizando o XML
				.setHeader("pedidoId", xpath("")) // acessar atraves de header
				.setProperty("clienteId", xpath("")) // acessar atraves de property
				
				// Divida as mensagens utilizando apenas os itens
			    .split()
		        	.xpath("/pedido/itens/item")
				
	        	// cria variaveis usando o resultado do split
		        	
				// Filtra por XPath o resultado do split
				.filter()
					.xpath("/item/formato[text()='EBOOK']")
				
				// Transforma o XML em JSON
				.marshal()
					.xmljson()
					
				// Log da Mensagem
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
