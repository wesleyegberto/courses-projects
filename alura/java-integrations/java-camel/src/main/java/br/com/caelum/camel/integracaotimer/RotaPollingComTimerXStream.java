package br.com.caelum.camel.integracaotimer;

import java.text.SimpleDateFormat;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.thoughtworks.xstream.XStream;

import br.com.caelum.model.Negociacao;

public class RotaPollingComTimerXStream {
	public static void main(String[] args) throws Exception {
		// configuração do XML unmarsheller
		final XStream xstream = new XStream();
		xstream.alias("negociacao", Negociacao.class);
		
		// Configuramos o DS para utilizar na rota
		SimpleRegistry registro = new SimpleRegistry();
		registro.put("mysql", criaDataSource());
		
		CamelContext context = new DefaultCamelContext(registro);

		// Faz a integração com Web Service, transforma o XML em objetos Java
		// e armazena no MySQL
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				// componente timer
				from("timer://negociacoes?fixedRate=true&delay=1s&period=360s")
				// componente que o timer executará
				.to("http4://argentumws.caelum.com.br/negociacoes")
				
				// converte o response para não perder após a leitura do InputStream
				.convertBodyTo(String.class)
				
				// seta o unmarsheller que transformará o XML em objeto List<Negociacao>
				// precisamos da lib camel-xstream
				.unmarshal(new XStreamDataFormat(xstream))
				// cada item da lista será uma mensagem
				.split(body())
				
				// cria um processador para manipularmos a mensagem
				.process(new Processor() {
			        @Override
			        public void process(Exchange exchange) throws Exception {
			        	// extrai o objeto Negociacao criado pelo XStream
			            Negociacao negociacao = exchange.getIn().getBody(Negociacao.class);
			            // setamos as propriedades na mensagem para usarmos nas instruções SQL
			            exchange.setProperty("preco", negociacao.getPreco());
			            exchange.setProperty("quantidade", negociacao.getQuantidade());
			            String data = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(negociacao.getData().getTime());
			            exchange.setProperty("data", data);
			        }
				})
				// seta o body usando EL para completar a instrução de Insert
				.setBody(simple("insert into negociacao(preco, quantidade, data) values (${property.preco}, ${property.quantidade}, '${property.data}')"))
			    .log("${body}") // logando o comando esql
			    .delay(500) // esperando 500ms para deixar a execução mais fácil de entender
			    .to("jdbc:mysql"); // usando o componente jdbc que envia o SQL para mysql
			}
		});
		context.start();
		Thread.sleep(3000);
		context.stop();
	}
	
	// Tabela usada:
	// create table negociacao (id BIGINT NOT NULL AUTO_INCREMENT, preco DECIMAL(5,2), quantidade MEDIUMINT, data DATETIME, primary key (id))
	private static MysqlConnectionPoolDataSource criaDataSource() {
	    MysqlConnectionPoolDataSource mysqlDs = new MysqlConnectionPoolDataSource();
	    mysqlDs.setDatabaseName("integracaocamel");
	    mysqlDs.setServerName("localhost");
	    mysqlDs.setPort(3306);
	    mysqlDs.setUser("root");
	    mysqlDs.setPassword("1234abc@");
	    return mysqlDs;
	}
}
