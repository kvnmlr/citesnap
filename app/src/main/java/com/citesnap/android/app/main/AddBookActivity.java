package com.citesnap.android.app.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Book;

/**
 * Created by Kevin on 10/18/2016.
 */

public class AddBookActivity extends Activity {
    private Button searchOnline;
    private Button addBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_book);

        searchOnline = (Button) findViewById(R.id.search_online_button);
        addBook = (Button) findViewById(R.id.add_book_button);

        searchOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Search Online not yet implemented", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Add Book not yet implemented", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.bookshelf));
    }
}
