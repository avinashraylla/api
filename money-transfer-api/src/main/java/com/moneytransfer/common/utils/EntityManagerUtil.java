package com.moneytransfer.common.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
    private static EntityManagerFactory entityManagerFactory;

    public static void initializeEntityManagerFactory(String persistenceUnit) throws Exception {
        synchronized (EntityManagerUtil.class) {
            if(entityManagerFactory==null) {
                entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
            }

        }
    }

    public static EntityManager getEntityManager() throws Exception {
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManagerFactory() throws Exception {
        if(entityManagerFactory!=null) {
            entityManagerFactory.close();
        }
    }

    public static void rollbackAndClose(EntityManager em) {
        if(em!=null && em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        close(em);
    }

    public static void close(EntityManager em) {
        if(em!=null && em.isOpen()) {
            em.close();
        }
    }
}
