package com.citesnap.android.app.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Book;
import com.citesnap.android.app.model.DataManager;
import com.citesnap.android.app.model.Quote;

import java.util.ArrayList;

/**
 * Created by Kevin on 10/25/2016.
 */

public class QuoteListAdapter  extends ArrayAdapter<Quote> {

    public QuoteListAdapter(Context context, ArrayList<Quote> items) {
        super(context, R.layout.quote_list_item, items);
    }

    @Override
    public View getView(int position, View convert, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View quoteListView = inflater.inflate(R.layout.quote_list_item, parent, false);

        Quote quote = getItem(position);
        TextView quoteText = (TextView) quoteListView.findViewById(R.id.quote_text);
        String /*TextView*/ bookText = "Placeholder"; //(TextView quoteListView.findViewById(R.id.quote_book_title))
        quoteText.setText(quote.getText() + "Book: " + DataManager.get(getContext()).getBook(quote.getBook()).getTitle());

        //ImageView bookImage = (ImageView) bookshelfItemView.findViewById(R.id.bookshelf_item_image);

        return quoteListView;
    }
}
