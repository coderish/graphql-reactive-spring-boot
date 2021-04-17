package com.microserviceslab.graphql.service;

import com.microserviceslab.graphql.model.Author;
import com.microserviceslab.graphql.model.Book;
import com.microserviceslab.graphql.repository.AuthorRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class Author2Service {
	
	@Autowired
	private AuthorRepository repository;
	
	public Mono<String> createAuthor2(String authorName, int age, UUID bookId) {
		Author author = new Author();
		author.setAge(age);
		author.setName(authorName);
		author.setBookId(bookId);
		return repository.createAuthor(author).map(Object::toString);
	}
	
	public DataFetcher<CompletableFuture<Author>> authorDataFetcher2() {
		return env -> {
			Book book = env.getSource();
			return repository.getAuthor(book.getId()).toFuture();
		};
	}
}



















