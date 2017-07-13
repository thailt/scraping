package com.thai.scraping.repository;

import org.springframework.data.repository.CrudRepository;

import com.thai.scraping.model.Product;

public interface ProductRepository extends CrudRepository<Product, String> {

}
