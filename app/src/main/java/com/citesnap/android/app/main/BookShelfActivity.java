package com.citesnap.android.app.main;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.citesnap.android.app.R;

public class BookShelfActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bookshelf);

        String[] books = {"Book1", "Book2", "Book3"};
        ListAdapter adapter = new BookShelfListAdapter(this, books);
        ListView bookshelf = (ListView) findViewById(R.id.bookshelf_list);
        bookshelf.setAdapter(adapter);

        bookshelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String book = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getApplicationContext(), book, Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.bookshelf));
    }
}
