package com.example.car_boot.service;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;

import org.springframework.stereotype.Service;

@Service
public class SPARQLQueryService {
    private final Repository repository;

    // Constructor injection for configuration properties
    public SPARQLQueryService(@org.springframework.beans.factory.annotation.Value("${rdf4j.server.url}") String serverUrl,
                              @org.springframework.beans.factory.annotation.Value("${rdf4j.repository.id}") String repositoryId) {
        RemoteRepositoryManager manager = new RemoteRepositoryManager(serverUrl);
        try {
            manager.init();
            this.repository = manager.getRepository(repositoryId);
            if (this.repository == null) {
                throw new IllegalArgumentException("Repository ID '" + repositoryId + "' not found.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize RDF repository manager", e);
        }
    }

    public void executeAllQueries() {
        String[] queries = {
                "PREFIX ont: <http://example.org/ontology#> SELECT ?product ?manufacturer ?property WHERE { ?product a ont:Barometer; ont:hasManufacturer ?manufacturer; ont:hasProperty ?property. }",
                "PREFIX ont: <http://example.org/ontology#> SELECT ?manufacturer (COUNT(?product) as ?number_of_products) WHERE { ?product a ont:Barometer; ont:hasManufacturer ?manufacturer. } GROUP BY ?manufacturer",
                "PREFIX ont: <http://example.org/ontology#> SELECT ?product WHERE { ?product a ont:Barometer; ont:hasManufacturer 'SpecificManufacturer'. }",
                "PREFIX ont: <http://example.org/ontology#> SELECT ?product ?manufacturer ?price WHERE { ?product a ont:Barometer; ont:hasMaterial 'SpecificMaterial'; ont:hasManufacturer ?manufacturer; ont:hasPrice ?price. }",
                "PREFIX ont: <http://example.org/ontology#> SELECT DISTINCT ?material WHERE { ?product a ont:Barometer; ont:hasMaterial ?material. }"
        };

        for (String query : queries) {
            executeQuery(query);
        }
    }

    private void executeQuery(String queryString) {

            RepositoryConnection conn = null;
            try {
                conn = repository.getConnection();
                TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
                try (TupleQueryResult result = tupleQuery.evaluate()) {
                    // Check and print if the result has no entries
                    if (!result.hasNext()) {
                        System.out.println("No results found for query: " + queryString);
                        return;
                    }
                    // Processing results
                    System.out.println("Results for query: " + queryString);
                    while (result.hasNext()) {
                        var bindingSet = result.next();
                        for (String name : bindingSet.getBindingNames()) {
                            Value value = bindingSet.getValue(name);
                            if (value != null) {
                                System.out.println(name + ": " + value.stringValue());
                            } else {
                                System.out.println(name + ": null");
                            }
                        }
                        System.out.println(); // Add a blank line between results for readability
                    }
                }
            } catch (Exception e) {
                System.err.println("Error executing query: " + queryString);
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        System.err.println("Error closing repository connection.");
                        e.printStackTrace();
                    }

            }
        }

    }
}
