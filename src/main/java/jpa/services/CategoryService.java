package jpa.services;

import jpa.model.dao.CategoryDao;
import jpa.model.entities.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class CategoryService {
    private static final Logger LOG = LogManager.getLogger(ProductService.class);

    private EntityManager entityManager;

    private CategoryDao categoryDao;

    public CategoryService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.categoryDao = new CategoryDao(entityManager);
    }

    private void commitAndCloseTransaction() {
        LOG.info("Commitando transação");
        entityManager.getTransaction().commit();
        LOG.info("Fechando transação");
        entityManager.close();
    }

    private void getAndBeginTransaction() {
        LOG.info("Iniciando transação");
        entityManager.getTransaction().begin();
    }

    public Category findByName(String name) {
        if (name == null) {
            LOG.error("Nome da categoria não pode ser nulo");
            throw new IllegalArgumentException("Nome da categoria não pode ser nulo");
        }
        LOG.info("Buscando categoria pelo nome: " + name.toLowerCase());
        name = name.toLowerCase();
        return categoryDao.findByName(name);
    }

    public Category findById(Long id) {
        if (id == null) {
            LOG.error("Id da categoria não pode ser nulo");
            throw new IllegalArgumentException("Id da categoria não pode ser nulo");
        }
        Category category = categoryDao.findById(id);
        if (category == null) {
            LOG.error("Categoria não encontrada");
            throw new IllegalArgumentException("Categoria não encontrada");
        }
        LOG.info("Categoria encontrada");
        return category;
    }

    public void create(Category category) {
        if (category == null) {
            LOG.error("Categoria não pode ser nula");
            throw new IllegalArgumentException("Categoria não pode ser nula");
        }

        try {
            getAndBeginTransaction();
            categoryDao.create(category);
            commitAndCloseTransaction();
        } catch (Exception e) {
            LOG.error("Erro ao criar a categoria: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOG.info("Categoria criada com sucesso");
    }

    public void update(Category category, Long id) {
        if (category == null) {
            LOG.error("Categoria não pode ser nula");
            throw new IllegalArgumentException("Categoria não pode ser nula");
        }
        if (id == null) {
            LOG.error("Id da categoria não pode ser nulo");
            throw new IllegalArgumentException("Id da categoria não pode ser nulo");
        }
        Category categoryFound = this.findById(id);
        try {
            getAndBeginTransaction();
            categoryFound.setName(category.getName());
            categoryDao.update(categoryFound);
            commitAndCloseTransaction();
        } catch (Exception e) {
            LOG.error("Erro ao atualizar a categoria: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOG.info("Categoria atualizada com sucesso");
    }

    public void delete(Long id) {
        if (id == null) {
            LOG.error("Id da categoria não pode ser nulo");
            throw new IllegalArgumentException("Id da categoria não pode ser nulo");
        }

        Category categoryFound = findById(id);

        try {
            getAndBeginTransaction();
            categoryDao.delete(categoryFound);
            commitAndCloseTransaction();
        } catch (Exception e) {
            LOG.error("Erro ao deletar a categoria: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Category> findAll() {
        LOG.info("Buscando todas as categorias");
        List<Category> categories = null;
        try {
            categories = categoryDao.findAll();
        } catch (Exception e) {
            LOG.error("Erro ao buscar todas as categorias: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOG.info("Categorias encontradas");
        return categories;
    }
}
