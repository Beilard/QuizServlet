package ua.quiz.model.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CrudDao<T, ID> {
    void save(T item);

    Optional<T> findById(ID id);

    List<T> findAll(Long startFrom, Long rowCount);

    Long countEntries();

    void update(T item);

}
