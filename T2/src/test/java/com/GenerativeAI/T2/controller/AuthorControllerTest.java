package com.GenerativeAI.T2.controller;

import com.GenerativeAI.T2.dto.AuthorDTO;
import com.GenerativeAI.T2.exception.AuthorNotFoundException;
import com.GenerativeAI.T2.model.Author;
import com.GenerativeAI.T2.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAuthor() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Author Name");

        Author createdAuthor = createNewAuthor();

        when(authorService.createAuthor(authorDTO)).thenReturn(createdAuthor);

        ResponseEntity<Author> response = authorController.createAuthor(authorDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdAuthor, response.getBody());
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(createNewAuthor());
        authors.add(createNewAuthor());

        when(authorService.getAllAuthors()).thenReturn(authors);

        List<Author> retrievedAuthors = authorController.getAllAuthors();

        assertNotNull(retrievedAuthors);
        assertEquals(authors.size(), retrievedAuthors.size());
    }

    @Test
    public void testGetAuthorById() {
        Long authorId = 1L;
        Author author = createNewAuthor();
        author.setId(authorId);

        when(authorService.getAuthorById(authorId)).thenReturn(author);

        ResponseEntity<?> response = authorController.getAuthorById(authorId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        Long authorId = 1L;

        when(authorService.getAuthorById(authorId)).thenThrow(AuthorNotFoundException.class);

        ResponseEntity<?> response = authorController.getAuthorById(authorId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateAuthor() {
        Long authorId = 1L;
        AuthorDTO updatedAuthorDto = new AuthorDTO();
        updatedAuthorDto.setName("Updated Author Name");

        Author updatedAuthor = createNewAuthor();

        when(authorService.updateAuthor(authorId, updatedAuthorDto)).thenReturn(updatedAuthor);

        ResponseEntity<?> response = authorController.updateAuthor(authorId, updatedAuthorDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateAuthorNotFound() {
        Long authorId = 1L;
        AuthorDTO updatedAuthorDto = new AuthorDTO();

        when(authorService.updateAuthor(authorId, updatedAuthorDto)).thenThrow(AuthorNotFoundException.class);

        ResponseEntity<?> response = authorController.updateAuthor(authorId, updatedAuthorDto);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteAuthor() {
        Long authorId = 1L;
        Author author = createNewAuthor();
        author.setId(authorId);

        when(authorService.deleteAuthor(authorId)).thenReturn(author);

        ResponseEntity<?> response = authorController.deleteAuthor(authorId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteAuthorNotFound() {
        Long authorId = 1L;

        when(authorService.deleteAuthor(authorId)).thenThrow(AuthorNotFoundException.class);

        ResponseEntity<?> response = authorController.deleteAuthor(authorId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private Author createNewAuthor() {
        Author author = new Author();
        author.setName("Author Name");
        return author;
    }
}
