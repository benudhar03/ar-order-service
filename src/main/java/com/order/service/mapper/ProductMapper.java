package com.order.service.mapper;

import com.order.service.entity.Product;
import com.order.service.dto.ProductCreateRequest;
import com.order.service.dto.ProductResponse;
import com.order.service.dto.ProductSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    /*
     * REQUEST DTO -> DOCUMENT
     */
    public Product toDocument(ProductCreateRequest request) {

        if (request == null) {
            return null;
        }

        return Product.builder()
                .vendorId(request.getVendorId())
                .vendorName(request.getVendorName())
                .productName(request.getProductName())
                .skuCode(request.getSkuCode())
                .category(request.getCategory())
                .subCategory(request.getSubCategory())
                .brand(request.getBrand())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .description(request.getDescription())
                .imageUrls(request.getImageUrls())
                .active(request.getActive())
                .attributes(request.getAttributes())
                .build();
    }

    /*
     * DOCUMENT -> RESPONSE DTO
     */
    public ProductResponse toResponse(Product product) {

        if (product == null) {
            return null;
        }

        return ProductResponse.builder()
                .productId(product.getProductId())
                .vendorId(product.getVendorId())
                .vendorName(product.getVendorName())
                .productName(product.getProductName())
                .skuCode(product.getSkuCode())
                .category(product.getCategory())
                .subCategory(product.getSubCategory())
                .brand(product.getBrand())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .imageUrls(product.getImageUrls())
                .status(product.getStatus())
                .active(product.getActive())
                .version(product.getVersion())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    /*
     * DOCUMENT -> SUMMARY RESPONSE DTO
     */
    public ProductSummaryResponse toSummaryResponse(
            Product product) {

        if (product == null) {
            return null;
        }

        return ProductSummaryResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .brand(product.getBrand())
                .category(product.getCategory())
                .price(product.getPrice())
                .thumbnailUrl(
                        product.getImageUrls() != null
                                && !product.getImageUrls().isEmpty()
                                ? product.getImageUrls().get(0)
                                : null)
                .active(product.getActive())
                .build();
    }
}