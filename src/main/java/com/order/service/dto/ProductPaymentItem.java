package com.order.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductPaymentItem {

    @NotBlank
    private String productId;

    private String productName;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    /**
     * SKU / Variant
     */
    private String sku;

    /**
     * Size/Color/etc
     */
    private Map<String, String> attributes;
}