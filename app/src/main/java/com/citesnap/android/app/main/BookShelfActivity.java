package com.citesnap.android.app.main;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Book;
import com.citesnap.android.app.model.DataManager;
import com.citesnap.android.app.ocr.OcrCaptureActivity;
import com.google.android.gms.common.api.CommonStatusCodes;

public class BookShelfActivity extends Activity {
    private static final String TAG = "BookShelfActivity";
    private static final int RC_ADD_BOOK = 9006;

    private ArrayAdapter<Book> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bookshelf);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_bookshelf);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), AddBookActivity.class);
                startActivityForResult(intent, RC_ADD_BOOK);
            }
        });

        adapter = new BookShelfListAdapter(this, DataManager.get(this).getBooks());
        ListView bookshelf = (ListView) findViewById(R.id.bookshelf_list);
        bookshelf.setAdapter(adapter);

        bookshelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = (Book)parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.bookshelf));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_ADD_BOOK) {
            if (resultCode == CommonStatusCodes.SUCCESS || data.getBooleanExtra(AddBookActivity.BOOK_ADDED, true)) {
                adapter.notifyDataSetChanged();
            }
        }
    }
}
