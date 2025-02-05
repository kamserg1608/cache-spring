package com.luxoft.springadvanced.springdatacaching.model;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Book {

    @Id
    private int id;
    private String title;

    public Book() {

    }

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
