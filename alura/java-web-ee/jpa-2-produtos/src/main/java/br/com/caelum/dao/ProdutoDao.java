package br.com.caelum.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.caelum.model.Loja;
import br.com.caelum.model.Produto;

@Repository
public class ProdutoDao {

	@PersistenceContext
	private EntityManager em;

	public List<Produto> getProdutos() {
		return em.createQuery("from Produto", Produto.class).getResultList();
	}

	public Produto getProduto(Integer id) {
		Produto produto = em.find(Produto.class, id);
		return produto;
	}

	public Produto getProdutoComCategoria(Integer id) {
        TypedQuery<Produto> query = em.createQuery("select distinct p from Produto p join fetch p.categorias where p.id = :id", Produto.class)
            .setHint("javax.persistence.loadgraph", em.getEntityGraph("produtoComCategorias"));
        query.setParameter("id", id);
        return query.getSingleResult();
	}
	
	// JPA 2.1
	public Produto getProdutoComCategoriaUsandoGrafo(Integer id) {
        TypedQuery<Produto> query = em.createQuery("select distinct p from Produto p where p.id = :id", Produto.class)
    		// Os atributos declarado no Graph serão tratados como EAGER (os que não foram declarados serão tratados da forma original)
    		// também temos o javax.persistence.fetchgraph, que trará apenas os atributos declarados no grafo 
            .setHint("javax.persistence.loadgraph", em.getEntityGraph("produtoComCategorias"));
        query.setParameter("id", id);
        return query.getSingleResult();
	}

	public List<Produto> getProdutos(String nome, Integer categoriaId, Integer lojaId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> root = query.from(Produto.class);

		List<Predicate> predicates = new ArrayList<>();
		// Predicate predicate = builder.conjunction();

		if (!nome.isEmpty()) {
			Path<String> nomePath = root.<String> get("nome");
			Predicate nomeIgual = builder.like(nomePath, "%" + nome + "%");
			predicates.add(nomeIgual);
			// predicate = builder.and(nomeIgual);
		}
		if (categoriaId != null) {
			Path<Integer> categoriaPath = root.join("categorias").<Integer> get("id");
			Predicate categoriaIgual = builder.equal(categoriaPath, categoriaId);
			predicates.add(categoriaIgual);
			// predicate = builder.and(predicate, categoriaIgual);
		}
		if (lojaId != null) {
			Path<Integer> lojaPath = root.<Loja> get("loja").<Integer> get("id");
			Predicate lojaIgual = builder.equal(lojaPath, lojaId);
			predicates.add(lojaIgual);
			// predicate = builder.and(predicate, lojaIgual);
		}

		query.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Produto> typedQuery = em.createQuery(query);
		
		// Solicita o cache do resultado (se tiver habilitado no Hibernate)
		// mas ainda assim o cache de queries armazenam somente os IDs
		typedQuery.setHint("org.hibernate.cacheable", "true");
		
		return typedQuery.getResultList();

	}

	public void insere(Produto produto) {
		if (produto.getId() == null)
			em.persist(produto);
		else
			em.merge(produto);
	}

}
