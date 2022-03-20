import jpa.connection.JpaConnectionFactory;
import jpa.model.entities.Category;
import jpa.model.entities.Product;
import jpa.services.CategoryService;
import jpa.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProgramApp {
    private static final Logger LOG = LogManager.getLogger(ProgramApp.class);

    public static void main(String[] args) {
        LOG.info("Programa iniciou!");
        EntityManager entityManager = new JpaConnectionFactory().getEntityManager();
        ProductService productService = new ProductService(entityManager);
        CategoryService categoryService = new CategoryService(entityManager);

//        Product product = new Product("Notebook", "DELL", new BigDecimal("1700.00"), new Category("Informática"));
//        Product product = new Product("Notebook", "VAIO", new BigDecimal("2500.00"), new Category("Informática"));
//        productService.create(product);
//        productService.delete(2L);
//        productService.update(product, 3L);
//        List<Product> products = productService.findAll();
//        List<Product> products = productService.findByName("Notebook");
//        products.forEach(product -> LOG.info(product.toString()));
//        Category category = new Category("Alimentos");
//        categoryService.create(category);
//        categoryService.delete(6L);
//        categoryService.findAll().forEach(category -> LOG.info(category.toString()));
//        Category category = categoryService.findByName("Informática");
//        System.out.println(category);
        categoryService.update(new Category("alimentos"), 7L);
    }
}
