package com.myapp.flashcards.service;

import com.myapp.flashcards.model.Collection;
import com.myapp.flashcards.repository.CollectionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionService {

  private final CollectionRepository collectionRepository;

  @Transactional
  public List<Collection> getAll() {
    return collectionRepository.findAll();
  }
}
