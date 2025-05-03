package com.amin.backenddevelopertask.service.interfaces;

import com.amin.backenddevelopertask.dto.request.CreateCardRequest;
import com.amin.backenddevelopertask.dto.response.CardDto;

import java.util.List;

public interface CardServiceInterface {

    CardDto createCard(CreateCardRequest createCardRequest);

    List<CardDto> getAllCards();

    CardDto getCardById(Long id);

    CardDto updateCard(Long id, CreateCardRequest createCardRequest);

    void deleteCardById(Long id);
}

