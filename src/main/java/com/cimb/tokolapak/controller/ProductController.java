package com.cimb.tokolapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.ProductRepo;
import com.cimb.tokolapak.entity.Product;
import com.cimb.tokolapak.service.ProductService;

@RestController
@CrossOrigin
public class ProductController {
	
	// Controller -> service -> DAO/ Repo -> DB
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public Iterable<Product> getProducts() {
		
		return productService.getProducts();
	}
	
	@GetMapping("/products/{id}")
	public Product getProductById(@PathVariable int id) {
		return productService.getProductById(id).get();
	}
	
	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}
	
	@DeleteMapping("/products/{id}")
	public void deleteProductById(@PathVariable int id) {
		
		// bisa ditulis tanpa this
		productService.deleteProductById(id);
	}
	
	@PutMapping("/products")
	public Product updateProduct(@RequestBody Product product) {			
		return productService.updateProduct(product);
		
		//buat delete
//		productRepo.deleteById(product.getId());
	}
	
	// kalo mau pake PUT untuk update dan delete maka nama route nya tidak boleh sama
		
	@GetMapping("/productName/{productName}")
	public Product getProductByName (@PathVariable String productName) {
		return productRepo.findByProductName(productName);
	}
	
	@GetMapping("/products/custom")
	public Iterable<Product> customQueryGet(@RequestParam double minPrice,@RequestParam String productName){
		return productRepo.findProductByMinPrice(minPrice, productName);
	}
	
	@GetMapping("/products/custom2")
	public Iterable<Product> customQueryGet2(@RequestParam double maxPrice,@RequestParam String productName){
		return productRepo.findProductByMaxPrice(maxPrice, productName);
	}
}
