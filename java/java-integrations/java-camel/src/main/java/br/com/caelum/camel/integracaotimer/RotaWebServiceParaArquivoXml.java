package br.com.caelum.camel.integracaotimer;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaWebServiceParaArquivoXml {
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		// Faz a integração com um Web Service e salva o retorno do XML em disco
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// componente timer
				from("timer://negociacoes?fixedRate=true&delay=1s&period=360s")
				// componente que o timer executará
				.to("http4://argentumws.caelum.com.br/negociacoes")
				
				// converte o response para não perder após a leitura do InputStream
				.convertBodyTo(String.class)
				
				.log("${body}")
				
				// nome do arquivo de saída 
				.setHeader(Exchange.FILE_NAME, constant("negociacoes.xml"))
				// arquivo de destino
				.to("file:saida");
			}
		});
		context.start();
		Thread.sleep(3000);
		context.stop();
	}
}
