package com.dov.ebook.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dov.ebook.databinding.ActivityMainBinding;
import com.dov.ebook.sharedpreferences.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              search();
            }
        });
        binding.myFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayMyFav();
            }
        });
     }

    private void displayMyFav() {
        Intent intent = BooksListActivity.newIntent(MainActivity.this, false,"","");
        startActivity(intent);
    }

    private void search() {
        Intent intent = BooksListActivity.newIntent(MainActivity.this, true, binding.bookTitleEt.getText().toString(),binding.bookAuthorEt.getText().toString());
        startActivity(intent);
        SharedPreferencesManager.getInstance(MainActivity.this).save(SharedPreferencesManager.LAST_AUTHOR_KEY,binding.bookAuthorEt.getText().toString());
        SharedPreferencesManager.getInstance(MainActivity.this).save(SharedPreferencesManager.LAST_TITLE_KEY,binding.bookTitleEt.getText().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.bookTitleEt.setText(SharedPreferencesManager.getInstance(MainActivity.this).get(SharedPreferencesManager.LAST_TITLE_KEY));
        binding.bookAuthorEt.setText(SharedPreferencesManager.getInstance(MainActivity.this).get(SharedPreferencesManager.LAST_AUTHOR_KEY));
    }
}