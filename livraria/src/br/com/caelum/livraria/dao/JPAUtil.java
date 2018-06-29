package br.com.caelum.livraria.dao;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("livraria");

	@Produces //metodo produtor de EntityManager
	@RequestScoped
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	
	public void close(@Disposes EntityManager em) { //@Disposes informa que esse metodo será chamado ao final do escopo
		em.close();
	}
}
