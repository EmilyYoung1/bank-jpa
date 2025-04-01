package org.example;

import jakarta.persistence.EntityManager;

import java.util.List;

public class TestableBank extends Bank {
    AdminTeller getAdminTeller() {
        EntityManager em = managerFactory.createEntityManager();
        return new AdminTeller(em);
    }

    public static class AdminTeller extends Teller {
        public AdminTeller(EntityManager em) {
            super(em);
        }

        Long getNumberOfAccounts() {
            return em.createQuery("SELECT COUNT(acc) FROM Account acc", Long.class)
                    .getSingleResult();
        }

        Account makeRawAccount(int initialBalance) {
            Account account = new Account(initialBalance);
            em.getTransaction().begin();
            em.persist(account);
            em.getTransaction().commit();
            return account;
        }
    }
}
