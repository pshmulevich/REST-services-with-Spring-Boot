package com.rest.example.persistence;

import org.springframework.data.repository.CrudRepository;

import com.rest.example.bean.Book;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BookRepository extends CrudRepository<Book, Long> {
	/**
	 * Finds a book by its title.
	 * This method will be auto-implemented by Spring.
	 *
	 * @param title book title (name of the argument has to match the name of the column "title")
	 * @return the book if found, else null
	 */
	public Book findByTitle(String title);
}
