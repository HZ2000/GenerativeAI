package com.GenerativeAI.T2.service;

import com.GenerativeAI.T2.config.TestConfig;
import com.GenerativeAI.T2.dto.AuthorDTO;
import com.GenerativeAI.T2.exception.AuthorNotFoundException;
import com.GenerativeAI.T2.model.Author;
import com.GenerativeAI.T2.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = TestConfig.class)
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAuthor() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Author Name");

        Author author = new Author();
        author.setId(1L);
        author.setName("Author Name");

        when(modelMapper.map(authorDTO, Author.class)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);

        Author createdAuthor = authorService.createAuthor(authorDTO);

        assertNotNull(createdAuthor);
        assertEquals(author.getName(), createdAuthor.getName());
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(createNewAuthor());
        authors.add(createNewAuthor());

        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> retrievedAuthors = authorService.getAllAuthors();

        assertEquals(authors.size(), retrievedAuthors.size());
    }

    @Test
    public void testGetAuthorById() {
        Long authorId = 1L;
        Author author = createNewAuthor();
        author.setId(authorId);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Author retrievedAuthor = authorService.getAuthorById(authorId);

        assertNotNull(retrievedAuthor);
        assertEquals(authorId, retrievedAuthor.getId());
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorById(authorId));
    }

    @Test
    public void testUpdateAuthor() {
        Long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Updated Author Name");

        Author existingAuthor = createNewAuthor();
        existingAuthor.setId(authorId);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(modelMapper.map(authorDTO, Author.class)).thenReturn(existingAuthor);
        when(authorRepository.save(existingAuthor)).thenReturn(existingAuthor);

        Author updatedAuthor = authorService.updateAuthor(authorId, authorDTO);

        assertNotNull(updatedAuthor);
        assertEquals(authorId, updatedAuthor.getId());
        assertEquals("Updated Author Name", updatedAuthor.getName());
    }

    @Test
    public void testUpdateAuthorNotFound() {
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        AuthorDTO authorDTO = new AuthorDTO();

        assertThrows(AuthorNotFoundException.class, () -> authorService.updateAuthor(authorId, authorDTO));
    }

    @Test
    public void testDeleteAuthor() {
        Long authorId = 1L;
        Author author = createNewAuthor();
        author.setId(authorId);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Author deletedAuthor = authorService.deleteAuthor(authorId);

        assertNotNull(deletedAuthor);
        assertEquals(authorId, deletedAuthor.getId());
    }

    private Author createNewAuthor() {
        Author author = new Author();
        author.setName("Author Name");

        return author;
    }
}
