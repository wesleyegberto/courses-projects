package com.github.wesleyegberto.alura.eecasadocodigo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull @Size(min = 5, max = 200)
	private String titulo;
	
	@Lob
	@NotNull @Size(min = 5)
	private String descricao;
	
	@Min(50)
	private Integer numeroPaginas;
	
	@DecimalMin("20")
	private BigDecimal preco;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataPublicacao = Calendar.getInstance();
	
	@ManyToMany
	@NotNull @Size(min = 1)
	private List<Autor> autores = new ArrayList<>();
	
	private String capaLivroPath;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(Integer numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public Calendar getDataPublicacao() {
		return dataPublicacao;
	}
	
	public void setDataPublicacao(Calendar dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public String getCapaLivroPath() {
		return capaLivroPath;
	}

	public void setCapaLivroPath(String capaLivroPath) {
		this.capaLivroPath = capaLivroPath;
	}

	@Override
	public String toString() {
		return "Livro [titulo=" + titulo + ", descricao=" + descricao + ", numeroPaginas=" + numeroPaginas + ", preco="
				+ preco + "]";
	}
}
