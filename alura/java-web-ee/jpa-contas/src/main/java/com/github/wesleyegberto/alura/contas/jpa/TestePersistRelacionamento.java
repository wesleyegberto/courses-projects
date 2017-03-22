package com.github.wesleyegberto.alura.contas.jpa;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

import com.github.wesleyegberto.alura.contas.jpa.util.JPAUtil;
import com.github.wesleyegberto.alura.contas.model.Conta;
import com.github.wesleyegberto.alura.contas.model.Movimentacao;
import com.github.wesleyegberto.alura.contas.model.TipoMovimentacao;

public class TestePersistRelacionamento {

	public static void main(String[] args) {

		Conta conta = new Conta();
		// conta.setId(1);
		conta.setTitular("Ana Maria");
		conta.setBanco("Itau");
		conta.setNumero("54321");
		conta.setAgencia("111");

		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setData(Calendar.getInstance());
		movimentacao.setDescricao("Conta de Luz");
		movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao.setValor(new BigDecimal("123.9"));

		movimentacao.setConta(conta);

		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();

		// Precisamos persistir antes caso nao tenha sido criado
		manager.persist(conta);
		manager.persist(movimentacao);

		manager.getTransaction().commit();
		manager.close();

	}
}