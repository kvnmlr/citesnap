package com.citesnap.android.app.model;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.UUID;

public class DataManager {
    private static final String TAG = "CrimeLab";
    private static final String BOOKS_FILENAME = "books.json";
    private static final String QUOTES_FILENAME = "quotes.json";
    private BookSerializer bookSerializer;
    private QuoteSerializer quoteSerializer;



    private static DataManager dataManager;
    private Context appContext;

    private ArrayList<Book> books;
    private ArrayList<Quote> quotes;


    private DataManager(Context appContext){
        appContext = appContext;
        bookSerializer = new BookSerializer(appContext, BOOKS_FILENAME);
        quoteSerializer = new QuoteSerializer(appContext, QUOTES_FILENAME);


        try {
            books = bookSerializer.load();
        } catch (Exception e) {
            books = new ArrayList<Book>();
            Log.e(TAG, "error loading crimes: ", e);
        }
    }

    public static DataManager get(Context c) {
        if (dataManager == null) {
            dataManager = new DataManager(c.getApplicationContext());
        }
        return dataManager;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
    public Book getBook(UUID id) {
        for(Book b : books) {
            if(b.getId().equals(id))
                return b;
        }
        return null;
    }
    public void addBook(Book b) {
        books.add(b);
    }
    public void delete(Book b) {
        books.remove(b);
    }
    public boolean saveBooks() {
        try {
            bookSerializer.save(books);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "error saving crimes: ", e);
            return false;
        }
    }

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }
    public Quote getQuote(UUID id) {
        for(Quote q : quotes) {
            if(q.getId().equals(id))
                return q;
        }
        return null;
    }
    public void addQuote(Quote q) {
        quotes.add(q);
    }
    public void delete(Quote q) {
        quotes.remove(q);
    }
    public boolean saveQuotes() {
        try {
            quoteSerializer.save(quotes);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "error saving crimes: ", e);
            return false;
        }
    }
}
