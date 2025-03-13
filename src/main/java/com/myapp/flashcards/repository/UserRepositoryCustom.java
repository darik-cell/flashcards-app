package com.myapp.flashcards.repository;

import com.myapp.flashcards.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepositoryCustom {
  List<User> findAllWithFetchMap(Map<String, Boolean> fetchMap);
  Optional<User> findByIdWithFetchMap(Long id, Map<String, Boolean> fetchMap);
}
