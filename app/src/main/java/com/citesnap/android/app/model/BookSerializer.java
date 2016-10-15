package com.citesnap.android.app.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Kevin on 10/15/2016.
 */

public class BookSerializer {
    private Context context;
    private String filename;

    public BookSerializer(Context c, String f) {
        context = c;
        filename = f;
    }

    public void save(ArrayList<Book> books) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for (Book b : books) {
            array.put(b.toJSON());
        }

        Writer writer = null;
        try {
            OutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString()); }
        finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public ArrayList<Book> load() throws IOException, JSONException {
        ArrayList<Book> books = new ArrayList<Book>();
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++) {
                books.add(new Book(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) { /*ignore*/ }
        finally {
            if (reader != null)
                reader.close();
        }
        return books;
    }
}
