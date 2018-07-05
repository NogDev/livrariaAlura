package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transactional
@Interceptor
public class GerenciadorDeTransacao implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject
	EntityManager em;
	
	@AroundInvoke
	public Object executaTX(InvocationContext context) throws Exception{
		
		// abre transacao
		em.getTransaction().begin();
		
		
		Object result = context.proceed();
		
		
		
		// commita a transacao
		em.getTransaction().commit();
		
		return result;
	}
}
