package br.com.caelum.estoque.modelo.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(parameterStyle = ParameterStyle.BARE, style = Style.DOCUMENT, use = Use.LITERAL)
/**
 * Sem o SOAPAction receberemos um erro: Cannot find dispatch method
 * http://mangstacular.blogspot.com.br/2011/05/wsdl-soap-bindings-confusion-rpc-vs.html
 */
public class EstoqueRPC {
	
	@WebMethod(operationName = "PesquisarPorNome", action = "PesquisarPorNome")
	@WebResult(name = "resultado")
	public String pesquisaPorNome(@WebParam(name = "filtro") String filtroNome) { 
		return "Achou por nome";
	}
	
	@WebMethod(operationName = "PesquisarPorCategoria", action = "PesquisarPorCategoria")
	@WebResult(name = "resultado")
	public String pesquisaPorCategoria(@WebParam(name = "filtro") String filtroNome) { 
		return "Achou por categoria";
	}
}
