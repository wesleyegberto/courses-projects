package com.github.wesleyegberto.alura.contas.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.wesleyegberto.alura.contas.jpa.util.JPAUtil;
import com.github.wesleyegberto.alura.contas.model.Conta;
import com.github.wesleyegberto.alura.contas.model.Movimentacao;

public class TestaConsulta {

	public static void main(String[] args) {
		EntityManager manager = new JPAUtil().getEntityManager();

		Conta conta = new Conta();
		conta.setId(2);

		TypedQuery<Movimentacao> query = manager.createQuery("select m from Movimentacao m where m.conta=:conta", Movimentacao.class);
		query.setParameter("conta", conta);

		List<Movimentacao> movimentacoes = query.getResultList();

		for (Movimentacao m : movimentacoes) {
			System.out.println("\nDescricao ..: " + m.getDescricao());
			System.out.println("Valor ......: R$ " + m.getValor());
		}
	}
}