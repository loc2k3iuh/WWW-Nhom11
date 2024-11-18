package com.project.shopapp.dtos.responses.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.dtos.responses.BaseResponse;
import com.project.shopapp.models.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse extends BaseResponse {

    String name;
    Float price;
    String description;
    String thumbnail;
    @JsonProperty("category_id")
    int categoryId;


    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse=  ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .thumbnail(product.getThumbnail())
                .categoryId(product.getCategory().getId())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
