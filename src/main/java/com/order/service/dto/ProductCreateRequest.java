package com.order.service.dto;

import com.order.service.enums.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {


    @NotBlank(message = "Vendor Id is required")
    private String vendorId;

    @NotBlank(message = "Vendor Name is required")
    private String vendorName;

    /*
     * Product Basic Details
     */
    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "SKU Code is required")
    private String skuCode;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Sub category is required")
    private String subCategory;

    @NotBlank(message = "Brand is required")
    private String brand;

    /*
     * Pricing & Inventory
     */
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantity;

    @NotBlank(message = "Description is required")
    private String description;
    @NotEmpty(message = "At least one image is required")
    private List<String> imageUrls;
    private ProductStatus status = ProductStatus.DRAFT;
    private Boolean active = true;

    /*
     * Optional Product Attributes
     * Flexible structure for dynamic categories
     *
     * Example:
     * {
     *   "color": "Black",
     *   "storage": "256GB",
     *   "ram": "12GB"
     * }
     */
    private Map<String, Object> attributes;
}