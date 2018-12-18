package com.github.wesleyegberto.alura.springmvc.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.wesleyegberto.alura.springmvc.model.Produto;
import com.github.wesleyegberto.alura.springmvc.model.TipoPreco;

@Repository
@Transactional
public class ProdutoDAO {

	@PersistenceContext
	private EntityManager em;
	
	public void salva(Produto produto) {
		em.persist(produto);
	}

	public List<Produto> lista() {
		return em.createQuery("select p from Produto p", Produto.class)
				.getResultList();
	}

	public Produto getPorId(long id) {
		return em.createQuery("select distinct(p) from Produto p join fetch p.precos where p.id = :id", Produto.class)
					.setParameter("id", id).getSingleResult();
	}
	
	public BigDecimal somaPrecosPorTipo(TipoPreco tipoPreco){
	    TypedQuery<BigDecimal> query = em.createQuery("select sum(preco.preco) from Produto p join p.precos preco"
	    												+ " where preco.tipo = :tipoPreco", BigDecimal.class);
	    query.setParameter("tipoPreco", tipoPreco);
	    return query.getSingleResult();
	}
}
