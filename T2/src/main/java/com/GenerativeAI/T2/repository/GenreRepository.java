package com.GenerativeAI.T2.repository;

import com.GenerativeAI.T2.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findByName(String name);
}
