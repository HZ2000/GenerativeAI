package com.GenerativeAI.T2.repository;

import com.GenerativeAI.T2.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseOrGenreNameContainingIgnoreCase(String title, String authorName, String genreName);
}
