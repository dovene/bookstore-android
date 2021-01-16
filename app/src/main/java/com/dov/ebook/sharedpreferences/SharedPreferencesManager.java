package com.dov.ebook.sharedpreferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.dov.ebook.model.BookStoreResponse;
import com.dov.ebook.repository.BooksRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreferencesManager {

    public static final String MY_PREF = "AppPreferences";
    private static SharedPreferencesManager INSTANCE = null;
    private SharedPreferences sharedPreferences;
    public static String LAST_AUTHOR_KEY = "LAST_AUTHOR_KEY";
    public static String LAST_TITLE_KEY = "LAST_TITLE_KEY";
    public static String MY_BOOKS = "MY_BOOKS";

    private SharedPreferencesManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(MY_PREF, 0);
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if(INSTANCE == null){
            INSTANCE = new SharedPreferencesManager(context);
        }
        return INSTANCE;
    }

    public void save(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String get(String key) {
        return this.sharedPreferences.getString(key, null);
    }

    public void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    public void saveBooks(List<BookStoreResponse.Book> books) {
        Gson gson = new Gson();
        String booksAsString = gson.toJson(books);
        save(MY_BOOKS,booksAsString);
    }

    public List<BookStoreResponse.Book> getBooks(){
        List<BookStoreResponse.Book> booksList = new ArrayList<>();
        Gson gson = new Gson();
        BookStoreResponse.Book[] books = gson.fromJson(get(MY_BOOKS), BookStoreResponse.Book[].class);
        if (books != null){
            booksList = Arrays.asList(gson.fromJson(get(MY_BOOKS), BookStoreResponse.Book[].class));
        }
        return  booksList;
    }

    public void addBookToList(BookStoreResponse.Book book){
        ArrayList<BookStoreResponse.Book> books = new ArrayList<>(getBooks());
        remove(MY_BOOKS);
        books.add(book);
        saveBooks(books);
    }


  }
