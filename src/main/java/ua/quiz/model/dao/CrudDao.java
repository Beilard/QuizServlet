package ua.quiz.model.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CrudDao<T, ID> {
    void save(T item);

    Optional<T> findById(ID id);

    List<T> findAll();

    void update(T item);

    default void deleteById(ID id) {
        throw new UnsupportedOperationException("Deletion not allowed");
    }

    default void deleteAllById(Set<ID> ids) {
        throw new UnsupportedOperationException("Deletion not allowed");
    }
}
