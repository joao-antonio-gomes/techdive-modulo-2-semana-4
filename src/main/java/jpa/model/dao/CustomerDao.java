package jpa.model.dao;

import javax.persistence.EntityManager;

public class CustomerDao {
    private EntityManager entityManager;

    public CustomerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
