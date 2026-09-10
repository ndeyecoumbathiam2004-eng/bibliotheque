package com.example.repository;

import java.util.List;

public interface Repository<T> {

    void save(T objet);

    T findById(Long id);

    List<T> findAll();
}