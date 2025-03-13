package com.myapp.flashcards.graphql;

import com.myapp.flashcards.model.Collection;
import com.myapp.flashcards.service.CollectionService;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
public class CollectionResolver {

  private final CollectionService collectionService;

  @QueryMapping
  public List<Collection> allCollections() {
    return collectionService.getAll();
  }

  @SchemaMapping(typeName = "Collection", field = "countCards")
  public CompletableFuture<Integer> getCollectionCardCount(Collection collection, DataFetchingEnvironment env) {
    DataLoader<Long, Integer> dataLoader = env.getDataLoader("collectionCardCount");
    return dataLoader.load(collection.getId());
  }
}
