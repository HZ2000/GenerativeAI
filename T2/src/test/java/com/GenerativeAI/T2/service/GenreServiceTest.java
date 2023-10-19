package com.GenerativeAI.T2.service;

import com.GenerativeAI.T2.config.TestConfig;
import com.GenerativeAI.T2.dto.GenreDTO;
import com.GenerativeAI.T2.exception.GenreNotFoundException;
import com.GenerativeAI.T2.model.Genre;
import com.GenerativeAI.T2.repository.GenreRepository;
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
public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private GenreService genreService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGenre() {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setName("Genre Name");

        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Genre Name");

        when(modelMapper.map(genreDTO, Genre.class)).thenReturn(genre);
        when(genreRepository.save(genre)).thenReturn(genre);

        Genre createdGenre = genreService.createGenre(genreDTO);

        assertNotNull(createdGenre);
        assertEquals(genre.getName(), createdGenre.getName());
    }

    @Test
    public void testGetAllGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(createNewGenre());
        genres.add(createNewGenre());

        when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> retrievedGenres = genreService.getAllGenres();

        assertEquals(genres.size(), retrievedGenres.size());
    }

    @Test
    public void testGetGenreById() {
        Long genreId = 1L;
        Genre genre = createNewGenre();
        genre.setId(genreId);

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));

        Genre retrievedGenre = genreService.getGenreById(genreId);

        assertNotNull(retrievedGenre);
        assertEquals(genreId, retrievedGenre.getId());
    }

    @Test
    public void testGetGenreByIdNotFound() {
        Long genreId = 1L;
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        assertThrows(GenreNotFoundException.class, () -> genreService.getGenreById(genreId));
    }

    @Test
    public void testUpdateGenre() {
        Long genreId = 1L;
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setName("Updated Genre Name");

        Genre existingGenre = createNewGenre();
        existingGenre.setId(genreId);

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(existingGenre));
        when(modelMapper.map(genreDTO, Genre.class)).thenReturn(existingGenre);
        when(genreRepository.save(existingGenre)).thenReturn(existingGenre);

        Genre updatedGenre = genreService.updateGenre(genreId, genreDTO);

        assertNotNull(updatedGenre);
        assertEquals(genreId, updatedGenre.getId());
        assertEquals("Updated Genre Name", updatedGenre.getName());
    }

    @Test
    public void testUpdateGenreNotFound() {
        Long genreId = 1L;
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        GenreDTO genreDTO = new GenreDTO();

        assertThrows(GenreNotFoundException.class, () -> genreService.updateGenre(genreId, genreDTO));
    }

    @Test
    public void testDeleteGenre() {
        Long genreId = 1L;
        Genre genre = createNewGenre();
        genre.setId(genreId);

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));

        Genre deletedGenre = genreService.deleteGenre(genreId);

        assertNotNull(deletedGenre);
        assertEquals(genreId, deletedGenre.getId());
    }

    private Genre createNewGenre() {
        Genre genre = new Genre();
        genre.setName("Genre Name");

        return genre;
    }
}
