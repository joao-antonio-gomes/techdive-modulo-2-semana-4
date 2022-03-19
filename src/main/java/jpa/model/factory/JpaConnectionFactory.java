package jpa.model.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaConnectionFactory {
    private final EntityManager entityManager;

    public JpaConnectionFactory() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("market");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
