package com.ranjit.spring.graphl.service;

import com.ranjit.spring.graphl.model.Book;
import com.ranjit.spring.graphl.repository.BookRepository;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class GraphQLService {
    @Value("classpath:books.graphql")
    Resource resource;

    private GraphQL graphQL;

    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;

    @Autowired
    private BookDataFetcher bookDataFetcher;

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    private void loadSchema() throws IOException {

        //load data
        loadDataIntoHSQL();
        //get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistory = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistory, wiring);
        graphQL= GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring ->
                        typeWiring.dataFetcher("allBooks", allBooksDataFetcher)
                                .dataFetcher("book", bookDataFetcher)
                )
                .build();
    }


    private void loadDataIntoHSQL(){
        Stream.of(
                new Book("101", "How to win Friends", "Kindle Edition",
                        new String[]{
                                "Dale Karnegie"
                        }, ""),
                new Book("102", "Thinking Big", "Olliey",
                        new String[]{
                                "author1","author2"
                        }, ""),
                new Book("103", "Book of clouds", "Kindle Edition",
                        new String[]{
                                "Sample books 1234"
                        }, ""),
                new Book("104", "Spring in action", "TMH",
                        new String[]{
                                "Tata"
                        }, ""),
                new Book("105", "Java", "Cloudy",
                        new String[]{
                                "Sample books 1234"
                        }, "")
        ).forEach(
                book -> {
                    bookRepository.save(book);
                }
        );
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
