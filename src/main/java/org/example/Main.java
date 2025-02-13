package org.example;

import java.sql.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Teller teller = new Teller(bank);
        UUID account = teller.createAccount(100);
        String url = "jdbc:h2:./test";
//        try {
//            Connection connection = DriverManager.getConnection(url);
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(
//                    "CREATE TABLE accounts (" +
//                            "id int PRIMARY KEY," +
//                            "balance double NOT NULL );");
//            statement.executeUpdate(
//                    "INSERT INTO accounts (id, balance) VALUES " +
//                            "(3, 24.0), (5, 26.2);");
//            PreparedStatement ps = connection.prepareStatement(
//                    "INSERT INTO accounts (id, balance) VALUES (?, ?);");
//            ps.setInt(1, 10);
//            ps.setDouble(2, 124.3);
//            ps.executeUpdate();
//            ResultSet rs = statement.executeQuery("SELECT * FROM accounts;");
//            List<Account> accounts = new ArrayList<>();
//            int size = rs.getFetchSize();
//            for (int i = 1; i <= size; i = i + 2) {
//              int id = rs.getInt(i);
//              double balance = rs.getDouble(i + 1);
//              Account acc = new Account();
//              acc.id = id;
//              acc.balance = balance;
//            }
//            connection.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


//        try {
//            statement.executeUpdate(
//                    "CREATE TABLE accounts (" +
//                            "    id int PRIMARY KEY, " +
//                            "    balance DOUBLE NOT NULL" +
//                            ");");
//            statement.executeUpdate("INSERT INTO accounts (id, balance) VALUES (1, 24.0), (3, 56.2);");
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts");
//
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO accounts (id, balance) VALUES (?, ?)");
//            preparedStatement.setInt(1, 4);
//            preparedStatement.setDouble(2, 45.6);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        // JPA below


        try (EntityManagerFactory managerFactory =
                     Persistence.createEntityManagerFactory("default")) {
            EntityManager em = managerFactory.createEntityManager();
            Account acc = new Account(100);
            AccessKey accKey = new AccessKey(acc, AccessType.OWNER);
            em.getTransaction().begin();
            em.persist(acc);
            em.persist(accKey);
            em.getTransaction().commit();
            EntityManager em2 = managerFactory.createEntityManager();
            Account acc2 = em2.find(Account.class, acc.getId());
            acc.equals(acc2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}