package com.order.service.controller;


import com.order.service.dto.ProductCreateRequest;
import com.order.service.dto.ProductFilterRequest;
import com.order.service.dto.ProductResponse;
import com.order.service.dto.ProductSummaryResponse;
import com.order.service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(request));
    }

    @GetMapping
    public ResponseEntity<Page<ProductSummaryResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        return ResponseEntity.ok(
                productService.getProducts(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<ProductResponse>> filterProducts(@RequestBody ProductFilterRequest request) {
        return ResponseEntity.ok(
                productService.filterProducts(request));
    }
}