package com.github.wesleyegberto.alura.springmvc.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.github.wesleyegberto.alura.springmvc.model.Usuario;
import com.github.wesleyegberto.alura.springmvc.model.UsuarioPrincipal;

@Repository
public class UsuarioDAO implements UserDetailsService {

	@PersistenceContext
	private EntityManager em;

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
				.setParameter("email", email);
		
		try {
			Usuario usuario = query.getSingleResult();
			return new UsuarioPrincipal(usuario);
		} catch(NoResultException ex) {
			throw new UsernameNotFoundException("Usuário e/ou senha inválidos", ex);
		}
	}
}
