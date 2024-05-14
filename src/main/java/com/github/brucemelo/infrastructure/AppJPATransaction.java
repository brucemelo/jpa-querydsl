package com.github.brucemelo.infrastructure;

import jakarta.persistence.EntityManager;

import java.util.function.Consumer;
import java.util.function.Function;

public class AppJPATransaction {

    public static void inTransaction(Consumer<EntityManager> work) {
        var entityManager = AppJPA.getEntityManagerFactory().createEntityManager();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            work.accept(entityManager);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static <T> T inTransaction(Function<EntityManager, T> work) {
        var entityManager = AppJPA.getEntityManagerFactory().createEntityManager();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var applied = work.apply(entityManager);
            transaction.commit();
            return applied;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

}
