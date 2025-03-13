package com.myapp.flashcards.repository;

import com.myapp.flashcards.model.Collection;
import com.myapp.flashcards.model.QCard;
import com.myapp.flashcards.model.QCollection;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CollectionRepositoryImpl implements CollectionRepositoryCustom {

  @PersistenceContext
  private EntityManager em;

  private JPAQueryFactory queryFactory;

  @PostConstruct
  public void init() {
    queryFactory = new JPAQueryFactory(em);
  }

  private final QCollection collection = QCollection.collection;
  private final QCard card = QCard.card;

  @Override
  public List<Collection> findCollectionsByUserIdWithFetchMap(Long userId, Map<String, Boolean> fetchMap) {
    JPAQuery<Collection> query = new JPAQuery<>(em);

  }

  @Override
  public Optional<Collection> findCollectionByIdWithFetchMap(Long id) {
    return Optional.empty();
  }

  @Override
  public List<Tuple> fetchCardCountForCollectionIds(List<Long> collectionIds) {

    return queryFactory.select(collection.id, card.count())
            .from(collection)
            .leftJoin(collection.cards, card)
            .where(collection.id.in(collectionIds))
            .groupBy(collection.id)
            .fetch();
  }
}
