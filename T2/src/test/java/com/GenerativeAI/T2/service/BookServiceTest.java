package com.GenerativeAI.T2.service;

import com.GenerativeAI.T2.config.TestConfig;
import com.GenerativeAI.T2.dto.AuthorDTO;
import com.GenerativeAI.T2.dto.BookDTO;
import com.GenerativeAI.T2.dto.GenreDTO;
import com.GenerativeAI.T2.model.Author;
import com.GenerativeAI.T2.model.Book;
import com.GenerativeAI.T2.model.Genre;
import com.GenerativeAI.T2.repository.AuthorRepository;
import com.GenerativeAI.T2.repository.BookRepository;
import com.GenerativeAI.T2.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = TestConfig.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Book Title");
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Author Name");
        bookDTO.setAuthor(authorDTO);
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setName("Genre Name");
        bookDTO.setGenre(genreDTO);
        bookDTO.setPrice(BigDecimal.valueOf(10.00));
        bookDTO.setQuantity(10);

        Author existingAuthor = new Author();
        existingAuthor.setId(1L);
        existingAuthor.setName("Author Name");

        Genre existingGenre = new Genre();
        existingGenre.setId(1L);
        existingGenre.setName("Genre Name");

        // Mock repository methods
        when(authorRepository.findByName("Author Name")).thenReturn(existingAuthor);
        when(genreRepository.findByName("Genre Name")).thenReturn(existingGenre);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle("Book Title");
        savedBook.setAuthor(existingAuthor);
        savedBook.setGenre(existingGenre);
        savedBook.setPrice(BigDecimal.valueOf(10.00));
        savedBook.setQuantity(10);

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(savedBook);

        Book createdBook = bookService.createBook(bookDTO);

        assertNotNull(createdBook);
        assertEquals(existingAuthor.getId(), createdBook.getAuthor().getId());
        assertEquals(existingGenre.getId(), createdBook.getGenre().getId());
    }

    @Test
    public void testGetAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(createNewBook());
        books.add(createNewBook());

        //Mock repository method
        when(bookRepository.findAll()).thenReturn(books);

        ArrayList<Book> retrievedBooks = (ArrayList<Book>) bookService.getAllBooks();

        assertEquals(books.size(), retrievedBooks.size());
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book retrievedBook = bookService.getBookById(bookId);

        assertNotNull(retrievedBook);
        assertEquals(bookId, retrievedBook.getId());
    }

    @Test
    public void testUpdateBook() {
        Long bookId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Custom Title");

        Book existingBook = createNewBook();

        Author existingAuthor = new Author();
        existingAuthor.setId(1L);
        existingAuthor.setName("Author Name");

        Genre existingGenre = new Genre();
        existingGenre.setId(1L);
        existingGenre.setName("Genre Name");

        // Mock the repository methods
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(modelMapper.map(bookDTO, Book.class)).thenReturn(existingBook);
        when(authorRepository.findByName(existingBook.getAuthor().getName())).thenReturn(existingAuthor);
        when(genreRepository.findByName(existingBook.getGenre().getName())).thenReturn(existingGenre);
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        Book updatedBook = bookService.updateBook(bookId, bookDTO);

        assertNotNull(updatedBook);
        assertEquals(bookId, updatedBook.getId());
        // Add additional assertions for updated properties
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book deletedBook = bookService.deleteBook(bookId);

        assertNotNull(deletedBook);
        assertEquals(bookId, deletedBook.getId());
    }

    @Test
    public void testSearchBooks() {
        String searchTerm = "Genre Name";
        // Create and add books with matching search terms to a list
        ArrayList<Book> books = new ArrayList<>();
        books.add(createNewBook());

        when(bookRepository.findByTitleContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseOrGenreNameContainingIgnoreCase(searchTerm, searchTerm, searchTerm))
                .thenReturn(books);

        List<Book> retrievedBooks = bookService.searchBooks(searchTerm);

        assertNotNull(retrievedBooks);
        assertEquals(books.size(), retrievedBooks.size());
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
