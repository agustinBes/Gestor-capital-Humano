package com.practical.consultora_rrhh.configuracion;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Clase de configuracion que intercepta la conexión de spring con el archivo de propiedades
 * con el proposito de crear la BDD si aun no existe, para que flyway puede aplicar las migraciones sin problemas
 * @author  Besigniano Agustin
 * @since 1.0
 */
@Configuration
public class DatabaseInitializer {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${app.db.name}")
    private String dbName;
@Value("${app.db.port}")
    private String port;
    @Bean
    public DataSource dataSource() {
        // PASO 1: Nos conectamos temporalmente a la base de datos por defecto 'postgres'
        String urlMaster = "jdbc:postgresql://localhost:"+port+"/postgres";

        try (Connection conn = java.sql.DriverManager.getConnection(urlMaster, username, password);
             Statement statement = conn.createStatement()) {

            // Comprobamos si tu base de datos ya existe
            String checkDbQuery = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";
            var resultSet = statement.executeQuery(checkDbQuery);

            if (!resultSet.next()) {
                // Si no existe, la creamos ANTES de que Spring haga cualquier otra cosa
                statement.executeUpdate("CREATE DATABASE " + dbName);
                System.out.println(">>> [IMPORTANTE] Base de datos '" + dbName + "' creada exitosamente.");
            } else {
                System.out.println(">>> La base de datos '" + dbName + "' ya existía.");
            }

        } catch (Exception e) {
            System.err.println(">>> Error crítico al intentar crear la base de datos: " + e.getMessage());
        }

        // PASO 2: Una vez asegurado que la BD existe, devolvemos el DataSource oficial
        // apuntando ya a tu base de datos definitiva para que la usen Hibernate y Flyway.
        DriverManagerDataSource finalDataSource = new DriverManagerDataSource();
        finalDataSource.setDriverClassName("org.postgresql.Driver");
        finalDataSource.setUrl("jdbc:postgresql://localhost:"+port+"/" + dbName);
        finalDataSource.setUsername(username);
        finalDataSource.setPassword(password);

        return finalDataSource;
    }
}