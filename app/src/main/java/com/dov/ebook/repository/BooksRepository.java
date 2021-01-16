package com.dov.ebook.repository;

import android.util.Log;

import com.dov.ebook.model.BookInfo;
import com.dov.ebook.model.BookStoreResponse;
import com.dov.ebook.service.BookApi;
import com.dov.ebook.service.BookStoreService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksRepository {
    public interface BooksServiceListener {
        void onSuccess(List<BookStoreResponse.Book> books);
        void onError(Throwable throwable);
    }

    private BooksRepository(){ }
    private static BooksRepository INSTANCE = null;

    public static synchronized BooksRepository getInstance() {
        if(INSTANCE == null){
            INSTANCE = new BooksRepository();
        }
        return INSTANCE;
    }

    public void getBooks(String title, String author, BooksServiceListener booksServiceListener){
        BookStoreService service = BookApi.getInstance().getClient().create(BookStoreService.class);

        String queryString = title+"+inauthor:"+ author;
        Call<BookStoreResponse> call = service.getBooks(queryString);
        call.enqueue(new Callback<BookStoreResponse>() {
            @Override
            public void onResponse(Call<BookStoreResponse> call, Response<BookStoreResponse> response) {
                booksServiceListener.onSuccess(response.body().getBooks());
            }

            @Override
            public void onFailure(Call<BookStoreResponse> call, Throwable t) {
                booksServiceListener.onError(t);
            }
        });
    }
}
