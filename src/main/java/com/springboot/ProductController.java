package com.springboot;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	@Autowired
	private ProductRepo cr;

	@PostMapping("/product")
	public ResponseEntity<Product> saveData(@RequestBody Product c) {
		return new ResponseEntity<>(cr.save(c), HttpStatus.CREATED);
	}

	@GetMapping("/product")
	public ResponseEntity<List<Product>> getAllData() {
		List<Product> lst = cr.findAll();
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getDataById(@PathVariable int product_id) {
		Optional<Product> obj = cr.findById(product_id);
		if (obj.isPresent()) {
			return new ResponseEntity<>(obj.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<Product> updateDataById(@PathVariable int product_id, @RequestBody Product c) {
		Optional<Product> obj = cr.findById(product_id);
		if (obj.isPresent()) {
			obj.get().setProduct_name(c.getProduct_name());
			obj.get().setProduct_price(c.getProduct_price());
			obj.get().setCategory(c.getCategory());
			return new ResponseEntity<>(cr.save(obj.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<Product> deleteDataById(@PathVariable int product_id) {
		Optional<Product> obj = cr.findById(product_id);
		if (obj.isPresent()) {
			cr.deleteById(product_id);
			return new ResponseEntity<>(obj.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@GetMapping("/product/page")
	public List<Product> getPageOne() {
		Pageable p = PageRequest.of(0, 5, Sort.by("product_id").ascending());
		Page<Product> page = cr.findAll(p);
		return page.getContent();
	}
}
