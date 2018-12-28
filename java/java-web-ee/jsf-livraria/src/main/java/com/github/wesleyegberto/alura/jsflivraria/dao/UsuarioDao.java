package com.github.wesleyegberto.alura.jsflivraria.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.github.wesleyegberto.alura.jsflivraria.modelo.Usuario;

@RequestScoped
public class UsuarioDao {

	@Inject
	private EntityManager em;

	public boolean existe(Usuario usuario) {
		TypedQuery<Usuario> query = em.createQuery(
				  " select u from Usuario u "
				+ " where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
		
		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		try {
			return query.getSingleResult() != null;
		} catch (NoResultException ex) {
			return false;
		}
	}

}
