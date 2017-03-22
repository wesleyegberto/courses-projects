package com.github.wesleyegberto.alura.eecasadocodigo.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.github.wesleyegberto.alura.eecasadocodigo.dao.LivroDao;
import com.github.wesleyegberto.alura.eecasadocodigo.model.Livro;

@Model
public class AdminListaLivrosBean {

	@Inject
	private LivroDao dao;

	private List<Livro> livros = new ArrayList<>();

	public List<Livro> getLivros() {
		this.livros = dao.listar();

		return livros;
	}

}