package com.microserviceslab.graphql;

import java.io.IOException;

import com.microserviceslab.graphql.service.Author2Service;
import com.microserviceslab.graphql.service.Book2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import com.microserviceslab.graphql.service.AuthorService;
import com.microserviceslab.graphql.service.BookService;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import io.r2dbc.spi.ConnectionFactory;

@SpringBootApplication
public class GraphqlApplication {

	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private Book2Service book2Service;
	@Autowired
	private Author2Service author2Service;
	
	public static void main(String[] args) {
		SpringApplication.run(GraphqlApplication.class, args);
	}

	@Bean
	public ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory factory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(factory);
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
		initializer.setDatabasePopulator(populator);
		return initializer;
	}
	
	@Bean
	public GraphQL books2GraphQL() throws IOException {
		SchemaParser schemaParser = new SchemaParser();
		ClassPathResource schema = new ClassPathResource("books2.graphql");
		TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema.getInputStream());
		
		RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
		.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getBook2", book2Service.getBook2()))
		.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getBooks2", book2Service.getBooks2()))
		.type(TypeRuntimeWiring.newTypeWiring("Mutation").dataFetcher("createBook2", book2Service.createBook2()))
		.type(TypeRuntimeWiring.newTypeWiring("Book").dataFetcher("author", author2Service.authorDataFetcher2()))
		.build();
		
		SchemaGenerator generator = new SchemaGenerator();
		GraphQLSchema graphQLSchema = generator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
		return GraphQL.newGraphQL(graphQLSchema).build();
	}

	@Bean
	public GraphQL booksGraphQL() throws IOException {
		SchemaParser schemaParser = new SchemaParser();
		ClassPathResource schema = new ClassPathResource("books.graphql");
		TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema.getInputStream());

		RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
				.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getBook", bookService.getBook()))
				.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getBooks", bookService.getBooks()))
				.type(TypeRuntimeWiring.newTypeWiring("Mutation").dataFetcher("createBook", bookService.createBook()))
				.type(TypeRuntimeWiring.newTypeWiring("Book").dataFetcher("author", authorService.authorDataFetcher()))
				.build();

		SchemaGenerator generator = new SchemaGenerator();
		GraphQLSchema graphQLSchema = generator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
		return GraphQL.newGraphQL(graphQLSchema).build();
	}
}
