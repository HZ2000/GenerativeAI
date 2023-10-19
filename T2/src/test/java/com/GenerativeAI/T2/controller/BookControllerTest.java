package com.GenerativeAI.T2.controller;

import com.GenerativeAI.T2.dto.AuthorDTO;
import com.GenerativeAI.T2.dto.BookDTO;
import com.GenerativeAI.T2.dto.GenreDTO;
import com.GenerativeAI.T2.exception.BookNotFoundException;
import com.GenerativeAI.T2.model.Author;
import com.GenerativeAI.T2.model.Book;
import com.GenerativeAI.T2.model.Genre;
import com.GenerativeAI.T2.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateBook() {
        BookDTO bookDTO = new BookDTO();
        // Set properties for bookDTO
        bookDTO.setTitle("Book Title");
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Author Name");
        bookDTO.setAuthor(authorDTO);
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setName("Genre Name");
        bookDTO.setGenre(genreDTO);
        bookDTO.setPrice(BigDecimal.valueOf(10.00));
        bookDTO.setQuantity(10);

        Book createdBook = createNewBook();

        when(bookService.createBook(bookDTO)).thenReturn(createdBook);

        ResponseEntity<Book> response = bookController.createBook(bookDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdBook, response.getBody());
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(createNewBook());
        books.add(createNewBook());

        when(bookService.getAllBooks()).thenReturn(books);

        List<Book> retrievedBooks = bookController.getAllBooks();

        assertNotNull(retrievedBooks);
        assertEquals(books.size(), retrievedBooks.size());
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        Book book = createNewBook();
        book.setId(bookId);

        when(bookService.getBookById(bookId)).thenReturn(book);

        ResponseEntity<?> response = bookController.getBookById(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetBookByIdNotFound() {
        Long bookId = 1L;

        when(bookService.getBookById(bookId)).thenThrow(BookNotFoundException.class);

        ResponseEntity<?> response = bookController.getBookById(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateBook() {
        Long bookId = 1L;
        BookDTO updatedBookDto = new BookDTO();
        updatedBookDto.setTitle("Updated Book Title");
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Updated Author Name");
        updatedBookDto.setAuthor(authorDTO);
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setName("Updated Genre Name");
        updatedBookDto.setGenre(genreDTO);
        updatedBookDto.setPrice(BigDecimal.valueOf(20.00));
        updatedBookDto.setQuantity(20);

        Book updatedBook = createNewBook();

        when(bookService.updateBook(bookId, updatedBookDto)).thenReturn(updatedBook);

        ResponseEntity<?> response = bookController.updateBook(bookId, updatedBookDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateBookNotFound() {
        Long bookId = 1L;
        BookDTO updatedBookDto = new BookDTO();

        when(bookService.updateBook(bookId, updatedBookDto)).thenThrow(BookNotFoundException.class);

        ResponseEntity<?> response = bookController.updateBook(bookId, updatedBookDto);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        Book book = createNewBook();
        book.setId(bookId);

        when(bookService.deleteBook(bookId)).thenReturn(book);

        ResponseEntity<?> response = bookController.deleteBook(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteBookNotFound() {
        Long bookId = 1L;

        when(bookService.deleteBook(bookId)).thenThrow(BookNotFoundException.class);

        ResponseEntity<?> response = bookController.deleteBook(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchBooks() {
        String query = "Book Title";
        List<Book> books = new ArrayList<>();
        books.add(createNewBook());

        when(bookService.searchBooks(query)).thenReturn(books);

        ResponseEntity<?> response = bookController.searchBooks(query);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testSearchBooksNotFound() {
        String query = "Non-Existent Title";

        when(bookService.searchBooks(query)).thenThrow(BookNotFoundException.class);

        ResponseEntity<?> response = bookController.searchBooks(query);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private Book createNewBook() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Author Name");

        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Genre Name");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPrice(BigDecimal.valueOf(10.00));
        book.setQuantity(10);

        return book;
    }
}
