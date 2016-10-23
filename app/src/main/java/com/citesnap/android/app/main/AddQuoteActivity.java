package com.citesnap.android.app.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.citesnap.android.app.R;
import com.citesnap.android.app.ocr.OcrCaptureActivity;

/**
 * Created by Kevin on 10/23/2016.
 */

public class AddQuoteActivity extends Activity {
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main_screen);
        result = (TextView) findViewById(R.id.result_text_view);

        Bundle b = getIntent().getExtras();

        result.setText(b.getString(OcrCaptureActivity.TextBlockObject));
    }
}
