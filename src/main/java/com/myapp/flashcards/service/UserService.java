package com.myapp.flashcards.service;

import com.myapp.flashcards.model.User;
import com.myapp.flashcards.repository.UserRepository;
import graphql.schema.DataFetchingFieldSelectionSet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public List<User> getAll(DataFetchingFieldSelectionSet selectionSet) {
    Map<String, Boolean> fetchMap = fetchMap(selectionSet);
    return userRepository.findAllWithFetchMap(fetchMap);
  }

  @Transactional
  public User getById(DataFetchingFieldSelectionSet selectionSet, Long id) {
    Map<String, Boolean> fetchMap = fetchMap(selectionSet);
    return userRepository.findByIdWithFetchMap(id, fetchMap).orElseThrow();
  }

  private Map<String, Boolean> fetchMap(DataFetchingFieldSelectionSet selectionSet) {

    Map<String, Boolean> fetchMap = new HashMap<>();
    if (selectionSet.contains("collections")) fetchMap.put("collections", true);
    return fetchMap;
  }

}
