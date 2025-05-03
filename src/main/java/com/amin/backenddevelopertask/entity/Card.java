package com.amin.backenddevelopertask.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
//
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 16 rəqəmli string / number
    @Size(min = 16, max = 16, message = "Card number must be exactly 16 digits")
    @Pattern(regexp = "\\d{16}", message = "Card number must only contain digits")
    private String cardNumber;

    // MM/YY formatında tarix
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{2})$",
            message = "Son istifadə tarixi MM/YY formatında olmalıdır (məs: 12/25). Ay 01-12 aralığında olmalıdır.")
    private String expireDate;


    // 3 rəqəmli CVV
    @Size(min = 3, max = 3, message = "CVV must be exactly 3 digits")
    @Pattern(regexp = "\\d{3}", message = "CVV must only contain digits")
    private String cvv;
}


