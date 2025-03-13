package com.myapp.flashcards.config;

import com.myapp.flashcards.repository.CollectionRepository;
import com.querydsl.core.Tuple;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Configuration
public class GraphQLConfig {

   @Bean
    public DataLoaderRegistry dataLoaderRegistry(CollectionRepository collectionRepository) {
        DataLoaderRegistry registry = new DataLoaderRegistry();
        DataLoader<Long, Integer> collectionCardCountDataLoader = DataLoaderFactory.newMappedDataLoader(keys ->
            CompletableFuture.supplyAsync(() -> {
                // Выполняем один запрос для получения количества карточек для всех коллекций
                // Например, с использованием QueryDSL в CollectionRepository:
                List<Tuple> results = collectionRepository.fetchCardCountForCollectionIds(new ArrayList<>(keys));
                // Преобразуем результаты в Map: ключ - id коллекции, значение - количество карточек
                Map<Long, Integer> countMap = results.stream().collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> tuple.get(1, Integer.class)
                ));
                // Для коллекций, для которых результата нет, возвращаем 0
                keys.forEach(id -> countMap.putIfAbsent(id, 0));
                return countMap;
            })
        );
        registry.register("collectionCardCount", collectionCardCountDataLoader);
        return registry;
    }
}
