package com.amin.backenddevelopertask.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class UpdateProductRequest {
        @NotEmpty(message = "Məhsul adı boş ola bilməz")
        private String productName;

        @NotNull(message = "Qiymət boş ola bilməz")
        private Double price;

        private List<String> productImages;



}

