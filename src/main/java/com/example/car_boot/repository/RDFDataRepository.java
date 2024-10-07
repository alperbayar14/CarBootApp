package com.example.car_boot.repository;

import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class RDFDataRepository {
    @Autowired
    private RepositoryConnection connection;

    public String executeQuery(String queryString) {
        StringBuilder result = new StringBuilder();
        try {
            TupleQuery query = connection.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult queryResult = query.evaluate()) {
                while (queryResult.hasNext()) {
                    var bindingSet = queryResult.next();
                    result.append(bindingSet.toString()).append("\n");
                }
            }
        } catch (Exception e) {
            result.append("Error executing query: ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        }
        return result.toString();
    }

    public String executeAllQueries() {
        StringBuilder allResults = new StringBuilder();
        String[] queries = {
                "PREFIX ont: <http://example.org/ontology#> SELECT ?product ?manufacturer ?property WHERE { ?product a ont:Barometer; ont:hasManufacturer ?manufacturer; ont:hasProperty ?property. }",
                "PREFIX ont: <http://example.org/ontology#> SELECT ?manufacturer (COUNT(?product) as ?number_of_products) WHERE { ?product a ont:Barometer; ont:hasManufacturer ?manufacturer. } GROUP BY ?manufacturer",
                "PREFIX ont: <http://example.org/ontology#> SELECT ?product WHERE { ?product a ont:Barometer; ont:hasManufacturer 'SpecificManufacturer'. }",
                "PREFIX ont: <http://example.org/ontology#> SELECT ?product ?manufacturer ?price WHERE { ?product a ont:Barometer; ont:hasMaterial 'SpecificMaterial'; ont:hasManufacturer ?manufacturer; ont:hasPrice ?price. }",
                "PREFIX ont: <http://example.org/ontology#> SELECT DISTINCT ?material WHERE { ?product a ont:Barometer; ont:hasMaterial ?material. }"
        };
        for (String query : queries) {
            allResults.append(executeQuery(query)).append("\n");
        }
        return allResults.toString();
    }
}
