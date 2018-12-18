package com.github.wesleyegberto.alura.contas.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.github.wesleyegberto.alura.contas.model.Conta;

public class TestePersist {

	public static void main(String[] args) {
		Conta conta = new Conta();
		conta.setTitular("Maria dos Santos");
		conta.setBanco("Caixa");
		conta.setAgencia("043");
		conta.setNumero("54321");

		/**
		 * Usando HSQLDB
		 */

		// EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas-hsqldb");

		/**
		 * Usando MySQL
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas-mysql");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		em.persist(conta);

		em.getTransaction().commit();
		em.close();
	}
}
