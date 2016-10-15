package com.citesnap.android.app.model;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import java.util.UUID;

public class Book {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DATE = "date";
    private static final String JSON_AUTHOR = "author";
    private static final String JSON_LINK = "link";
    private static final String JSON_ISBN = "ISBN";

    private UUID id;
    private String title;
    private Date date;
    private String author;
    private String link;
    private String isbn;

    public Book() {
        id = UUID.randomUUID();	// unique identifier
        date = new Date();
    }

    public Book(JSONObject json) throws JSONException {
        id = UUID.fromString(json.getString(JSON_ID));
        date = new Date(json.getLong(JSON_DATE));
        if (json.has(JSON_TITLE)) {
            title = json.getString(JSON_TITLE);
        }
        if (json.has(JSON_AUTHOR)) {
            author = json.getString(JSON_AUTHOR);
        }
        if (json.has(JSON_LINK)) {
            link = json.getString(JSON_LINK);
        }
        if (json.has(JSON_ISBN)) {
            isbn = json.getString(JSON_ISBN);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        try {
            return toJSON().toString();
        } catch (Exception JSONException) {
            return this.title;
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, id.toString());
        json.put(JSON_TITLE, title);
        json.put(JSON_AUTHOR, author);
        json.put(JSON_LINK, link);
        json.put(JSON_ISBN, isbn);
        json.put(JSON_DATE, date.getTime());

        return json;
    }
}
