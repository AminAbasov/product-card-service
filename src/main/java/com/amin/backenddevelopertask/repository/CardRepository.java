package com.amin.backenddevelopertask.repository;

import com.amin.backenddevelopertask.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
