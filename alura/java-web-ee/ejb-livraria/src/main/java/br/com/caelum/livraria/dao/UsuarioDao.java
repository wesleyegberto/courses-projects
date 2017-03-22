package br.com.caelum.livraria.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

@Stateless
public class UsuarioDao {
	@PersistenceContext
	private EntityManager manager;
	
	public Usuario buscaPeloLogin(String login) {
		TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u where u.login = :login", Usuario.class);
		query.setParameter("login", login);
		try {
			return query.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}
	
}
