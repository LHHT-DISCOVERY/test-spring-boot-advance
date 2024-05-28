package com.example.demo.service;

import java.util.Collection;
import java.util.Optional;

public interface IServiceCRUD<E,ERQ, ERP> {
    Collection<E> getList();

    E findById(String id);

    ERP createEntity(ERQ erq);

    String deleteById(String id);

    Optional<ERP> findByKeyword(String keyword);
}
