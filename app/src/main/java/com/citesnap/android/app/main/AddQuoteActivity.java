package com.citesnap.android.app.main;

import android.app.Activity;
import android.os.Bundle;
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
import com.citesnap.android.app.model.DataManager;
import com.citesnap.android.app.model.Quote;
import com.citesnap.android.app.ocr.OcrCaptureActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kevin on 10/23/2016.
 */

public class AddQuoteActivity extends Activity {
    private static final String TAG = "AddQuoteActivity";
    private TextView finalLabel;
    private Spinner bookSelection;
    private ArrayAdapter<String> adapter;
    private String finalQuote;
    private ArrayList<Integer> selectedPositions;
    private ArrayList<String> selectedQuotes;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_add_quote);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.add_quote));

        finalLabel = (TextView) findViewById(R.id.final_quote);

        selectedPositions = new ArrayList<>();

        bookSelection = (Spinner) findViewById(R.id.book_selection_spinner);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        bookSelection.setAdapter(spinnerDataAdapter);
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

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);



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
                Quote q = new Quote();
                q.setDate(new Date());
                q.setText(finalQuote);
                DataManager.get(getApplication()).add(q).saveQuotes();
            }
        });
    }

    private void generateQuote() {
        finalQuote = "";
        for (int v : selectedPositions) {
            finalQuote += selectedQuotes.get(v);
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
