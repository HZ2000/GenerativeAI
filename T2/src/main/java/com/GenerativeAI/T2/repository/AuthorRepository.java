package com.GenerativeAI.T2.repository;

import com.GenerativeAI.T2.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Author findByName(String name);
}
