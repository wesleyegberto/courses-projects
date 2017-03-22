package com.github.wesleyegberto.alura.eecasadocodigo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.github.wesleyegberto.alura.eecasadocodigo.model.Livro;

public class LivroDao {

	@PersistenceContext
	// Precisar se EJB Stateful
    // @PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager manager;

	@Transactional
	public void salvar(Livro livro) {
		manager.persist(livro);
	}

	public List<Livro> listar() {
		TypedQuery<Livro> query = manager.createQuery("select distinct(l) from Livro l join fetch l.autores", Livro.class);
		return query.getResultList();
	}

	public List<Livro> ultimosLancamentos() {
	    String jpql = "select l from Livro l order by l.id desc";
	    return manager.createQuery(jpql, Livro.class)
	            .setMaxResults(5)
	            .getResultList();
	}
	
	public List<Livro> demaisLivros() {
        String jpql = "select l from Livro l order by l.id desc";
        return manager.createQuery(jpql, Livro.class)
                .setFirstResult(5)
                .getResultList();
    }
}