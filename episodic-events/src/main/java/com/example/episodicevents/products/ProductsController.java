package com.example.episodicevents.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rudyard Moreno on 5/22/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public List<Object> getProducts() {

        /*List<Product> productList = new ArrayList<Product>();
        productList=productRepository.findAll();*/

        return Arrays.asList(
                new Product("abc123", "Hamilton CD", 1299),
                new DigitalProduct("abc123", "Hamilton", 1299, "http://example.com/playground/hamilton.mp4"),
                new StreamingProduct("abc123", "Hamilton", 1299, "rtmp://example.com/playground/mp4:hamilton.mp4")
        );
    }

    @PostMapping
    public Object createProduct(@RequestBody Product product) {
        System.out.println(product.getClass().getName());
        return product;
    }
}
