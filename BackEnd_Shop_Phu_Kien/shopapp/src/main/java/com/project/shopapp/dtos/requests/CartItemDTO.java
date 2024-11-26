package com.project.shopapp.dtos.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("quantity")
    private Integer quantity;
}
