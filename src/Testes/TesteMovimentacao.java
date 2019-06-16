package Testes;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

public class TesteMovimentacao {

	public static void main(String[] args) {

	    Conta conta = new Conta();
	    
	    conta.setAgencia("0102");
	    conta.setNumeroConta("1234");
	    conta.setTitular("Paulo");

	    Movimentacao movimentacao = new Movimentacao();
	    
	    movimentacao.setData(Calendar.getInstance());
	    movimentacao.setDescricao("Pagamento");
	    movimentacao.setTipo(TipoMovimentacao.SAIDA);
	    movimentacao.setValor(new BigDecimal("200.0"));

	    movimentacao.setConta(conta);

	    EntityManager em = new JPAUtil().getEntityManager();
	    
	    em.getTransaction().begin();

	    em.persist(conta);
	    em.persist(movimentacao);;

	    em.getTransaction().commit();
	    em.close();
	}
	
	
}
