package com.github.wesleyegberto.alura.contas.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.github.wesleyegberto.alura.contas.jpa.util.JPAUtil;
import com.github.wesleyegberto.alura.contas.model.Conta;

public class TesteMovimentacaoConta {

    public static void main(String[] args) {
        EntityManager manager = new JPAUtil().getEntityManager();

        Conta conta = manager.find(Conta.class, 1); //id deve existir
        
        // Se fechar aqui ocorrerá um LazyInitializationException
        // manager.close();
        System.out.println(conta.getMovimentacoes().size());
        
        Query query = manager.createQuery("select c from Conta c join fetch c.movimentacoes where c.id = :pId");
        query.setParameter("pId", 2);
        conta = (Conta) query.getSingleResult();

        // Não ocorre a exception devido ao JOIN FETCH no JPQL
        manager.close();

        System.out.println(conta.getMovimentacoes().size());
    }
}