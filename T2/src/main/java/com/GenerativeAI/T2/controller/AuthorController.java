package com.GenerativeAI.T2.controller;

import com.GenerativeAI.T2.dto.AuthorDTO;
import com.GenerativeAI.T2.exception.AuthorNotFoundException;
import com.GenerativeAI.T2.model.Author;
import com.GenerativeAI.T2.service.AuthorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Author addedAuthor = authorService.createAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAuthor);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable @Min(1) Long id) {
        try {
            Author author = authorService.getAuthorById(id);
            return ResponseEntity.ok(author);
        } catch (AuthorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable @Min(1) Long id, @RequestBody @Valid AuthorDTO updatedAuthorDto) {
        try {
            Author updatedAuthor = authorService.updateAuthor(id, updatedAuthorDto);
            return ResponseEntity.ok(updatedAuthor);
        } catch (AuthorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable @Min(1) Long id) {
        try {
            Author deletedAuthor = authorService.deleteAuthor(id);
            return ResponseEntity.ok(deletedAuthor);
        } catch (AuthorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
