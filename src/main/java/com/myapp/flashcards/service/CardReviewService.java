package com.myapp.flashcards.service;

import com.myapp.flashcards.model.Card;
import com.myapp.flashcards.model.CardReviewHistory;
import com.myapp.flashcards.repository.CardRepository;
import com.myapp.flashcards.repository.CardReviewHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class CardReviewService {

  private final CardRepository cardRepository;
  private final CardReviewHistoryRepository reviewHistoryRepository;

  public CardReviewService(CardRepository cardRepository, CardReviewHistoryRepository reviewHistoryRepository) {
    this.cardRepository = cardRepository;
    this.reviewHistoryRepository = reviewHistoryRepository;
  }

  @Transactional
  public CardReviewHistory reviewCard(Long cardId, int quality) {
    Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new IllegalArgumentException("Карточка не найдена с id: " + cardId));

    CardReviewHistory latestReview = reviewHistoryRepository.findLatestByCard(card).orElse(null);

    int repetitionCount = (latestReview != null) ? latestReview.getRepetitionCount() : 0;
    int intervalDays = (latestReview != null) ? latestReview.getIntervalDays() : 0;
    double easeFactor = (latestReview != null) ? latestReview.getEaseFactor() : 2.5;

    LocalDateTime now = LocalDateTime.now();

    if (quality < 3) {
      repetitionCount = 0;
      intervalDays = 1;
    } else {
      repetitionCount++;
      if (repetitionCount == 1) {
        intervalDays = 1;
      } else if (repetitionCount == 2) {
        intervalDays = 6;
      } else {
        intervalDays = (int) Math.round(intervalDays * easeFactor);
      }
      easeFactor = easeFactor + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02));
      if (easeFactor < 1.3) {
        easeFactor = 1.3;
      }
    }

    LocalDateTime nextReview = now.plus(intervalDays, ChronoUnit.DAYS);

    CardReviewHistory newReview = new CardReviewHistory();
    newReview.setCard(card);
    newReview.setReviewDate(now);
    newReview.setQuality(quality);
    newReview.setRepetitionCount(repetitionCount);
    newReview.setIntervalDays(intervalDays);
    newReview.setEaseFactor(easeFactor);
    newReview.setNextReview(nextReview);

    return reviewHistoryRepository.save(newReview);
  }
}
