package com.amin.backenddevelopertask.service.interfaces;

import com.amin.backenddevelopertask.dto.request.UpdateProductRequest;
import com.amin.backenddevelopertask.dto.response.ProductDto;
import com.amin.backenddevelopertask.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductServiceInterface {

    List<ProductDto> getAllProduct();

    ProductDto getProductById(Long id);

    ProductDto updateProduct(Long id, UpdateProductRequest updateProductRequest);

    void deleteProductById(Long id);

    public String uploadImage(MultipartFile file) throws IOException;

    ProductDto createProduct(Product product, MultipartFile file) throws Exception;
}

