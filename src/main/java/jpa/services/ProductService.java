package jpa.services;

import jpa.model.dao.ProductDao;
import jpa.model.entities.Category;
import jpa.model.entities.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductService {
    private static final Logger LOG = LogManager.getLogger(ProductService.class);
    private EntityManager entityManager;
    private ProductDao productDao;
    private CategoryService categoryService;

    public ProductService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.productDao = new ProductDao(entityManager);
        this.categoryService = new CategoryService(entityManager);
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

    public void create(Product product) {
        if (product == null) {
            LOG.error("O Produto informado não pode ser nulo.");
            throw new IllegalArgumentException("Product cannot be null");
        }

        LOG.info("Buscando se existe categoria: " + product.getCategory().getName());
        Category category = this.categoryService.findByName(product.getCategory().getName());

        if (category != null) {
            product.setCategory(category);
        }

        try {
            getAndBeginTransaction();
            productDao.create(product);
            commitAndCloseTransaction();
        } catch (Exception e) {
            LOG.error("Erro ao criar o produto: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOG.info("Produto criado com sucesso");
    }

    public void delete(Long id) {
        if (id == null) {
            LOG.error("O id informado não pode ser nulo.");
            throw new IllegalArgumentException("Id cannot be null");
        }

        Product productFound = this.findById(id);

        try {
            getAndBeginTransaction();
            productDao.delete(productFound);
            commitAndCloseTransaction();
        } catch (Exception e) {
            LOG.error("Erro ao deletar o produto: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOG.info("Produto deletado com sucesso");
    }

    public Product findById(Long id) {
        if (id == null) {
            LOG.error("O id informado não pode ser nulo.");
            throw new IllegalArgumentException("Id cannot be null");
        }
        LOG.info("Buscando produto pelo id: " + id);
        Product product = null;
        try {
            product = productDao.findById(id);
        } catch (Exception e) {
            LOG.error("Erro ao buscar o produto: " + e.getMessage());
            throw new RuntimeException(e);
        }

        if (product == null) {
            LOG.error("Produto não encontrado.");
            throw new RuntimeException("Product not found");
        }

        LOG.info("Produto encontrado com sucesso");
        return product;
    }

    public void update(Product productNew, Long id) {
        if (productNew == null) {
            LOG.error("O Produto informado não pode ser nulo.");
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (id == null) {
            LOG.error("O id informado não pode ser nulo.");
            throw new IllegalArgumentException("Id cannot be null");
        }

        Product productFound = this.findById(id);

        LOG.info("Preparando para atualizar o produto");
        try {
            getAndBeginTransaction();
            productFound.setName(productNew.getName());
            productFound.setDescription(productNew.getDescription());
            productFound.setPrice(productNew.getPrice());
            Category category = categoryService.findByName(productNew.getCategory().getName());
            productFound.setCategory(category);
            productDao.update(productFound);
            commitAndCloseTransaction();
        } catch (Exception e) {
            LOG.error("Erro ao atualizar o produto: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOG.info("Produto atualizado com sucesso");
    }

    public List<Product> findAll() {
        LOG.info("Buscando todos os produtos");
        List<Product> products = null;
        try {
            products = productDao.findAll();
        } catch (Exception e) {
            LOG.error("Erro ao buscar todos os produtos: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOG.info("Produtos encontrados com sucesso");
        return products;
    }

    public List<Product> findByName(String name) {
        if (name == null) {
            LOG.error("O nome informado não pode ser nulo.");
            throw new IllegalArgumentException("Name cannot be null");
        }
        name = name.toLowerCase();
        LOG.info("Buscando produto pelo nome: " + name);
        List<Product> products = null;
        try {
            products = productDao.findByName(name);
        } catch (Exception e) {
            LOG.error("Erro ao buscar o produto: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOG.info("Produto encontrado com sucesso");
        return products;
    }
}
