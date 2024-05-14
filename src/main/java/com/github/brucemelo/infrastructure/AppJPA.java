package com.github.brucemelo.infrastructure;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.tool.schema.Action;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

class AppJPA {

    private static final Logger logger = Logger.getLogger(AppJPA.class.getName());

    private static EntityManagerFactory entityManagerFactory;

    static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            try {
                hibernateLogsOff();
                var properties = new HashMap<>();
                properties.put(JdbcSettings.JAKARTA_JDBC_DRIVER, "org.postgresql.Driver");
                properties.put(JdbcSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/mydatabase");
                properties.put(JdbcSettings.JAKARTA_JDBC_USER, "sa");
                properties.put(JdbcSettings.JAKARTA_JDBC_PASSWORD, "sa");
                properties.put(Environment.SHOW_SQL, true);
                properties.put(Environment.FORMAT_SQL, true);
                properties.put(Environment.HIGHLIGHT_SQL, true);
                properties.put(Environment.HBM2DDL_AUTO, Action.ACTION_CREATE);
                entityManagerFactory = new HibernatePersistenceProvider()
                        .createContainerEntityManagerFactory(new AppPersistenceUnit(), properties);
            } catch (Throwable ex) {
                logger.log(Level.SEVERE, "Failed to create entity manager factory", ex);
            }
        }
        return entityManagerFactory;
    }

    private static void hibernateLogsOff() {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

}
