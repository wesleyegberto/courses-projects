package br.com.caelum.estoque.modelo.ws;

import java.util.List;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.ResponseWrapper;

import br.com.caelum.estoque.modelo.item.Filtros;
import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;
import br.com.caelum.estoque.modelo.item.ItemValidador;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED) // Default
/**
 * Wrapper para usar o nome do método wrapper no body (sem ele precisaria de outra maneira para linkar ao método)
 * Sem o WRAPPED, os métodos não os mesmos parâmetros (tipos) pois ele não conseguiria descobrir qual chamar
 * Precisaria usar SOAPAction no Headers do POST
 */
public class EstoqueWS {
	private ItemDao dao = new ItemDao();

	@WebMethod(operationName = "CarregarItens")
	@ResponseWrapper(localName = "itens")
	@WebResult(name = "item")
	public List<Item> getItens(@WebParam(name = "filtros") Filtros filtros) {
		System.out.println("Chamando getItens()");
		return dao.todosItens();
	}

	@WebMethod(operationName = "CadastrarItem")
	@WebResult(name = "item")
	public Item cadastraItem(@WebParam(name = "token", header = true) TokenUsuario token,
								@WebParam(name = "item") Item item) throws AutorizacaoException {
		System.out.println("Cadastrando " + item);

		boolean valido = new TokenDao().ehValido(token);

		if (!valido) {
			throw new AutorizacaoException("Token invalido");
		}
		
		new ItemValidador(item).validate();

		this.dao.cadastrar(item);
		return item;
	}
	
	@WebMethod(operationName = "IniciarValidacaoEstoque")
	@Oneway
	public void iniciaValidacaoEstoque() {
		System.out.println("Iniciando validação do estoque");
	}
}