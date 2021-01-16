package com.dov.ebook.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dov.ebook.R;
import com.dov.ebook.model.BookStoreResponse;
import com.dov.ebook.sharedpreferences.SharedPreferencesManager;

import java.util.List;

public class BooksRecyclerViewAdapter  extends RecyclerView.Adapter<BooksRecyclerViewAdapter.BooksViewHolder> {
    private List<BookStoreResponse.Book> books;

    public BooksRecyclerViewAdapter(List<BookStoreResponse.Book> books) {
        this.books = books;
    }

    public void setBooks(List<BookStoreResponse.Book> bookInfos) {
        this.books = bookInfos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.book_item, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        holder.bind(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public class BooksViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView titleTV;
        AppCompatTextView authorTV;
        AppCompatTextView descriptionTV;
        AppCompatImageView imageIV;
        AppCompatImageView bookmarkIV;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.title_tv);
            authorTV = itemView.findViewById(R.id.author_tv);
            descriptionTV = itemView.findViewById(R.id.description_tv);
            imageIV = itemView.findViewById(R.id.image_iv);
            bookmarkIV = itemView.findViewById(R.id.bookmark_iv);
        }

        public void bind(BookStoreResponse.Book bookInfo) {
            titleTV.setText(bookInfo.getVolumeInfo().getTitle());
            authorTV.setText(bookInfo.getVolumeInfo().getAuthors().get(0));
            descriptionTV.setText(bookInfo.getVolumeInfo().getDescription());
            bookmarkIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferencesManager.getInstance(bookmarkIV.getContext()).addBookToList(bookInfo);
                }
            });
            if(bookInfo.getVolumeInfo().getImageLinks()!=null){
                Glide.with(imageIV.getContext()).load(bookInfo.getVolumeInfo().getImageLinks().getSmallThumbnail()).into(imageIV);
            }
         }
    }
}
