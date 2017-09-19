package com.rest.example.persistence;

import org.springframework.data.repository.CrudRepository;

import com.rest.example.bean.Stock;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface StockRepository extends CrudRepository<Stock, Long> {

}
