package com.github.wesleyegberto.alura.contas.jpa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.wesleyegberto.alura.contas.jpa.util.JPAUtil;
import com.github.wesleyegberto.alura.contas.model.Conta;
import com.github.wesleyegberto.alura.contas.model.TipoMovimentacao;

public class TestaJpqlAvancada {

    public static void main(String[] args) {
        EntityManager manager = new JPAUtil().getEntityManager();

        Conta conta = new Conta();
        conta.setId(3);

		calculaTotalGasto(manager, conta);
        
        calculaMediaGasta(manager, conta);
        
        consultaMaiorGasto(manager, conta);
        
        totalDeMovimentacoes(manager, conta);
        
        manager.close();
    }

	private static void consultaMaiorGasto(EntityManager manager, Conta conta) {
		String jpql = "select max(m.valor) from Movimentacao m where m.conta=:pConta "
                + "and m.tipoMovimentacao=:pTipo";
		TypedQuery<BigDecimal> query = manager.createQuery(jpql, BigDecimal.class);
        query.setParameter("pConta", conta);
        query.setParameter("pTipo", TipoMovimentacao.SAIDA);
        
        BigDecimal maiorGasto =  (BigDecimal) query.getSingleResult();
        System.out.println("O maior gasto da conta é " + maiorGasto );
	}

	private static void calculaMediaGasta(EntityManager manager, Conta conta) {
        TypedQuery<Double> queryAvg = manager.createNamedQuery("mediaDaContaPeloTipoMovimentacao", Double.class);
        queryAvg.setParameter("pConta", conta);
        queryAvg.setParameter("pTipo", TipoMovimentacao.SAIDA);

        Double resultadoAvg = queryAvg.getSingleResult();
        System.out.println("Média movimentada ..: R$ " + resultadoAvg);
	}

	private static void calculaTotalGasto(EntityManager manager, Conta conta) {
		String jpql = "select sum(m.valor) from Movimentacao m where m.conta=:pConta "
                + "and m.tipoMovimentacao=:pTipo";
        TypedQuery<BigDecimal> querySum = manager.createQuery(jpql, BigDecimal.class);
        querySum.setParameter("pConta", conta);
        querySum.setParameter("pTipo", TipoMovimentacao.SAIDA);

        BigDecimal resultadoSum = querySum.getSingleResult();
        System.out.println("Total movimentado ..: R$ " + resultadoSum);
	}
	
	private static void totalDeMovimentacoes(EntityManager manager, Conta conta) {
	    TypedQuery<Long> query = manager.createNamedQuery("totalDeMovimentacoes", Long.class);
	    query.setParameter("pConta", conta);
	    Long qtdeMovimentacoes = query.getSingleResult();
	    System.out.println("Quantidade de movimentações ..: " + qtdeMovimentacoes);
	}
}