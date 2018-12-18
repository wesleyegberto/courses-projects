package com.github.wesleyegberto.javaactivemq.messages;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Serializable {
	private static final long serialVersionUID = 3987404195123348343L;

	private int id;
	private String nome;

	public Item() {
	}
	
	public Item(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", nome=" + nome + "]";
	}

}
