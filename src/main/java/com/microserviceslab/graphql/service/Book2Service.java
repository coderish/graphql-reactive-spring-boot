package com.microserviceslab.graphql.service;

import com.microserviceslab.graphql.constant.Category;
import com.microserviceslab.graphql.model.Book;
import com.microserviceslab.graphql.repository.BookRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class Book2Service {
	
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private Author2Service authorService;
	
	public DataFetcher<CompletableFuture<Book>> getBook2() {
		return env -> {
			String bookId = env.getArgument("id");
			return bookRepository.getBook(UUID.fromString(bookId)).toFuture();
		};
	}
	
	public DataFetcher<CompletableFuture<List<Book>>> getBooks2() {
		return env -> bookRepository.getBooks().collectList().toFuture();
	}
	
	public DataFetcher<CompletableFuture<String>> createBook2() {
		return env -> {
			String bookName = env.getArgument("bookName");
			String authorName = env.getArgument("authorName");
			
			int pages = env.getArgument("pages");
			int age = env.getArgument("age");
			Category category = Category.valueOf(env.getArgument("category"));
			
			Book book = new Book();
			book.setName(bookName);
			book.setPages(pages);
			book.setCategory(category);
			
			return bookRepository.createBook(book).flatMap(
					bookId -> authorService.createAuthor2(authorName, age, bookId)
					.map(authorId -> bookId.toString()))
			.toFuture();
		};
	}
}
