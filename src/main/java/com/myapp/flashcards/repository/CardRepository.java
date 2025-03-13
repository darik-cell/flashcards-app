package com.myapp.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myapp.flashcards.model.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
  List<Card> findAllByCollectionId(Long collectionId);
}
