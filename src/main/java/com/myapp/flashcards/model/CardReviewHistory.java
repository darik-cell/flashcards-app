package com.myapp.flashcards.model;

import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDateTime;

@Entity
@Table(name = "card_review_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardReviewHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Связь с карточкой
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id", nullable = false)
  private Card card;

  // Дата проведения повторения
  @Column(name = "review_date", nullable = false)
  private LocalDateTime reviewDate;

  // Оценка качества ответа (например, по шкале SM‑2 от 0 до 5)
  @Column(name = "quality", nullable = false)
  private int quality;

  // Опционально: можно сохранить обновлённые параметры карточки после повторения
  @Column(name = "repetition_count")
  private int repetitionCount;

  @Column(name = "interval_days")
  private int intervalDays;

  @Column(name = "ease_factor")
  private double easeFactor;

  // Дата следующего повторения
  @Column(name = "next_review")
  private LocalDateTime nextReview;
}
