package com.rest.example.persistence;

import org.springframework.data.repository.CrudRepository;

import com.rest.example.bean.Library;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface LibraryRepository extends CrudRepository<Library, Long> {
	/**
	 * Finds a library by its name.
	 * This method will be auto-implemented by Spring.
	 *
	 * @param name library name (name of the argument has to match the name of the column "name")
	 * @return the library if found, else null
	 */
	public Library findByName(String name);
}
