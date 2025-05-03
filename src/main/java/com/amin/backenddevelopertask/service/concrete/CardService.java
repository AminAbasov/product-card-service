package com.amin.backenddevelopertask.service.concrete;

import com.amin.backenddevelopertask.dto.request.CreateCardRequest;
import com.amin.backenddevelopertask.dto.response.CardDto;
import com.amin.backenddevelopertask.dto.response.DtoConverter;
import com.amin.backenddevelopertask.entity.Card;
import com.amin.backenddevelopertask.repository.CardRepository;
import com.amin.backenddevelopertask.service.interfaces.CardServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService implements CardServiceInterface {

    private final CardRepository cardRepository;

    private final DtoConverter  dtoConverter;

    public CardService(CardRepository cardRepository, DtoConverter dtoConverter) {
        this.cardRepository = cardRepository;
        this.dtoConverter = dtoConverter;
    }

    public CardDto createCard(CreateCardRequest createCardRequest) {
        Card card = new Card();
        card.setCardNumber(createCardRequest.getCardNumber());
        card.setExpireDate(createCardRequest.getExpireDate());
        card.setCvv(createCardRequest.getCvv());
        Card savedCard = cardRepository.save(card);
        return dtoConverter.convertToCardDto(savedCard);
    }

    public List<CardDto> getAllCards() {
        return cardRepository.findAll().stream()
                .map(dtoConverter::convertToCardDto)
                .collect(Collectors.toList());
    }

    public CardDto getCardById(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        return dtoConverter.convertToCardDto(card);
    }

    public CardDto updateCard(Long id, CreateCardRequest createCardRequest) {
        Card existingCard = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        existingCard.setCardNumber(createCardRequest.getCardNumber());
        existingCard.setExpireDate(createCardRequest.getExpireDate());
        existingCard.setCvv(createCardRequest.getCvv());
        Card updatedCard = cardRepository.save(existingCard);
        return dtoConverter.convertToCardDto(updatedCard);
    }

    public void deleteCardById(Long id) {
        Card existingCard = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        cardRepository.delete(existingCard);
    }

}
