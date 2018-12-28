package br.com.caelum.livraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.livraria.modelo.Autor;

@Stateless
// @Interceptors({ LogInterceptador.class })
public class AutorDao {

	@PersistenceContext
	private EntityManager manager;

	@Resource
	private SessionContext ctx;
	
	@PostConstruct
	void aposCriacao() {
	    System.out.println("[INFO] AutorDao foi criado.");
	}
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void salva(Autor autor) {
		manager.persist(autor);
	}
	
	public List<Autor> todosAutores() {
		return manager.createQuery("select a from Autor a", Autor.class)
				.getResultList();
	}

	public Autor buscaPelaId(Integer autorId) {
		return this.manager.find(Autor.class, autorId);
	}
	
}
