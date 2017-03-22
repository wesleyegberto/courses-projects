package com.github.wesleyegberto.alura.eecasadocodigo.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import com.github.wesleyegberto.alura.eecasadocodigo.dao.AutorDao;
import com.github.wesleyegberto.alura.eecasadocodigo.dao.LivroDao;
import com.github.wesleyegberto.alura.eecasadocodigo.model.Autor;
import com.github.wesleyegberto.alura.eecasadocodigo.model.Livro;
import com.github.wesleyegberto.alura.eecasadocodigo.util.FileSaver;

@Named
@RequestScoped
public class AdminLivrosBean {
	@Inject
	private FacesContext facesContext;

	@Inject
	private LivroDao dao;
	@Inject
	private AutorDao autorDao;

	private Livro livro = new Livro();
	private Part capaLivro;
	
	@PostConstruct
	public void init() {
		System.out.println("AdminLivrosBean - PostConstruct");
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Part getCapaLivro() {
		return capaLivro;
	}

	public void setCapaLivro(Part capaLivro) {
		this.capaLivro = capaLivro;
	}

	public List<Autor> getAutores() {
		return autorDao.getLista();
	}

	public String cadastra() {
		System.out.println("Salvando a imagem");
		
		FileSaver fileSaver = new FileSaver();
        livro.setCapaLivroPath(fileSaver.write(capaLivro, "livros"));

		System.out.println("Cadastrando " + livro);
		dao.salvar(livro);

        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        facesContext.addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));

        return "/livros/lista?faces-redirect=true";
	}
}
