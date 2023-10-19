package com.GenerativeAI.T2.service;

import com.GenerativeAI.T2.dto.GenreDTO;
import com.GenerativeAI.T2.exception.GenreNotFoundException;
import com.GenerativeAI.T2.model.Genre;
import com.GenerativeAI.T2.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public Genre createGenre(GenreDTO genreDTO) {
        Genre genre = modelMapper.map(genreDTO, Genre.class);
        return genreRepository.save(genre);
    }

    public List<Genre> getAllGenres() {
        return (List<Genre>) genreRepository.findAll();
    }

    public Genre getGenreById(Long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.orElseThrow(() -> new GenreNotFoundException("Genre not found with ID: " + id));
    }

    public Genre updateGenre(Long id, GenreDTO updatedGenreDTO) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            Genre existingGenre = genre.get();
            modelMapper.map(updatedGenreDTO, existingGenre);
            existingGenre.setId(id);
            return genreRepository.save(existingGenre);
        } else {
            throw new GenreNotFoundException("Genre not found with ID: " + id);
        }
    }

    public Genre deleteGenre(Long id) {
        Genre genre = getGenreById(id);
        genreRepository.deleteById(id);
        return genre;
    }
}