package com.citesnap.android.app.model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import java.util.UUID;

public class Profile {
    private static final String JSON_ID = "id";
    private static final String JSON_FIRSTNAME = "firstname";
    private static final String JSON_LASTNAME = "lastname";

    private UUID id;
    private String firstname;
    private String lastname;

    public Profile() {
        id = UUID.randomUUID();	// unique identifier
    }

    public Profile(JSONObject json) throws JSONException {
        id = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_FIRSTNAME)) {
            firstname = json.getString(JSON_FIRSTNAME);
        }
        if (json.has(JSON_LASTNAME)) {
            lastname = json.getString(JSON_LASTNAME);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        try {
            return toJSON().toString();
        } catch (Exception JSONException) {
            return this.firstname;
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, id.toString());
        json.put(JSON_FIRSTNAME, firstname);
        json.put(JSON_LASTNAME, lastname);
        return json;
    }
}
