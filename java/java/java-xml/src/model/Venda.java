package model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Venda {

	private String formaDePagamento;

	@XmlElementWrapper(name = "produtos")
	@XmlElement(name = "produto")
	private List<Produto> produtos;

	public void setFormaDePagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public String getFormaDePagamento() {
		return formaDePagamento;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	@Override
	public String toString() {
		return "Forma de pagamento: " + formaDePagamento + "\nProdutos: \n " + produtos;
	}
}