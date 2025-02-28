package com.trimv.crudgeneric.service;

import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface CRUDService<T, ID> {

    List<T> getAll();
    Page<T> getAll(Pageable pageable);
    Optional<T> getById(ID id);
    T create(T entity);
    T update(ID id, T entity);
    void delete(ID id);
    boolean existsById(ID id);
}
