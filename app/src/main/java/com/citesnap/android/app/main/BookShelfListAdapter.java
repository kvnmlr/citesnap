package com.citesnap.android.app.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Book;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Kevin on 10/16/2016.
 */

public class BookShelfListAdapter extends ArrayAdapter<Book> {

    public BookShelfListAdapter(Context context, ArrayList<Book> items) {
        super(context, R.layout.bookshelf_item, items);
    }

    @Override
    public View getView(int position, View convert, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View bookshelfItemView = inflater.inflate(R.layout.bookshelf_item, parent, false);

        Book bookItem = getItem(position);

        TextView bookTitle = (TextView) bookshelfItemView.findViewById(R.id.bookshelf_item_title);
        TextView bookAuthor = (TextView) bookshelfItemView.findViewById(R.id.bookshelf_item_author);
        TextView bookISBN = (TextView) bookshelfItemView.findViewById(R.id.bookshelf_item_isbn);
        TextView bookLink = (TextView) bookshelfItemView.findViewById(R.id.bookshelf_item_link);

        ImageView bookImage = (ImageView) bookshelfItemView.findViewById(R.id.bookshelf_item_image);

        bookTitle.setText(bookItem.getTitle());
        bookAuthor.setText(bookItem.getAuthor());
        bookISBN.setText(bookItem.getISBN());
        bookLink.setText(bookItem.getLink());
        bookImage.setImageResource(R.drawable.samplecover);

        return bookshelfItemView;
    }
}
