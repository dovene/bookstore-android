package com.dov.ebook.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dov.ebook.model.BookStoreResponse;
import com.dov.ebook.repository.BooksRepository;

import java.util.List;

public class BooksViewModel extends ViewModel {
    private MutableLiveData<List<BookStoreResponse.Book>> booksListMutableLiveData = new MutableLiveData<>();

    public LiveData<List<BookStoreResponse.Book>> getBooksLiveData(){
        return booksListMutableLiveData;
    }

    public void getBooks(String title, String author) {
        BooksRepository.getInstance().getBooks(title, author, new BooksRepository.BooksServiceListener() {
            @Override
            public void onSuccess(List<BookStoreResponse.Book> books) {
                booksListMutableLiveData.setValue(books);
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }
}
