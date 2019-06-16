package Testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteConta {

	public static void main(String[] args) {
		
		Conta conta = new Conta();
		
		conta.setTitular("Paulo");
		conta.setAgencia("123");
		conta.setNumeroConta("54321");
		
		EntityManagerFactory emf =  Persistence.createEntityManagerFactory("SistemaBancario");
		EntityManager em = emf.createEntityManager();


		em.getTransaction().begin();
		em.persist(conta);
		em.getTransaction().commit();

		em.close();
		emf.close();
	}
	
}
