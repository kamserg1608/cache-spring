package com.luxoft.springadvanced.caching.main;
 
import com.luxoft.springadvanced.caching.book.Book;
import com.luxoft.springadvanced.caching.book.BookDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	private static void pause(long timeout){
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeansConfiguration.class);
	    BookDao bookDao = (BookDao) context.getBean("bookDao");
	    
	    log.debug("Book : {}", bookDao.findByAuthor("Joshua Bloch"));
	    pause(2000);
		log.debug("Updated Book : {}", bookDao.save(new Book(0, "", "Joshua Bloch")));
	    log.debug("Book : {}", bookDao.findByAuthor("Joshua Bloch"));
		pause(2000);
	    log.debug("Book : {}", bookDao.findByAuthor("Joshua Bloch"));
		pause(2000);
		log.debug("Book : {}", bookDao.findByAuthor("Joshua Bloch"));
		pause(2000);
		log.debug("Book : {}", bookDao.findByAuthor("Joshua Bloch"));
	    
	    context.close();
	    
	}
}