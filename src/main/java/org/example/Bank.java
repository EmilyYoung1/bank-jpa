package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Bank implements AutoCloseable {
    protected final EntityManagerFactory managerFactory;

    public Bank() {
        managerFactory = Persistence.createEntityManagerFactory("default");
    }

    public Teller getTeller() {
        EntityManager em = managerFactory.createEntityManager();
        return new Teller(em);
    }

    public void close() {
        managerFactory.close();
    }
}