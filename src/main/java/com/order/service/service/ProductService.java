package com.order.service.service;

import com.order.service.dto.ProductCreateRequest;
import com.order.service.dto.ProductFilterRequest;
import com.order.service.dto.ProductResponse;
import com.order.service.dto.ProductSummaryResponse;
import org.springframework.data.domain.Page;


public interface ProductService {


    ProductResponse createProduct(ProductCreateRequest request);

    ProductResponse getProduct(String id);

    Page<ProductSummaryResponse> getProducts(int page, int size, String sortBy, String direction);

    Page<ProductResponse> filterProducts(ProductFilterRequest request);

}
