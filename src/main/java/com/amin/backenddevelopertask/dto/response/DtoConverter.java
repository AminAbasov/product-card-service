package com.amin.backenddevelopertask.dto.response;

import com.amin.backenddevelopertask.entity.Card;
import com.amin.backenddevelopertask.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {


public ProductDto convertToProductDto(Product product) {
    if (product == null) {
        throw new RuntimeException("Product null dəyəridir");
    }
    System.out.println("Converting product: " + product.getProductName());
    return ProductDto.builder()
            .price(product.getPrice())
            .productName(product.getProductName())
            .productImages(product.getProductImages())
            .build();
}

    public CardDto convertToCardDto(Card card){
        return CardDto.builder()
                .cardNumber(card.getCardNumber())
                .expireDate(card.getExpireDate())
                .cvv(card.getCvv())
                .build();
    }

}
