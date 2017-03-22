package br.com.caelum.vraptor.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.annotations.Transacional;
import br.com.caelum.vraptor.dao.ProdutoDao;
import br.com.caelum.vraptor.model.Produto;
import br.com.caelum.vraptor.simplemail.Mailer;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;

@Controller
public class ProdutoController {

	private final ProdutoDao dao;
	private final Result result;
	private final Validator validator;
	private final Mailer mailer;
	
	public ProdutoController() {
		this(null, null, null, null);
	}

	@Inject
    public ProdutoController(ProdutoDao dao, Result result, Validator validator, Mailer mailer) {
		this.dao = dao;
		this.result = result;
		this.validator = validator;
		this.mailer = mailer;
	}

	@Get("/")
    public void inicio() {
    }
    
    // @Path("/produto/lista")
    @Get
    public List<Produto> lista() {
        // result.include("produtoList", dao.lista());
        return dao.lista();
    }

    @Get("/produto/lista/json")
    public void listaJson() {
        result.use(Results.json())
        	.from(dao.lista()).exclude("quantidade")
        	.serialize();
    }
    
    @Get("/produto/lista/xml")
    public void listaXml() {
        result.use(Results.xml()).from(dao.lista()).serialize();
    }
    
    @Get
    public void formulario() {

    }
    
    // @Path("/produto/adiciona") @Post
    @Post
    @Transacional
    public void adiciona(@Valid Produto produto) { 
    	// se for false então a mensagem (setada ou do arquivo properties) é adiciona na lista de erros
    	// validator.check(!isNullOrEmpty(produto.getNome()), new SimpleMessage("erro", "Produto inválido"));
    	
    	validator.check(!isNullOrEmpty(produto.getNome()), new I18nMessage("erro", "string.empty", "Nome"));
    	
    	// Se ocorrer erro redireciona para o formulário e o VRaptor disponibiliza um atributo errors
    	// usando o Bean Validation ainda temos que setar o onErrorUsePageOf
    	validator.onErrorUsePageOf(this).formulario();
    	
        dao.adiciona(produto);
        result.include("mensagem", "Produto adicionado com sucesso.");
        result.redirectTo(this).lista();
    }

	@Get
	@Transacional
    public void remove(Produto produto) {
    	// se for false então a mensagem é adicionada na lista de erros
    	validator.check(produto != null && produto.getId() > 0, new SimpleMessage("erro", "Produto inválido"));
    	
    	// Se ocorrer erro redireciona para o formulário e o VRaptor disponibiliza um atributo errors
    	validator.onErrorUsePageOf(this).formulario();
    	
    	dao.remove(produto);
        result.include("mensagem", "Produto removido com sucesso.");
    	result.redirectTo(this).lista();
    }
	
	@Get
	public void enviaPedidoEstoque(Produto produto) {
	    try {
			Email email = new SimpleEmail();
		    email.setSubject("[VRaptor-Produtos] Solicitação de Estoque");
		    email.addTo("wesleyegberto@gmail.com");
		    email.setMsg("Precisamos de mais itens do produto" + produto.getNome());
		    
		    mailer.send(email);
	        result.include("mensagem", "E-mail enviado com sucesso.");
	    } catch(EmailException ex) {
	    	result.include("mensagem", "Ocorreu um erro ao enviar o e-mail: " + ex.getMessage());
	    }
	    result.redirectTo(this).lista();
	}

    @Get
    public void sobre() {
    	
    }

    private static boolean isNullOrEmpty(String nome) {
		return nome == null || nome.trim().length() == 0;
	}
}