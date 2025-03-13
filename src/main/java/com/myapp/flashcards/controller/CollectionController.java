package com.myapp.flashcards.controller;

import com.myapp.flashcards.model.*;
import com.myapp.flashcards.model.Collection;
import com.myapp.flashcards.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

  @Autowired
  private CollectionRepository collectionRepository;

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/default")
  public List<Card> getDefaultCollection(@RequestParam(required = false) String date, Authentication authentication) {
    User user = userRepository.findByEmail(authentication.getName()).get();
    Collection defaultCollection = collectionRepository.findByNameAndUserId("Default", user.getId())
            .orElseThrow();
    List<Card> cards = cardRepository.findAllByCollectionId(defaultCollection.getId());

    if (date != null) {
      // Фильтрация по дате создания
      // Добавьте логику фильтрации по дате
    }

    return cards;
  }
}
