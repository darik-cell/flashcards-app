package com.myapp.flashcards.controller;

import com.myapp.flashcards.model.*;
import com.myapp.flashcards.model.Collection;
import com.myapp.flashcards.repository.*;
import com.myapp.flashcards.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private CollectionRepository collectionRepository;

  @Autowired
  private UserRepository userRepository;

  @PostMapping
  public Card createCard(@RequestBody Card card) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    User user = userDetails.getUser();

    Collection defaultCollection = collectionRepository.findByNameAndUserId("Default", user.getId())
            .orElseGet(() -> {
              Collection col = new Collection();
              col.setName("Default");
              col.setUser(user);
              return collectionRepository.save(col);
            });
    card.setCollection(defaultCollection);
    return cardRepository.save(card);
  }

  @GetMapping("/{id}")
  public Card getCard(@PathVariable Long id) {
    return cardRepository.findById(id).orElseThrow();
  }

  @PutMapping("/{id}")
  public Card updateCard(@PathVariable Long id, @RequestBody Card cardDetails) {
    Card card = cardRepository.findById(id).orElseThrow();
    card.setQuestion(cardDetails.getQuestion());
    card.setAnswer(cardDetails.getAnswer());
    return cardRepository.save(card);
  }

  @DeleteMapping("/{id}")
  public String deleteCard(@PathVariable Long id) {
    cardRepository.deleteById(id);
    return "Card deleted";
  }
}
