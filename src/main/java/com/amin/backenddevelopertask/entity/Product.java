package com.amin.backenddevelopertask.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String productName;
    @NotNull
    private Double price;

    @NotNull
            @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(ignoreUnknown = true)

    List<String> productImages;
}
