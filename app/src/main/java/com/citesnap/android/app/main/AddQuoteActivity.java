package com.citesnap.android.app.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Book;
import com.citesnap.android.app.model.DataManager;
import com.citesnap.android.app.model.Quote;
import com.citesnap.android.app.ocr.OcrCaptureActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static java.lang.Math.toIntExact;


/**
 * Created by Kevin on 10/23/2016.
 */

public class AddQuoteActivity extends Activity {
    private static final String TAG = "AddQuoteActivity";
    private TextView finalLabel;
    private Spinner bookSelection;
    private Spinner categorySelection;
    private ArrayAdapter<String> adapter;
    private String finalQuote;
    private ArrayList<Integer> selectedPositions;
    private ArrayList<String> selectedQuotes;
    private ArrayList<Book> bookList;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_add_quote);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.add_quote));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            toolbar.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.common_google_signin_btn_icon_dark_focused));
        }

        finalLabel = (TextView) findViewById(R.id.final_quote);

        selectedPositions = new ArrayList<>();

        bookSelection = (Spinner) findViewById(R.id.book_selection_spinner);
        List<String> books = new ArrayList<>();
        bookList = new ArrayList<>();
        for (Book b :DataManager.get(getApplication()).getBooks()) {
            if (b.isCurrent()) {
                books.add(b.getTitle() + " (" + b.getAuthor() + ")");
                bookList.add(b);
            }
        }
        for (Book b :DataManager.get(getApplication()).getBooks()) {
            if (!b.isCurrent()) {
                books.add(b.getTitle() + " (" + b.getAuthor() + ")");
                bookList.add(b);
            }
        }
        ArrayAdapter<String> booksSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, books);
        booksSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookSelection.setAdapter(booksSpinnerAdapter);
        bookSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "noting selected", Toast.LENGTH_SHORT).show();
            }
        });

        categorySelection = (Spinner) findViewById(R.id.category_selection_spinner);
        List<String> categories = Quote.getCategories();
        ArrayAdapter<String> categoriesSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySelection.setAdapter((categoriesSpinnerAdapter));
        categorySelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "noting selected", Toast.LENGTH_SHORT).show();
            }
        });

        Bundle b = getIntent().getExtras();
        String[] textParts = b.getStringArray(OcrCaptureActivity.TextBlockObject);
        String textMerge = "";
        for (String s : textParts) {
            if (s == null)
                continue;
            s = s.replace("\n", " ");
            s = s.replace(". ", ".");
            textMerge+=("\n"+s);
        }
        String[] parts = textMerge.split("[.\\n]");
        ArrayList<String> partsList = new ArrayList<>();

        for (String s : parts) {
            if (!s.equals("") && !s.equals(" ") && !s.equals("\n") && s != null) {
                s += ".";
                Log.d(TAG, s);
                partsList.add(s);
            }
        }
        selectedQuotes = new ArrayList<>();
        for (String s : partsList) {
            selectedQuotes.add("");
            selectedQuotes.add("");
        }
        adapter = new QuotePartAdapter(this, partsList);
        ListView quoteList = (ListView) findViewById(R.id.list_quote_parts);
        quoteList.setAdapter(adapter);

        quoteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox check = (CheckBox) view.findViewById(R.id.quote_list_checkbox);
                String quote = (String)parent.getItemAtPosition(position);
                if (check.isChecked()) {
                    selectedPositions.remove(selectedPositions.indexOf(position));
                    selectedQuotes.set(position, "");
                    check.setChecked(false);
                } else {
                    if (!alreadySelected(position)) {
                        selectedPositions.add(position);
                        Log.d(TAG, String.valueOf(selectedQuotes.size()));
                        selectedQuotes.set(position, quote);
                        Log.d(TAG, selectedQuotes.get(position));
                        Log.d(TAG, String.valueOf(selectedQuotes.size()));
                    }
                    check.setChecked(true);
                }
                generateQuote();
                Toast.makeText(getApplicationContext(), finalQuote, Toast.LENGTH_SHORT).show();
            }
        });

        submit = (Button) findViewById(R.id.save_quote);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPositions.size() == 0 || finalQuote == "") {
                    Toast.makeText(getApplicationContext(), R.string.select_at_least_one, Toast.LENGTH_LONG).show();
                } else {
                    Quote q = new Quote();
                    q.setDate(new Date());
                    q.setText(finalQuote);
                    q.setBook(bookList.get((int)(bookSelection.getSelectedItemId())).getId());
                    DataManager.get(getApplication()).add(q).saveQuotes();
                    Intent data = new Intent(getApplication(), QuotesActivity.class);
                    startActivity(data);
                    finish();
                }
            }
        });
    }

    private void generateQuote() {
        finalQuote = "";
        for (String q : selectedQuotes) {
            finalQuote += (q + " ");
        }
        finalLabel.setText(finalQuote);
        return;
    }

    private boolean alreadySelected(int check) {
        if (selectedPositions.size() == 0) {
            return false;
        }
        for (int x : selectedPositions) {
            if (check == x) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelected(int position) {
        return selectedPositions.contains(position);
    }
}
