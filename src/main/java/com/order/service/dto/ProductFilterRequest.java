package com.order.service.dto;

import lombok.Data;


@Data
public class ProductFilterRequest {

    private String category;

    private String brand;

    private Double minPrice;

    private Double maxPrice;

    private Boolean active;

    private String sortBy = "createdAt";

    private String sortDirection = "DESC";

    private Integer page = 0;

    private Integer size = 10;
}