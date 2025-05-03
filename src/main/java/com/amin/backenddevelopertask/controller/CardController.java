package com.amin.backenddevelopertask.controller;

import com.amin.backenddevelopertask.dto.request.CreateCardRequest;
import com.amin.backenddevelopertask.dto.response.CardDto;
import com.amin.backenddevelopertask.service.interfaces.CardServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardServiceInterface cardService;


    @Operation(summary = "Create a new card", description = "Create a new credit card and save it to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid card data provided")
    })
    @PostMapping
    public ResponseEntity<CardDto> createCard(@RequestBody @Valid CreateCardRequest createCardRequest) {
        CardDto cardDto = cardService.createCard(createCardRequest);
        return ResponseEntity.status(201).body(cardDto); // 201 status kodu ilə
    }



    @Operation(summary = "Get all cards", description = "Retrieve a list of all cards stored in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all cards"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<CardDto>> getAllCards() {
        List<CardDto> cardDtos = cardService.getAllCards();
        return ResponseEntity.ok(cardDtos); // 200 status kodu ilə
    }

    @Operation(summary = "Get card by ID", description = "Retrieve a specific card by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the card"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getCardById(@Parameter(description = "ID of the card to be retrieved") @PathVariable Long id) {
        try {
            CardDto cardDto = cardService.getCardById(id);
            return ResponseEntity.ok(cardDto); // 200 status kodu ilə
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build(); // 404 status kodu ilə
        }
    }

    @Operation(summary = "Update an existing card", description = "Update the details of a card based on the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the card"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CardDto> updateCard(@Parameter(description = "ID of the card to be updated") @PathVariable Long id,
                                              @RequestBody @Valid CreateCardRequest createCardRequest) {
        try {
            CardDto cardDto = cardService.updateCard(id, createCardRequest);
            return ResponseEntity.status(200).body(cardDto); // 200 status kodu ilə
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build(); // 404 status kodu ilə
        }
    }

    @Operation(summary = "Delete a card", description = "Delete a specific card by its unique ID from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the card"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@Parameter(description = "ID of the card to be deleted") @PathVariable Long id) {
        try {
            cardService.deleteCardById(id);
            return ResponseEntity.status(204).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
