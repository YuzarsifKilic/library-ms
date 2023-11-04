package com.yuzarsif.bookservice;

import com.yuzarsif.bookservice.model.Book;
import com.yuzarsif.bookservice.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(BookRepository bookRepository) {
		return args -> {
			Book book1 = new Book("Dünyanın Gözü", 2000, "Robert Jordan", "İthaki Yayınevi", "123456");
			Book book2 = new Book("Yüzüklerin Efendisi", 1960, "J.R.R Tolkien", "Metis Yayıncılık", "456789");
			Book book3 = new Book("Harry Potter ve Felsefe Taşı", 1997, "J. K. Rowling", "YKB Yayınları", "987654");

			List<Book> bookList = bookRepository.saveAll(Arrays.asList(book1, book2, book3));
		};
	}
}
