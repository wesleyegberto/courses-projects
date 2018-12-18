package com.github.wesleyegberto.alura.springmvc.model;

import java.math.BigDecimal;

public class DadosPagamento {

	private BigDecimal value;

	public DadosPagamento(BigDecimal valorTotalItens) {
		this.value = valorTotalItens;
	}

	public BigDecimal getValue() {
		return value;
	}
}
