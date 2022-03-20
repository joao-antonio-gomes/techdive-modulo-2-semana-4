package jpa.model.dao;

import jpa.model.entities.Category;

import javax.persistence.EntityManager;
import java.util.List;

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

    public Category findById(Long id) {
        return entityManager.find(Category.class, id);
    }

    public void create(Category category) {
        entityManager.persist(category);
    }

    public void update(Category category) {
        convertToMerge(category);
    }

    private void convertToMerge(Category category) {
        entityManager.merge(category);
    }

    public void delete(Category categoryFound) {
        entityManager.remove(categoryFound);
    }

    public List<Category> findAll() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }
}
