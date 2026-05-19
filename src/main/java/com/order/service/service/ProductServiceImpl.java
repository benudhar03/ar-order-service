package com.order.service.service;

import com.order.service.dto.*;
import com.order.service.entity.Product;
import com.order.service.enums.ProductStatus;
import com.order.service.mapper.ProductMapper;
import com.order.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {
        Product product =
                productMapper.toDocument(productCreateRequest);

        product.setStatus(ProductStatus.DRAFT);
        product.setActive(Boolean.TRUE);
        product.setVersion(1L);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct =
                productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse getProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id : " + productId));
        return productMapper.toResponse(product);
    }

    @Override
    public Page<ProductSummaryResponse> getProducts(int page, int size, String sortBy, String direction) {
        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.fromString(direction), sortBy));

        Page<Product> products =
                productRepository.findByActiveTrue(pageable);

        return products.map(
                productMapper::toSummaryResponse);
    }

    @Override
    public Page<ProductResponse> filterProducts(ProductFilterRequest productFilterRequest) {
        List<Criteria> criteriaList =
                new ArrayList<>();

        if (productFilterRequest.getCategory() != null) {
            criteriaList.add(
                    Criteria.where("category")
                            .is(productFilterRequest.getCategory()));
        }

        if (productFilterRequest.getBrand() != null) {
            criteriaList.add(
                    Criteria.where("brand")
                            .is(productFilterRequest.getBrand()));
        }

        if (productFilterRequest.getActive() != null) {
            criteriaList.add(
                    Criteria.where("active")
                            .is(productFilterRequest.getActive()));
        }

        if (productFilterRequest.getMinPrice() != null
                || productFilterRequest.getMaxPrice() != null) {
            Criteria priceCriteria =
                    Criteria.where("price");

            if (productFilterRequest.getMinPrice() != null) {
                priceCriteria.gte(
                        productFilterRequest.getMinPrice());
            }

            if (productFilterRequest.getMaxPrice() != null) {
                priceCriteria.lte(
                        productFilterRequest.getMaxPrice());
            }
            criteriaList.add(priceCriteria);
        }

        Criteria criteria = new Criteria();

        if (!criteriaList.isEmpty()) {
            criteria.andOperator(
                    criteriaList.toArray(
                            new Criteria[0]));
        }
        Query query = new Query(criteria);
        Sort.Direction sortDirection =
                Sort.Direction.fromString(
                        productFilterRequest.getSortDirection());

        query.with(
                Sort.by(sortDirection, productFilterRequest.getSortBy()));

        Pageable pageable =
                PageRequest.of(
                        productFilterRequest.getPage(),
                        productFilterRequest.getSize());

        query.skip(pageable.getOffset());
        query.limit(pageable.getPageSize());

        List<Product> products =
                mongoTemplate.find(
                        query,
                        Product.class);
        long total =
                mongoTemplate.count(
                        Query.of(query)
                                .limit(-1)
                                .skip(-1),
                        Product.class);

        List<ProductResponse> responses =
                products.stream()
                        .map(productMapper::toResponse)
                        .toList();

        return new PageImpl<>(responses, pageable, total);
    }
}
