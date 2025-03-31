package org.example;

import jakarta.persistence.EntityManager;

import java.util.List;

public class TestableBank extends Bank {
    AdminTeller getAdminTeller() {
        EntityManager em = managerFactory.createEntityManager();
        return new AdminTeller(em);
    }

    public class AdminTeller extends Teller {
        public AdminTeller(EntityManager em) {
            super(em);
        }

        List<Account> getAllAccounts() {
//            TODO: query to get all accounts
            return null;
        }
    }
}
