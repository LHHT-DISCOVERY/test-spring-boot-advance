package com.example.demo.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface IService<T> {
    Collection<T> findAll();

    Optional<T> findById(int id);

    T saveOrUpdate(T t);

    String deleteById(int id);
}
