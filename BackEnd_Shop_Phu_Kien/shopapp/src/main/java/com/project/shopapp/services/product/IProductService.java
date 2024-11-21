package com.project.shopapp.services.product;

import com.project.shopapp.dtos.requests.ProductDTO;
import com.project.shopapp.dtos.requests.ProductImageDTO;
import com.project.shopapp.dtos.responses.product.ProductResponse;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;

    Product getProductById(int id) throws Exception;

    Page<ProductResponse> getAllProducts(String keyword,
                                         int categoryId, PageRequest pageRequest);

    Product updateProduct(int id, ProductDTO productDTO) throws Exception;

    void deleteProduct(int id);

    boolean existsByName(String name);

    ProductImage createProductImage(int productId, ProductImageDTO productImageDTO) throws Exception;

}
