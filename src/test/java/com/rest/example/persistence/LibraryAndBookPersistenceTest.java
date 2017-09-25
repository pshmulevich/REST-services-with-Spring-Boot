package com.rest.example.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rest.example.Application;
import com.rest.example.TestContextConfiguration;
import com.rest.example.bean.Book;
import com.rest.example.bean.Library;

// Make this a Spring JUnit test and enable autowiring
@RunWith(SpringJUnit4ClassRunner.class)
// Tells how to find the Beans
@ContextConfiguration(classes = { Application.class })
// Enables to read the application.properties file
@SpringBootTest(classes = TestContextConfiguration.class)
// Cleans up the DB after unit test
@Transactional
public class LibraryAndBookPersistenceTest {

	// @Autowired will ensure that the field is initialized to a LibraryRepository instance (aka Bean)
	@Autowired
	private LibraryRepository libraryRepository;

	// @Autowired will ensure that the field is initialized to a BookRepository instance (aka Bean)
	@Autowired
	private BookRepository bookRepository;

	@Test
	public void testLibraryCreation() {
		// testing the Library class itself
		Library library1 = new Library();
		library1.setName("FirstLibrary");
		assertTrue(library1.getName().equals("FirstLibrary"));

		// testing the Library persistence
		libraryRepository.save(library1);
		assertNotNull(library1.getId());
	}

	@Test
	public void testBookCreation() {

		// create the Library class itself
		Library library2 = new Library();
		library2.setName("SecondLibrary");

		// save library
		libraryRepository.save(library2);

		// testing the Book class itself
		Book book1 = new Book();
		book1.setTitle("BookOne");
		book1.setISBN("1234");
		book1.setLibrary(library2);
		// testing the Book persistence
		bookRepository.save(book1);
		assertNotNull(book1.getId());
	}

	@Test
	public void testUpdate() {

		// create the Library class itself
		Library library2 = new Library();
		library2.setName("SecondLibrary");

		// save library
		libraryRepository.save(library2);

		// testing the Book class itself
		Book book1 = new Book();
		book1.setTitle("BookOne");
		book1.setISBN("1234");
		book1.setLibrary(library2);
		// testing the Book persistence
		bookRepository.save(book1);
		book1.setTitle("BookOne-changed");

		// save book again after the update
		bookRepository.save(book1);

		// load the book from the database
		Book updatedBook1 = bookRepository.findOne(book1.getId());
		assertEquals("Title wasn't updated", "BookOne-changed", updatedBook1.getTitle());
	}

	@Test
	public void testBookFindAll() {

		// create the Library class itself
		Library library2 = new Library();
		library2.setName("SecondLibrary");
		assertTrue(library2.getName().equals("SecondLibrary"));

		// create the Library persistence
		libraryRepository.save(library2);
		assertNotNull("Object has not been saved", library2.getId());

		// testing the Book class itself
		Book book1 = new Book();
		book1.setTitle("BookOne");
		book1.setISBN("1234");
		book1.setLibrary(library2);
		// testing the Library persistence
		bookRepository.save(book1);

		Book book2 = new Book();
		book2.setTitle("BookTwo");
		book2.setISBN("5678");
		book2.setLibrary(library2);
		// testing the Library persistence
		bookRepository.save(book2);

		Book book3 = new Book();
		book3.setTitle("Bookthree");
		book3.setISBN("9101");
		book3.setLibrary(library2);
		// testing the Library persistence
		bookRepository.save(book3);

		assertNotNull("Object has not been saved", book1.getId());
		assertEquals("ISBN didn't match expected", "1234", book1.getISBN());
		assertNotNull("Object has not been saved", book2.getId());
		assertEquals("ISBN didn't match expected", "5678", book2.getISBN());
		assertNotNull("Object has not been saved", book3.getId());
		assertEquals("ISBN didn't match expected", "9101", book3.getISBN());

		Iterable<Book> foundBooks = bookRepository.findAll();
		int numBooks = 0;
		Iterator<Book> bookIterator = foundBooks.iterator();
		while (bookIterator.hasNext()) {
			bookIterator.next();
			numBooks++;
		}
		assertEquals("Expected 3 books", 3, numBooks);
	}

	/**
	 * This test verifies that the custom repository method
	 * findByTitle() works as expected
	 */
	@Test
	public void testFindByTitle() {

		// create the Library class itself
		Library library = new Library();
		library.setName("Library");
		assertTrue(library.getName().equals("Library"));

		// create the Library persistence
		libraryRepository.save(library);
		assertNotNull("Object has not been saved", library.getId());

		// testing the Book class itself
		Book book1 = new Book();
		book1.setTitle("BookOne");
		book1.setISBN("1234");
		book1.setLibrary(library);
		// testing the Library persistence
		bookRepository.save(book1);

		Book book2 = new Book();
		book2.setTitle("BookTwo");
		book2.setISBN("5678");
		book2.setLibrary(library);
		// testing the Library persistence
		bookRepository.save(book2);

		Book book3 = new Book();
		book3.setTitle("Bookthree");
		book3.setISBN("9101");
		book3.setLibrary(library);
		// testing the Library persistence
		bookRepository.save(book3);

		assertNotNull("Object has not been saved", book1.getId());
		assertEquals("ISBN didn't match expected", "1234", book1.getISBN());
		assertNotNull("Object has not been saved", book2.getId());
		assertEquals("ISBN didn't match expected", "5678", book2.getISBN());
		assertNotNull("Object has not been saved", book3.getId());
		assertEquals("ISBN didn't match expected", "9101", book3.getISBN());

		/**
		 * Positive test: testing to make sure an existing book is found
		 */
		Book foundBookOne = bookRepository.findByTitle("BookOne");
		assertNotNull("Book not found", foundBookOne);
		assertEquals("Found wrong book", book1.getId(), foundBookOne.getId());

		/**
		 * Negative test: testing to make sure a nonexistant book isn't found
		 */
		Book falseBook = bookRepository.findByTitle("BookBlah");
		assertNull("Book that did not exist was found", falseBook);

	}

	/**
	 * This test can verify that you can check the data from the database
	 */
	@Test
	public void testLibraryBookList() {

		// create the Library class itself
		Library library = new Library();
		library.setName("Library");

		// create the Library persistence
		libraryRepository.save(library);
		assertNotNull("Object has not been saved", library.getId());

		// Create a few books and add them each to the library
		Book book1 = new Book();
		book1.setTitle("BookOne");
		book1.setISBN("1234");
		book1.setLibrary(library);

		bookRepository.save(book1);

		Book book2 = new Book();
		book2.setTitle("BookTwo");
		book2.setISBN("5678");
		book2.setLibrary(library);
		bookRepository.save(book2);

		Book book3 = new Book();
		book3.setTitle("Bookthree");
		book3.setISBN("9101");
		book3.setLibrary(library);
		bookRepository.save(book3);

		// Testing the library to see if the books were inserted
		Library myLibrary = libraryRepository.findOne(library.getId());
		// actual
		Set<Book> bookSet = myLibrary.getBookSet();
		// expected
		Set<Book> expectedBookSet = new HashSet<>();
		expectedBookSet.add(book1);
		expectedBookSet.add(book2);
		expectedBookSet.add(book3);
		assertEquals("Book sets didn't match", expectedBookSet, bookSet);
	}
}
