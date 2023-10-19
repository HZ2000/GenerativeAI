package com.GenerativeAI.T2.service;

import com.GenerativeAI.T2.dto.AuthorDTO;
import com.GenerativeAI.T2.exception.AuthorNotFoundException;
import com.GenerativeAI.T2.model.Author;
import com.GenerativeAI.T2.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public Author createAuthor(AuthorDTO authorDTO) {
        Author author = modelMapper.map(authorDTO, Author.class);
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElseThrow(() -> new AuthorNotFoundException("Author not found with ID: " + id));
    }

    public Author updateAuthor(Long id, AuthorDTO updatedAuthorDTO) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            Author existingAuthor = author.get();
            modelMapper.map(updatedAuthorDTO, existingAuthor);
            existingAuthor.setId(id);
            existingAuthor = authorRepository.save(existingAuthor);
            return existingAuthor;
        } else {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }
    }

    public Author deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        if(author != null) {
            authorRepository.deleteById(id);
            return author;
        }
        throw new AuthorNotFoundException("Author not found with ID: " + id);

    }
}
