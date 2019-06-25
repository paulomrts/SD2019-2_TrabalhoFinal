package sistemaBancarioDistribuido;

import javax.persistence.EntityManager;

public class TesteBuscaConta {

    public static void main(String[] args) {

        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        Conta conta = em.find(Conta);

        em.getTransaction().commit();
        em.close();
    }

}
