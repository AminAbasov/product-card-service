package com.amin.backenddevelopertask.repository;

import com.amin.backenddevelopertask.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
