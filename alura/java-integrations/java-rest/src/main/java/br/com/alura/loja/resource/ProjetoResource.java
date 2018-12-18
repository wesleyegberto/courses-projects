package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

@Path("projetos")
public class ProjetoResource {

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) {
		Projeto projeto = new ProjetoDAO().busca(id);
		return projeto.toXml();
	}

	@Path("{id}/jaxb")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Projeto buscaJaxb(@PathParam("id") long id) {
		return new ProjetoDAO().busca(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo) {
		Projeto carrinho = (Projeto) new XStream().fromXML(conteudo);
		new ProjetoDAO().adiciona(carrinho);

		URI location = URI.create("/projetos/" + carrinho.getId());
		return Response.created(location).build();
	}

	@Path("{id}")
	@DELETE
	public Response removeProjeto(@PathParam("id") long id) {
		new ProjetoDAO().remove(id);
		return Response.ok().build();
	}
}