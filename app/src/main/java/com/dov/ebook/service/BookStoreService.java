package com.dov.ebook.service;

import com.dov.ebook.model.BookStoreResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookStoreService {
    //https://www.googleapis.com/books/v1/volumes?q=' + titleBook + '+inauthor:'+ auteur
    @GET("volumes")
    Call<BookStoreResponse> getBooks(@Query("q") String sort);
}
