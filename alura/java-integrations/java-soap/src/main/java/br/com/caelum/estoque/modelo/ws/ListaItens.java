package br.com.caelum.estoque.modelo.ws;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.caelum.estoque.modelo.item.Item;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaItens {
	@XmlElement(name = "item")
	private List<Item> itens;

	public ListaItens() {
	}
	
	public ListaItens(List<Item> itens) {
		this.itens = itens;
	}
}