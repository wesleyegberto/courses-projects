package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaComXsltParaSoap {
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// <component>:<endereco>[?opcoesFormatoQueyString]
				from("file:pedidos?noop=true&fileName=1_pedido.xml")
				
				// xslt:<arquivo_resources>
				.to("xslt:pedido-para-soap.xslt")
					.log("Resultado do Template: ${body}")
			    // tendo o XML no body basta setar o content-type e fetuar POST
				.setHeader(Exchange.CONTENT_TYPE, constant("text/xml"))
			    .to("http4://localhost:8080/webservices/financeiro");
			}
		});
		// Inicia o processamento
		context.start();
		Thread.sleep(3000);
		context.stop();
	}
}
