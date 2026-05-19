package com.order.service.entity;

import com.order.service.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
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
public class Product implements Persistable<String> {

    @Id
    private String productId;

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Map<String, Object> attributes;

    @Transient
    private boolean isNew = true;

    @Override
    public String getId() {
        return this.productId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}