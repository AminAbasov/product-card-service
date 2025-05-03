package com.amin.backenddevelopertask.service.concrete;

import com.amin.backenddevelopertask.dto.request.UpdateProductRequest;
import com.amin.backenddevelopertask.dto.response.DtoConverter;
import com.amin.backenddevelopertask.dto.response.ProductDto;
import com.amin.backenddevelopertask.entity.Product;
import com.amin.backenddevelopertask.repository.ProductRepository;
import com.amin.backenddevelopertask.service.cloudinary.CloudinaryService;
import com.amin.backenddevelopertask.service.interfaces.ProductServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;
    private final DtoConverter dtoConverter;
    private final CloudinaryService cloudinaryService;

    public ProductDto createProduct(Product product, MultipartFile file) throws Exception {
        if (file != null && !file.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(file);
            product.setProductImages(List.of(imageUrl));  // Şəkil URL-i əlavə et
        }

        Product savedProduct = productRepository.save(product);

        return dtoConverter.convertToProductDto(savedProduct);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return cloudinaryService.uploadFile(file);
    }

    public List<ProductDto> getAllProduct() {
        List<Product> products = productRepository.findAll();

        if (products == null) {
            throw new RuntimeException("Məhsullar alınmadı, null qaytarıldı");
        }

        return products.stream()
                .map(dtoConverter::convertToProductDto) // Məhsulları DTO-ya çevirir
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return dtoConverter.convertToProductDto(product);
    }

    public ProductDto updateProduct(Long id, UpdateProductRequest updateProductRequest) {
        System.out.println("Gələn şəkillər: " + updateProductRequest.getProductImages());

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductName(updateProductRequest.getProductName());
        product.setPrice(updateProductRequest.getPrice());
        product.setProductImages(updateProductRequest.getProductImages());

        return dtoConverter.convertToProductDto(productRepository.save(product));
    }

    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}

