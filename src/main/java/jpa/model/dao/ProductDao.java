package jpa.model.dao;


import jpa.model.entities.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductDao {
    private EntityManager entityManager;

    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Product product) {
        entityManager.persist(product);
    }

    public void delete(Product product) {
        entityManager.remove(convertToMerge(product));
    }

    private Product convertToMerge(Product product) {
        return entityManager.merge(product);
    }

    public Product findById(Long id) {
        return entityManager.find(Product.class, id);
    }

    public Product update(Product product) {
        return convertToMerge(product);
    }

    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public List<Product> findByName(String name) {
        return entityManager.createQuery("SELECT p FROM Product p WHERE p.name LIKE :name", Product.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}
