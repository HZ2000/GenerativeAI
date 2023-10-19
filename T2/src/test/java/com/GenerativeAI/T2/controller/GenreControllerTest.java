package com.GenerativeAI.T2.controller;

import com.GenerativeAI.T2.dto.GenreDTO;
import com.GenerativeAI.T2.exception.GenreNotFoundException;
import com.GenerativeAI.T2.model.Genre;
import com.GenerativeAI.T2.service.GenreService;
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

public class GenreControllerTest {

    @Mock
    private GenreService genreService;

    @InjectMocks
    private GenreController genreController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGenre() {
        GenreDTO genreDTO = new GenreDTO();
        // Set properties for genreDTO
        genreDTO.setName("Genre Name");

        Genre createdGenre = createNewGenre();

        when(genreService.createGenre(genreDTO)).thenReturn(createdGenre);

        ResponseEntity<Genre> response = genreController.createGenre(genreDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdGenre, response.getBody());
    }

    @Test
    public void testGetAllGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(createNewGenre());
        genres.add(createNewGenre());

        when(genreService.getAllGenres()).thenReturn(genres);

        List<Genre> retrievedGenres = genreController.getAllGenres();

        assertNotNull(retrievedGenres);
        assertEquals(genres.size(), retrievedGenres.size());
    }

    @Test
    public void testGetGenreById() {
        Long genreId = 1L;
        Genre genre = createNewGenre();
        genre.setId(genreId);

        when(genreService.getGenreById(genreId)).thenReturn(genre);

        ResponseEntity<?> response = genreController.getGenreById(genreId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetGenreByIdNotFound() {
        Long genreId = 1L;

        when(genreService.getGenreById(genreId)).thenThrow(GenreNotFoundException.class);

        ResponseEntity<?> response = genreController.getGenreById(genreId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateGenre() {
        Long genreId = 1L;
        GenreDTO updatedGenreDto = new GenreDTO();
        // Set properties for updatedGenreDto
        updatedGenreDto.setName("Updated Genre Name");

        Genre updatedGenre = createNewGenre();

        when(genreService.updateGenre(genreId, updatedGenreDto)).thenReturn(updatedGenre);

        ResponseEntity<?> response = genreController.updateGenre(genreId, updatedGenreDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateGenreNotFound() {
        Long genreId = 1L;
        GenreDTO updatedGenreDto = new GenreDTO();

        when(genreService.updateGenre(genreId, updatedGenreDto)).thenThrow(GenreNotFoundException.class);

        ResponseEntity<?> response = genreController.updateGenre(genreId, updatedGenreDto);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteGenre() {
        Long genreId = 1L;
        Genre genre = createNewGenre();
        genre.setId(genreId);

        when(genreService.deleteGenre(genreId)).thenReturn(genre);

        ResponseEntity<?> response = genreController.deleteGenre(genreId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteGenreNotFound() {
        Long genreId = 1L;

        when(genreService.deleteGenre(genreId)).thenThrow(GenreNotFoundException.class);

        ResponseEntity<?> response = genreController.deleteGenre(genreId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private Genre createNewGenre() {
        Genre genre = new Genre();
        genre.setName("Genre Name");
        return genre;
    }
}
