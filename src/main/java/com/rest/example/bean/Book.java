package com.rest.example.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

// Tells hibernate to make a table for Book
@Entity
@Table(name = "book", uniqueConstraints = { @UniqueConstraint(columnNames = "isbn") })
public class Book implements Serializable {
	private static final long serialVersionUID = 2218975425707810701L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "ISBN", nullable = false)
	private String isbn;

	@ManyToOne(targetEntity = Library.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "LIBRARY_ID", nullable = false)
	private Library library;

	public Book() {
	}

	public Book(final String isbn) {
		this.isbn = isbn;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(final Library library) {
		this.library = library;
		// Make sure to add the book to the library
		// (reciprocal action)
		library.getBookSet().add(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getISBN() {
		return isbn;
	}

	public void setISBN(final String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Book other = (Book) obj;
		if (isbn == null) {
			if (other.isbn != null) {
				return false;
			}
		} else if (!isbn.equals(other.isbn)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Book [isbn=").append(isbn).append("]");
		return builder.toString();
	}
}