package com.citesnap.android.app.model;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public interface Serializer {
    void saveCrimes() throws JSONException, IOException;
    ArrayList<?> loadCrimes() throws JSONException, IOException;
}
