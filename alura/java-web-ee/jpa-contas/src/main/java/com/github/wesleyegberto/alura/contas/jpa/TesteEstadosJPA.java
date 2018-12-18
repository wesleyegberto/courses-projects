package com.github.wesleyegberto.alura.contas.jpa;

import javax.persistence.EntityManager;

import com.github.wesleyegberto.alura.contas.jpa.util.JPAUtil;
import com.github.wesleyegberto.alura.contas.model.Conta;

public class TesteEstadosJPA {

  public static void main(String[] args) {

		EntityManager manager = new JPAUtil().getEntityManager();

		manager.getTransaction().begin();

		// Testes do capitulo
		Conta conta = manager.find(Conta.class, 1);

        System.out.println(conta.getTitular());

        // Alterando o titular da conta
        conta.setTitular("Luiz Ferreira");

        System.out.println(conta.getTitular());


		manager.getTransaction().commit();

		manager.close();

	}
}