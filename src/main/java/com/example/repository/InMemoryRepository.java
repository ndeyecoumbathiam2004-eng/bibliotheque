package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InMemoryRepository<T> implements Repository<T> {

    private final List<T> elements = new ArrayList<>();
    private final Function<T, Long> idExtractor;

    public InMemoryRepository(Function<T, Long> idExtractor) {
        this.idExtractor = idExtractor;
    }

    @Override
    public void save(T objet) {
        elements.add(objet);
    }

    @Override
    public T findById(Long id) {
        for (T element : elements) {
            if (idExtractor.apply(element).equals(id)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        return elements;
    }
}
