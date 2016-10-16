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

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Kevin on 10/16/2016.
 */

public class BookShelfListAdapter extends ArrayAdapter<String> {

    public BookShelfListAdapter(Context context, String[] items) {
        super(context, R.layout.bookshelf_item, items);
    }

    @Override
    public View getView(int position, View convert, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View bookshelfItemView = inflater.inflate(R.layout.bookshelf_item, parent, false);

        String bookItem = getItem(position);
        TextView bookName = (TextView) bookshelfItemView.findViewById(R.id.bookshelf_item_text);
        ImageView bookImage = (ImageView) bookshelfItemView.findViewById(R.id.bookshelf_item_image);

        bookName.setText(bookItem);
        bookImage.setImageResource(R.drawable.samplecover);


        return bookshelfItemView;
    }
}
