package com.amin.backenddevelopertask.controller;

import com.amin.backenddevelopertask.dto.request.UpdateProductRequest;
import com.amin.backenddevelopertask.dto.response.ProductDto;
import com.amin.backenddevelopertask.entity.Product;
import com.amin.backenddevelopertask.service.cloudinary.CloudinaryService;
import com.amin.backenddevelopertask.service.interfaces.ProductServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceInterface productService;
    private final CloudinaryService cloudinaryService;

    @Operation(summary = "Get all products", description = "Retrieve a list of all products stored in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all products"),
            @ApiResponse(responseCode = "204", description = "No content, no products found")
    })
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProduct();
        if (products == null || products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/by-id/{id}")
    public ResponseEntity<ProductDto> getProductById(
            @Parameter(description = "ID of the product to be retrieved")
            @PathVariable Long id) {
        try {
            ProductDto product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Update product", description = "Update the details of a product based on the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") String productJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        try {
            UpdateProductRequest request = new ObjectMapper().readValue(productJson, UpdateProductRequest.class);

            if (files != null) {
                List<String> urls = files.stream()
                        .map(file -> {
                            try {
                                return cloudinaryService.uploadFile(file);
                            } catch (IOException e) {
                                throw new RuntimeException("File upload failed: " + e.getMessage());
                            }
                        })
                        .toList();

                if (request.getProductImages() == null) {
                    request.setProductImages(new ArrayList<>());
                }
                request.getProductImages().addAll(urls);
            }

            return ResponseEntity.ok(productService.updateProduct(id, request));
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Delete product", description = "Delete a specific product by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID of the product to be deleted") @PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create new product with image",
            description = "Create a new product with REQUIRED image upload (JPEG, PNG, GIF). Max size: 5MB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product data or missing/invalid image"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> createProduct(
            @Parameter(description = "Product data in JSON format", required = true)
            @RequestPart("product") @Valid String productJson,

            @Parameter(description = "Product image file (REQUIRED - JPEG/PNG/GIF)", required = true)
            @RequestPart("file") MultipartFile file) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(productJson, Product.class);

            if (file != null && !file.isEmpty()) {
                String imageUrl = cloudinaryService.uploadFile(file);
                product.setProductImages(List.of(imageUrl));
            }

            ProductDto savedProduct = productService.createProduct(product, file);
            return ResponseEntity.ok(savedProduct);

        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @Operation(summary = "Create new product with multiple images",
            description = "Create a new product with multiple image uploads (JPEG, PNG, GIF). Max 10 files, 5MB each")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data or files"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/upload-multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> createProductWithMultipleFiles(
            @Parameter(description = "Product data in JSON format", required = true)
            @RequestPart("product") @Valid String productJson,

            @Parameter(description = "List of image files (max 10)", required = true)
            @RequestPart("files") List<MultipartFile> files) {

        if (files == null || files.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(productJson, Product.class);

            List<String> imageUrls = cloudinaryService.uploadMultipleFiles(files);
            product.setProductImages(imageUrls);

            ProductDto savedProduct = productService.createProduct(product, null);
            return ResponseEntity.ok(savedProduct);

        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
