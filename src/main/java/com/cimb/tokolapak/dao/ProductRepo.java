package com.cimb.tokolapak.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cimb.tokolapak.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
//	nama field ngikutin yang di entity bukan di db
	public Product findByProductName(String productName);
											// kalo tanpa nativequery akan pake JPQL bukan SQL
	@Query(value = "SELECT * FROM product WHERE price < ?1 and product_name = ?2", nativeQuery = true)
	public Iterable<Product> findProductByMinPrice(double minPrice, String productName);

	@Query(value = "SELECT * FROM Product WHERE price < :maxPrice and product_name like %:productName%", nativeQuery = true)
	public Iterable<Product> findProductByMaxPrice(@Param("maxPrice") double maxPrice, @Param("productName") String namaProduk);

}
