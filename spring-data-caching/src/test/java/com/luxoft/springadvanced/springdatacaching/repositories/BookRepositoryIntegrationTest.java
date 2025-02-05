package com.luxoft.springadvanced.springdatacaching.repositories;

import com.luxoft.springadvanced.springdatacaching.CacheApplication;
import com.luxoft.springadvanced.springdatacaching.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@EntityScan(basePackageClasses = Book.class)
@SpringBootTest(classes = CacheApplication.class)
@EnableJpaRepositories(basePackageClasses = BookRepository.class)
public class BookRepositoryIntegrationTest {

    @Autowired
    CacheManager cacheManager;

    @Qualifier("bookRepository")
    @Autowired
    BookRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Book(1, "Dune"));
        repository.save(new Book(2, "Foundation"));
    }

    @Test
    @DisplayName("Given a book that should be cached, when we find it by title, then the result should be put in cache")
    void testBookToBeCached() {
        Optional<Book> dune = repository.findFirstByTitle("Dune");

        assertEquals(dune, getCachedBook("Dune"));
    }

    @Test
    @DisplayName("Cache")
    void testBookToBeEvictedFromCache() throws InterruptedException {
        Optional<Book> dune = repository.findFirstByTitle("Dune");

        Thread.sleep(3500);
        assertEquals(empty(), getCachedBook("Dune"));
    }

    @Test
    @DisplayName("Given a book that should not be cached, when we find it by title, then the result should not be put in cache")
    void testBookNotToBeCached() {
        repository.findFirstByTitle("Foundation");

        assertEquals(empty(), getCachedBook("Foundation"));
    }

    private Optional<Book> getCachedBook(String title) {
        return ofNullable(cacheManager.getCache("books"))
                .map(c -> c.get(title, Book.class));
    }

}
