package com.microserviceslab.graphql.controller;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microserviceslab.graphql.model.GraphQLRequestBody;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import reactor.core.publisher.Mono;

@RestController
public class GraphQLController {

    @Autowired
    private GraphQL booksGraphQL;

    @Autowired
    private GraphQL books2GraphQL;

    @PostMapping(value = "graphql", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Object>> execute(@RequestBody GraphQLRequestBody body) {
        return Mono.fromCompletionStage(booksGraphQL.executeAsync(ExecutionInput.newExecutionInput().query(body.getQuery())
                .operationName(body.getOperationName()).variables(body.getVariables()).build()))
                .map(ExecutionResult::toSpecification);
    }

    @PostMapping(value = "books-graphql", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> execute2(@RequestBody GraphQLRequestBody body) throws ExecutionException, InterruptedException {
//		return Mono.fromCompletionStage(books2GraphQL.executeAsync(ExecutionInput.newExecutionInput().query(body.getQuery())
//				.operationName(body.getOperationName()).variables(body.getVariables()).build()))
//				.map(ExecutionResult::toSpecification);
        return ResponseEntity.ok(
                books2GraphQL.executeAsync(ExecutionInput.newExecutionInput()
                        .query(body.getQuery())
                        .operationName(body.getOperationName())
                        .variables(body.getVariables())
                        .build())
                        .get()
                        .getData());
    }
}
