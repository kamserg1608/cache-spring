package com.luxoft.springadvanced.caching.book;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository("bookDao")
public class BookDaoImpl implements BookDao {

	@Cacheable(value="booksCache1", key="#author")
	public Book findByAuthor(String author) {
		System.out.println("Executing findByAuthor");
		return new Book(1,"Effective Java","Joshua Bloch");
	}

	@CachePut(value="booksCache1", key="#book.author")
	public Book save(Book book) {
		System.out.println("Executing save");
		return new Book(1,"Effective Java(upd)","Joshua Bloch");
	}
	
	private void pause(long timeout){
		try {
            Thread.sleep(timeout);
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
	}
	
}