package com.order.service.entity;

import com.order.service.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "products")
@CompoundIndexes({

        @CompoundIndex(
                name = "vendor_product_idx",
                def = "{'vendorId': 1, 'productId': 1}"
        ),

        @CompoundIndex(
                name = "vendor_category_idx",
                def = "{'vendorId': 1, 'category': 1}"
        ),

        @CompoundIndex(
                name = "search_filter_idx",
                def = "{'category': 1, 'brand': 1, 'price': 1}"
        )
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String productId;

    // Vendor Ownership
    @Indexed
    private String vendorId;

    @Indexed
    private String vendorName;

    private String skuCode;

    @Indexed
    private String productName;

    @Indexed
    private String category;

    @Indexed
    private String subCategory;

    @Indexed
    private String brand;

    private Double price;

    private Integer quantity;

    private String description;

    private List<String> imageUrls;

    private ProductStatus status;

    private Boolean active;

    @Version
    private Long version;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Map<String, Object> attributes;
}