package com.amin.backenddevelopertask.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String productName;

    private Double price;

    @Builder.Default
    private List<String> productImages = new ArrayList<>();
}

