package com.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryResponse {

    private String productId;

    private String productName;

    private String brand;

    private String category;

    private Double price;

    private String thumbnailUrl;

    private Boolean active;
}