package com.example.car_boot.Config;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RDF4JConfig {
    private static final String RDF4J_SERVER = "http://localhost:8080/rdf4j-server";

    @Bean
    public RemoteRepositoryManager repositoryManager() {
        RemoteRepositoryManager manager = new RemoteRepositoryManager(RDF4J_SERVER);
        manager.init(); // Initialize the RDF4J manager
        return manager;
    }

    @Bean
    public Repository repository(RemoteRepositoryManager manager) {
        Repository repo = manager.getRepository("barometer");
        if (repo == null) {
            throw new IllegalArgumentException("Repository 'barometer' not found.");
        }
        return repo;
    }

    @Bean
    public RepositoryConnection repositoryConnection(Repository repository) {
        return repository.getConnection();
    }
}
