package com.myapp.flashcards.repository;

import com.myapp.flashcards.model.Collection;
import com.myapp.flashcards.model.User;
import com.querydsl.core.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CollectionRepositoryCustom {
  List<Collection> findCollectionsByUserIdWithFetchMap(Long userId, Map<String, Boolean> fetchMap);
  Optional<Collection> findCollectionByIdWithFetchMap(Long id);
  List<Tuple> fetchCardCountForCollectionIds(List<Long> collectionIds);
}
