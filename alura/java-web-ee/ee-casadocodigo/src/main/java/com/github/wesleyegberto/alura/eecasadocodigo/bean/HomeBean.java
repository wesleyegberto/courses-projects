package com.github.wesleyegberto.alura.eecasadocodigo.bean;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.github.wesleyegberto.alura.eecasadocodigo.dao.LivroDao;
import com.github.wesleyegberto.alura.eecasadocodigo.model.Livro;

@Model
public class HomeBean {

	@Inject
	private LivroDao dao;

	public List<Livro> ultimosLancamentos() {
		return dao.ultimosLancamentos();
	}

	public List<Livro> demaisLivros() {
		return dao.demaisLivros();
	}

}