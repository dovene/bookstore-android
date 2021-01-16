package com.dov.ebook.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dov.ebook.R;
import com.dov.ebook.databinding.ActivityBooksBinding;
import com.dov.ebook.databinding.ActivityMainBinding;
import com.dov.ebook.model.BookStoreResponse;
import com.dov.ebook.repository.BooksRepository;
import com.dov.ebook.sharedpreferences.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class BooksListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BooksRecyclerViewAdapter booksRecyclerViewAdapter;
    public static String SEARCH_MODE = "SEARCH_MODE";
    public static String AUTHOR = "AUTHOR";
    public static String TITLE = "TITLE";
    private ActivityBooksBinding binding;

    public static Intent newIntent(Context context, boolean isSearchMode, String title, String author) {
        Intent intent = new Intent(context, BooksListActivity.class);
        intent.putExtra(SEARCH_MODE, isSearchMode);
        intent.putExtra(AUTHOR, author);
        intent.putExtra(TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBooksBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setViewItems();
    }

    private void setViewItems() {
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksRecyclerViewAdapter = new BooksRecyclerViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(booksRecyclerViewAdapter);
        if (getIntent().getBooleanExtra(SEARCH_MODE, false)) {
            setTitle("Résultat de la recherche");
            callService();
        } else {
            setTitle("Ma Bibliothèque");
            List<BookStoreResponse.Book> books = SharedPreferencesManager.getInstance(BooksListActivity.this).getBooks();
            booksRecyclerViewAdapter.setBooks(new ArrayList<>(books));
        }
    }

    private void callService() {
        BooksRepository.getInstance().getBooks(getIntent().getStringExtra(TITLE), getIntent().getStringExtra(AUTHOR), new BooksRepository.BooksServiceListener() {
            @Override
            public void onSuccess(List<BookStoreResponse.Book> books) {
                booksRecyclerViewAdapter.setBooks(books);
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
