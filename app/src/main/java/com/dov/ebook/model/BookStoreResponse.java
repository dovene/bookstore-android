package com.dov.ebook.model;

import java.util.List;

public class BookStoreResponse {
    private List<Book> items;

    public List<Book> getBooks() {
        return items;
    }

    public void setBooks(List<Book> books) {
        this.items = books;
    }

    public class Book {
        private BookInfo volumeInfo;

        public BookInfo getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(BookInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }
    }
}
