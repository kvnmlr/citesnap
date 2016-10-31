package com.citesnap.android.app.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Quote {
    private static final String JSON_ID = "id";
    private static final String JSON_BOOK = "book";
    private static final String JSON_DATE = "date";
    private static final String JSON_TEXT = "text";
    private static final String JSON_CATEGORY = "category";

    private UUID id;
    private UUID book;
    //private String book;
    private Date date;
    private String text;
    private String category;

    private static ArrayList<String> categories;

    public Quote() {
        id = UUID.randomUUID();	// unique identifier
        date = new Date();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Quote(JSONObject json) throws JSONException {
        id = UUID.fromString(json.getString(JSON_ID));
        date = new Date(json.getLong(JSON_DATE));
        if (json.has(JSON_BOOK)) {
            book = UUID.fromString(json.getString(JSON_BOOK));
        }
        if (json.has(JSON_TEXT)) {
            text = json.getString(JSON_TEXT);
        }
        if (json.has(JSON_CATEGORY)) {
            category = json.getString(JSON_CATEGORY);
        }
    }

    public UUID getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getBook() {
        return this.book;
    }

    public void setBook(UUID id) {
        this.book = id;
    }

    @Override
    public String toString() {
        try {
            return toJSON().toString();
        } catch (Exception JSONException) {
            return this.text;
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, id.toString());
        json.put(JSON_BOOK, book);
        json.put(JSON_TEXT, text);
        json.put(JSON_DATE, date.getTime());
        json.put(JSON_CATEGORY, category);

        return json;
    }

    public static ArrayList<String> getCategories() {
        categories = new ArrayList<>();
        categories.add("Inspiring");
        categories.add("Funny");
        categories.add("Interesting");
        categories.add("Motivational");

        return categories;
    }
}
