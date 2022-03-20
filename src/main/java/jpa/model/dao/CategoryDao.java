package jpa.model.dao;

import jpa.model.entities.Category;

import javax.persistence.EntityManager;

public class CategoryDao {
    private EntityManager entityManager;

    public CategoryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Category findByName(String name) {
        String sql = "SELECT * FROM Category WHERE name = :name";
        return (Category) entityManager.createNativeQuery(sql, Category.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
