package com.citesnap.android.app.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Book;

import java.util.ArrayList;

/**
 * Created by Kevin on 10/24/2016.
 */

public class QuotePartAdapter extends ArrayAdapter<String> {
    private Context c;

    public QuotePartAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.bookshelf_item, items);

        c = context;
    }

    @Override
    public View getView(int position, View convert, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View quoteListView = inflater.inflate(R.layout.quote_list_item, parent, false);

        String quote = getItem(position);
        TextView quoteText = (TextView) quoteListView.findViewById(R.id.quote_text);
        CheckBox check = (CheckBox) quoteListView.findViewById(R.id.quote_list_checkbox);

        quoteText.setText(quote);
        check.setChecked(((AddQuoteActivity)c).isSelected(position));

        return quoteListView;
    }
}
