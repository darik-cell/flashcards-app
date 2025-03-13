package com.myapp.flashcards.graphql;

import com.myapp.flashcards.model.User;
import com.myapp.flashcards.service.UserService;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserResolver {

  private final UserService userService;

    @QueryMapping
    public List<User> getAll(DataFetchingEnvironment environment) {
        DataFetchingFieldSelectionSet selectionSet = environment.getSelectionSet();

        return userService.getAll(selectionSet);
    }

    @QueryMapping
    public User userById(DataFetchingEnvironment environment, @Argument("id") Long id) {
      DataFetchingFieldSelectionSet selectionSet = environment.getSelectionSet();

      return userService.getById(selectionSet, id);
    }
}
