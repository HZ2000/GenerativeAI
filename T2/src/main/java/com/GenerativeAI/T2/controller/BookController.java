package com.GenerativeAI.T2.controller;

import com.GenerativeAI.T2.dto.BookDTO;
import com.GenerativeAI.T2.exception.BookNotFoundException;
import com.GenerativeAI.T2.model.Book;
import com.GenerativeAI.T2.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDTO bookDTO) {
        Book addedBook = bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable @Min(1) Long id) {
        try {
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable @Min(1) Long id, @RequestBody @Valid BookDTO updatedBookDto) {
        try {
            Book updatedBook = bookService.updateBook(id, updatedBookDto);
            return ResponseEntity.ok(updatedBook);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable @Min(1) Long id) {
        try {
            Book deletedBook = bookService.deleteBook(id);
            return ResponseEntity.ok(deletedBook);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestParam("query") String query) {
        try {
            List<Book> books = bookService.searchBooks(query);
            return ResponseEntity.ok(books);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
