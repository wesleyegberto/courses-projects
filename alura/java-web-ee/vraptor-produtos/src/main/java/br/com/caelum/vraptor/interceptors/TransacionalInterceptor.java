package br.com.caelum.vraptor.interceptors;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.annotations.Transacional;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;

@Intercepts(after = { AutorizadorInterceptor.class })
@AcceptsWithAnnotations(Transacional.class)
public class TransacionalInterceptor {

    private final EntityManager em;

    @Inject
    public TransacionalInterceptor(EntityManager em) {
        this.em = em;
    }

    TransacionalInterceptor() {
        this(null); // para uso do CDI
    }

    @AroundCall
    public void intercept(SimpleInterceptorStack stack) {
    	System.out.println("Iniciando transação");
        em.getTransaction().begin();
        stack.next();
        em.getTransaction().commit();
        System.out.println("Commitando transação");
    }
}