package com.github.wesleyegberto.alura.jsflivraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.wesleyegberto.alura.jsflivraria.modelo.Autor;
import com.github.wesleyegberto.alura.jsflivraria.util.Transacional;

@RequestScoped
public class AutorDao {
	@Inject
	private EntityManager em;
	private DAO<Autor> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Autor>(this.em, Autor.class);
	}

	public Autor buscaPorId(Integer autorId) {
		return this.dao.buscaPorId(autorId);
	}

	@Transacional
	public void adiciona(Autor autor) {
		this.dao.adiciona(autor);
	}

	@Transacional
	public void atualiza(Autor autor) {
		this.dao.atualiza(autor);
	}

	@Transacional
	public void remove(Autor autor) {
		this.dao.remove(autor);
	}

	public List<Autor> listaTodos() {
		return this.dao.listaTodos();
	}

}