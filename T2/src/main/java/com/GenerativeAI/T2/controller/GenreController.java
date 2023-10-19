package com.GenerativeAI.T2.controller;

import com.GenerativeAI.T2.dto.GenreDTO;
import com.GenerativeAI.T2.exception.GenreNotFoundException;
import com.GenerativeAI.T2.model.Genre;
import com.GenerativeAI.T2.service.GenreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody @Valid GenreDTO genreDTO) {
        Genre addedGenre = genreService.createGenre(genreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedGenre);
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGenreById(@PathVariable @Min(1) Long id) {
        try {
            Genre genre = genreService.getGenreById(id);
            return ResponseEntity.ok(genre);
        } catch (GenreNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenre(@PathVariable @Min(1) Long id, @RequestBody @Valid GenreDTO updatedGenreDto) {
        try {
            Genre updatedGenre = genreService.updateGenre(id, updatedGenreDto);
            return ResponseEntity.ok(updatedGenre);
        } catch (GenreNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable @Min(1) Long id) {
        try {
            Genre deletedGenre = genreService.deleteGenre(id);
            return ResponseEntity.ok(deletedGenre);
        } catch (GenreNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
