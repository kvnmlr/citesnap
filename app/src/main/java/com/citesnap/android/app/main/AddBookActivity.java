package com.citesnap.android.app.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Book;
import com.citesnap.android.app.model.DataManager;
import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.Date;

/**
 * Created by Kevin on 10/18/2016.
 */

public class AddBookActivity extends Activity {
    private static final String TAG = "AddBookActivity";

    private Button searchOnline;
    private Button addBook;

    private EditText titleEntry;
    private EditText authorEntry;
    private EditText isbnEntry;

    public static final String BOOK_ADDED = "BookAdded";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_book);

        titleEntry = (EditText) findViewById(R.id.booktitle_entry);
        authorEntry = (EditText) findViewById(R.id.author_entry);
        isbnEntry = (EditText) findViewById(R.id.isbn_entry);

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
                Book b = new Book();
                b.setTitle(titleEntry.getText().toString());
                b.setAuthor(authorEntry.getText().toString());
                b.setISBN(isbnEntry.getText().toString());

                b.setLink("www.not.yet/implemented");
                b.setDate(new Date());

                Intent data = new Intent();
                if(DataManager.get(getApplication()).add(b).saveBooks()) {
                    data.putExtra(BOOK_ADDED, true);
                } else {
                    data.putExtra(BOOK_ADDED, false);
                }
                setResult(CommonStatusCodes.SUCCESS, data);
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.bookshelf));
    }
}
