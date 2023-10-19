package com.GenerativeAI.T2.service;

import com.GenerativeAI.T2.dto.BookDTO;
import com.GenerativeAI.T2.exception.BookNotFoundException;
import com.GenerativeAI.T2.model.Author;
import com.GenerativeAI.T2.model.Book;
import com.GenerativeAI.T2.model.Genre;
import com.GenerativeAI.T2.repository.AuthorRepository;
import com.GenerativeAI.T2.repository.BookRepository;
import com.GenerativeAI.T2.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public Book createBook(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        checkBookForExistingAuthor(book);
        checkBookForExistingGenre(book);
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
    }

    public Book updateBook(Long id, BookDTO bookDTO) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book updatedBook = modelMapper.map(bookDTO, Book.class);
            checkBookForExistingAuthor(updatedBook);
            checkBookForExistingGenre(updatedBook);
            Book existingBook = book.get();
            modelMapper.map(updatedBook, existingBook);
            existingBook.setId(id);
            existingBook = bookRepository.save(existingBook);
            return existingBook;
        } else {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
    }

    public Book deleteBook(Long id) {
        Book book = getBookById(id);
        if(book != null) {
            bookRepository.deleteById(id);
            return book;
        }
        throw new BookNotFoundException("Book not found with ID: " + id);
    }

    public List<Book> searchBooks(String searchTerm) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseOrGenreNameContainingIgnoreCase(searchTerm, searchTerm, searchTerm)
                .stream()
                .distinct()
                .toList();
        if(!books.isEmpty()) {
            return books;
        }
        throw new BookNotFoundException("No books found with search term: " + searchTerm);
    }

    private void checkBookForExistingAuthor(Book book) {
        Author author = authorRepository.findByName(book.getAuthor().getName());
        if (author != null) {
            book.setAuthor(author);
        } else {
            Author newAuthor = new Author();
            newAuthor.setName(book.getAuthor().getName());
            book.setAuthor(newAuthor);
        }
    }

    private void checkBookForExistingGenre(Book book) {
        Genre genre = genreRepository.findByName(book.getGenre().getName());
        if (genre != null) {
            book.setGenre(genre);
        } else {
            Genre newGenre = new Genre();
            newGenre.setName(book.getGenre().getName());
            book.setGenre(newGenre);
        }
    }
}
