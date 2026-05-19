package com.order.service.dto;

import com.order.service.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String productId;

    private String vendorId;

    private String vendorName;

    private String skuCode;

    private String productName;

    private String category;

    private String subCategory;

    private String brand;

    private Double price;

    private Integer quantity;

    private ProductStatus status;

    private Boolean active;

    private String description;

    private Long version;

    private List<String> imageUrls;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}