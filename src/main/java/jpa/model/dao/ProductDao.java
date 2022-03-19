package jpa.model.dao;

import jpa.model.entities.Product;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

public class ProductDao {
    private EntityManager entityManager;

    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Product product) {
        entityManager.persist(product);
    }
}
