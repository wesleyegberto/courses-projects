package com.github.wesleyegberto.alura.springmvc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CarrinhoCompras implements Serializable {
	private static final long serialVersionUID = 7233795121475419470L;
	
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<>();

	private int getQuantidade(CarrinhoItem item) {
		return itens.getOrDefault(item, 0);
	}
	
	public void add(CarrinhoItem item) {
		itens.put(item, getQuantidade(item) + 1);
	}
	
	public int getQuantidadeTotalItens() {
		return itens.values().stream().reduce(0, Integer::sum);
	}
	
	public Collection<CarrinhoItem> getItens() {
		return Collections.unmodifiableSet(itens.keySet());
	}
	
	public int getQuantidadeDo(CarrinhoItem item) {
		return itens.getOrDefault(item, 0);
	}
	
	public BigDecimal getSubtotalDo(CarrinhoItem item) {
		return item.getValorUnit().multiply(BigDecimal.valueOf(getQuantidade(item)));
	}
	
	public BigDecimal getValorTotalItens() {
		return itens.entrySet().stream()
				.map(CarrinhoCompras::calculaSubtotalItem)
				.reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}
	
	private static BigDecimal calculaSubtotalItem(Entry<CarrinhoItem, Integer> item) {
		return item.getKey().getValorUnit().multiply(BigDecimal.valueOf(item.getValue()));
	}

	public void esvazia() {
		itens.clear();
	}

	public void remove(Long produtoId, TipoPreco tipoPreco) {
		Optional<CarrinhoItem> item = itens.keySet().stream()
				.filter(it -> ehMesmoItem(produtoId, tipoPreco, it))
				.findFirst();
		if(item.isPresent())
			itens.remove(item.get());
	}

	private static boolean ehMesmoItem(Long produtoId, TipoPreco tipoPreco, CarrinhoItem it) {
		return it.getProduto().getId() == produtoId && it.getTipoPreco().equals(tipoPreco);
	}
}