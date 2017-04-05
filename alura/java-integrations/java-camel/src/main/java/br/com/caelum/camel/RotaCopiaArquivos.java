package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaCopiaArquivos {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// <component>:<endereco>[?opcoesFormatoQueyString]
				from("file:pedidos?noop=true") // opcao noop para nao apagar os arquivos
				
				// Log do Exchange Pattern em uso
				.log("${exchange.pattern}")
				
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
