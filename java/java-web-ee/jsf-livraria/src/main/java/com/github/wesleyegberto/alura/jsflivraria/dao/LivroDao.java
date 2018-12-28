package com.github.wesleyegberto.alura.jsflivraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.wesleyegberto.alura.jsflivraria.modelo.Livro;
import com.github.wesleyegberto.alura.jsflivraria.util.Transacional;

@RequestScoped
public class LivroDao {
	@Inject
    private EntityManager em;

    private DAO<Livro> dao;

    @PostConstruct
    void init() {
        this.dao = new DAO<Livro>(this.em, Livro.class);
    }

    public Livro buscaPorId(Integer livroId) {
        return this.dao.buscaPorId(livroId);
    }

    @Transacional
    public void adiciona(Livro livro) {
        this.dao.adiciona(livro);
    }

    @Transacional
    public void atualiza(Livro livro) {
        this.dao.atualiza(livro);
    }

    @Transacional
    public void remove(Livro livro) {
        this.dao.remove(livro);
    }

    public List<Livro> listaTodos() {
        return this.dao.listaTodos();
    }

}