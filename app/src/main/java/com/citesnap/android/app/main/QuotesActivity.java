package com.citesnap.android.app.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.citesnap.android.app.R;
import com.citesnap.android.app.model.DataManager;
import com.citesnap.android.app.model.Quote;

import java.util.ArrayList;

public class QuotesActivity extends AppCompatActivity {
    private static final String TAG = "QuotesActivity";

    private ArrayAdapter<Quote> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_quotes);
        ArrayList<Quote> quoteArrayList = DataManager.get(getApplication()).getQuotes();


        adapter = new QuoteListAdapter(this, quoteArrayList);
        ListView quoteList = (ListView) findViewById(R.id.list_quotes);
        quoteList.setAdapter(adapter);

        quoteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //CheckBox check = (CheckBox) view.findViewById(R.id.quote_list_checkbox);
                Quote quote = (Quote) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), quote.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.quotes));
    }
}
