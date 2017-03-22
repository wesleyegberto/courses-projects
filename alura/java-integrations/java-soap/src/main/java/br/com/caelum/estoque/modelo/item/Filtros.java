package br.com.caelum.estoque.modelo.item;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Filtros {

	@XmlElement(name = "filtro")
	private List<Filtro> filtros;
	
	Filtros() {
	}

	public Filtros(List<Filtro> filtros) {
		this.filtros = filtros;
	}
	
	public List<Filtro> getLista() {
		return filtros;
	}
	
}
