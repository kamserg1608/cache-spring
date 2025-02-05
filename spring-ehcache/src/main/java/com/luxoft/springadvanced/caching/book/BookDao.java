package com.luxoft.springadvanced.caching.book;
public interface BookDao {

	Book findByAuthor(String author);

	Book save(Book book);

}
