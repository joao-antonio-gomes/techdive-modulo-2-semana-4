import jpa.model.factory.JpaConnectionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ProgramApp {
    public static void main(String[] args) {
        EntityManager entityManager = new JpaConnectionFactory().getEntityManager();
    }
}
