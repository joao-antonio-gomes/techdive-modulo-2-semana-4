package jpa.services;

import jpa.model.dao.CategoryDao;
import jpa.model.entities.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;

public class CategoryService {
    private static final Logger LOG = LogManager.getLogger(ProductService.class);

    private EntityManager entityManager;

    private CategoryDao categoryDao;

    public CategoryService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.categoryDao = new CategoryDao(entityManager);
    }

    public Category findByName(String name) {
        if (name == null) {
            LOG.error("Nome da categoria não pode ser nulo");
            throw new IllegalArgumentException("Nome da categoria não pode ser nulo");
        }
        name = name.toLowerCase();
        return categoryDao.findByName(name);
    }
}
