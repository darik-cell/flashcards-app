package com.myapp.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myapp.flashcards.model.Collection;

import java.util.Optional;
import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
  Optional<Collection> findByNameAndUserId(String name, Long userId);
  List<Collection> findAllByUserId(Long userId);
}
