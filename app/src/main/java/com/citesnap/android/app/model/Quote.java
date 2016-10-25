package com.citesnap.android.app.model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import java.util.UUID;

public class Quote {
    private static final String JSON_ID = "id";
    private static final String JSON_BOOK = "book";
    private static final String JSON_DATE = "date";
    private static final String JSON_TEXT = "text";

    private UUID id;
    private String book;
    private Date date;
    private String text;

    public Quote() {
        id = UUID.randomUUID();	// unique identifier
        date = new Date();
    }

    public Quote(JSONObject json) throws JSONException {
        id = UUID.fromString(json.getString(JSON_ID));
        date = new Date(json.getLong(JSON_DATE));
        if (json.has(JSON_BOOK)) {
            book = json.getString(JSON_BOOK);
        }
        if (json.has(JSON_TEXT)) {
            text = json.getString(JSON_TEXT);
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

    public String getBook() {
        return this.book;
    }

    public void setBook(String id) {
        this.book = id;
    }

    @Override
    public String toString() {
        try {
            return toJSON().toString();
        } catch (Exception JSONException) {
            return this.book;
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, id.toString());
        json.put(JSON_BOOK, book);
        json.put(JSON_TEXT, text);
        json.put(JSON_DATE, date.getTime());

        return json;
    }
}
