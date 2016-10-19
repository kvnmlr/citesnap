package com.citesnap.android.app.main;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;

import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Util;

import java.io.IOException;

/**
 * Created by Kevin on 10/19/2016.
 */

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";

    private Button defaultSettings;
    private Button apply;

    private CheckBox useFlash;
    private CheckBox autoFocus;

    private Util utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_settings);

        utilities = new Util(this);


        useFlash = (CheckBox) findViewById(R.id.use_flash_checkbox);
        autoFocus = (CheckBox) findViewById(R.id.auto_focus_checkbox);

        useFlash.setChecked(utilities.getBooleanProperty(Util.USE_FLASH));
        autoFocus.setChecked(utilities.getBooleanProperty(Util.AUTO_FOCUS));

        defaultSettings = (Button) findViewById(R.id.default_settings_button);
        defaultSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyDefault();
            }
        });

        apply = (Button) findViewById(R.id.apply_settings_button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilities.setProperty(Util.USE_FLASH, useFlash.isChecked());
                utilities.setProperty(Util.AUTO_FOCUS, autoFocus.isChecked());
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.settings));
    }

    private void applyDefault() {
        useFlash.setChecked(true);
        autoFocus.setChecked(true);
    }
}
