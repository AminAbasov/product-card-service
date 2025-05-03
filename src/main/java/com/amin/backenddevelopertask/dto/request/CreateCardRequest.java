package com.amin.backenddevelopertask.dto.request;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCardRequest {

    private Long id;

    @Size(min = 16, max = 16, message = "Card number must be exactly 16 digits")
    @Pattern(regexp = "\\d{16}", message = "Card number must only contain digits")
    private String cardNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{2})$",
            message = "Son istifadə tarixi MM/YY formatında olmalıdır (məs: 12/25). Ay 01-12 aralığında olmalıdır.")
    private String expireDate;


    @Size(min = 3, max = 3, message = "CVV must be exactly 3 digits")
    @Pattern(regexp = "\\d{3}", message = "CVV must only contain digits")
    private String cvv;


}

