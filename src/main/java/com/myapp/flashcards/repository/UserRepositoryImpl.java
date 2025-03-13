package com.myapp.flashcards.repository;

import com.myapp.flashcards.model.QCollection;
import com.myapp.flashcards.model.QUser;
import com.myapp.flashcards.model.User;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

  @PersistenceContext
  private final EntityManager entityManager;

  private final QUser user = QUser.user;
  private final QCollection collection = QCollection.collection;

  @Override
  public List<User> findAllWithFetchMap(Map<String, Boolean> fetchMap) {
    JPAQuery<User> query = new JPAQuery<>(entityManager);
    query.select(user).from(user);

    addRelations(query, fetchMap);

    return query.fetch();
  }

  @Override
  public Optional<User> findByIdWithFetchMap(Long id, Map<String, Boolean> fetchMap) {
    JPAQuery<User> query = new JPAQuery<>(entityManager);
    query.select(user).from(user);
    addRelations(query, fetchMap);
    return Optional.ofNullable(query.fetchOne());
  }

  private void addRelations(JPAQuery<User> query, Map<String, Boolean> fetchMap) {
    if (fetchMap.getOrDefault("collections", false)) {
      query.leftJoin(user.collections, collection).fetchJoin();
    }
  }
}
