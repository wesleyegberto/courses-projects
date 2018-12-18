package com.github.wesleyegberto.alura.contas.jpa.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("contas-mysql");

	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

}