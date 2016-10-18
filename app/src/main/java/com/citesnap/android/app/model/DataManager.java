package com.citesnap.android.app.model;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.UUID;

public class DataManager {
    private static final String TAG = "CrimeLab";
    private static final String BOOKS_FILENAME = "books.json";
    private static final String QUOTES_FILENAME = "quotes.json";
    private static final String PROFILE_FILENAME = "profiles.json";
    private BookSerializer bookSerializer;
    private QuoteSerializer quoteSerializer;
    private ProfileSerializer profileSerializer;

    private static DataManager dataManager;
    private Context appContext;

    private ArrayList<Book> books;
    private ArrayList<Quote> quotes;
    private ArrayList<Profile> profiles;


    private DataManager(Context appContext){
        this.appContext = appContext;
        bookSerializer = new BookSerializer(appContext, BOOKS_FILENAME);
        quoteSerializer = new QuoteSerializer(appContext, QUOTES_FILENAME);
        profileSerializer = new ProfileSerializer(appContext, PROFILE_FILENAME);


        try {
            books = bookSerializer.load();
        } catch (Exception e) {
            books = new ArrayList<>();
            Log.e(TAG, "error loading books: ", e);
        }

        try {
            quotes = quoteSerializer.load();
        } catch (Exception e) {
            quotes = new ArrayList<>();
            Log.e(TAG, "error loading quotes: ", e);
        }

        try {
            profiles = profileSerializer.load();
        } catch (Exception e) {
            profiles = new ArrayList<>();
            Log.e(TAG, "error loading profiles: ", e);
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
    public DataManager add(Book b) {
        books.add(b);
        return dataManager;
    }
    public DataManager delete(Book b) {
        books.remove(b);
        return dataManager;
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
    public DataManager add(Quote q) {
        quotes.add(q);
        return dataManager;
    }
    public DataManager delete(Quote q) {
        quotes.remove(q);
        return dataManager;
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
    public Profile getProfile(UUID id) {
        for(Profile p : profiles) {
            if(p.getId().equals(id))
                return p;
        }
        return null;
    }
    public DataManager add(Profile p) {
        profiles.add(p);
        return dataManager;
    }
    public DataManager delete(Profile p) {
        quotes.remove(p);
        return dataManager;
    }
    public boolean saveProfiles() {
        try {
            profileSerializer.save(profiles);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "error saving crimes: ", e);
            return false;
        }
    }

    public boolean saveAll() {
        return (saveQuotes() && saveBooks() && saveProfiles());
    }

    public DataManager clear() {
        books = new ArrayList<>();
        quotes = new ArrayList<>();
        profiles = new ArrayList<>();
        saveAll();
        return dataManager;
    }
}
