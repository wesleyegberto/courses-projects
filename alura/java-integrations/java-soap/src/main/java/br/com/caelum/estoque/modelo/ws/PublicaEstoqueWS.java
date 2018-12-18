package br.com.caelum.estoque.modelo.ws;

import javax.xml.ws.Endpoint;

public class PublicaEstoqueWS {

	public static void main(String[] args) {
		String URL = "http://localhost:8080/estoquews";
		Endpoint.publish(URL, new EstoqueWS());
		System.out.println("EstoqueWS rodando: " + URL);

		String URL_RPC = "http://localhost:8080/estoquerpc";
		Endpoint.publish(URL_RPC, new EstoqueRPC());
		System.out.println("EstoqueWS rodando: " + URL_RPC);
	}

}