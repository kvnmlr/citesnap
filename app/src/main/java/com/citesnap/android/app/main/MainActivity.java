package com.citesnap.android.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Book;
import com.citesnap.android.app.model.DataManager;
import com.citesnap.android.app.model.Profile;
import com.citesnap.android.app.model.Quote;
import com.citesnap.android.app.model.Util;
import com.citesnap.android.app.ocr.OcrCaptureActivity;
import com.citesnap.android.app.ocr.OcrMainActivity;
import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.Date;

public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private static final int RC_OCR_CAPTURE = 9003;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main_screen);
        result = (TextView) findViewById(R.id.result_text_view);

        defaultTestSetup();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), OcrCaptureActivity.class);
                startActivityForResult(intent, RC_OCR_CAPTURE);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_feed) {
            Toast toast = Toast.makeText(getApplicationContext(), "Not yet implemented", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (id == R.id.nav_bookshelf) {

            Intent intent = new Intent(this, BookShelfActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_quotes) {
            Intent intent = new Intent(this, QuotesActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_share) {
            Toast toast = Toast.makeText(getApplicationContext(), "Not yet implemented", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (id == R.id.nav_send) {
            Intent intent = new Intent(this, OcrCaptureActivity.class);
            startActivityForResult(intent, RC_OCR_CAPTURE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    //statusMessage.setText(R.string.ocr_success);
                    result.setText(text);
                    Log.d(TAG, "Text read: " + text);
                } else {
                    //statusMessage.setText(R.string.ocr_failure);
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
                //statusMessage.setText(String.format(getString(R.string.ocr_error), CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void defaultTestSetup() {
        DataManager dm = DataManager.get(this);
        dm.clear();

        Book b1 = new Book();
        b1.setTitle("Harry Potter");
        b1.setAuthor("K.K. Rolling");
        b1.setDate(new Date());
        b1.setISBN("345-567-678");
        b1.setLink("www.amazon.de/irgendwas");
        b1.setFinished(true);
        dm.add(b1);

        Book b2 = new Book();
        b2.setTitle("A Brief History of Time");
        b2.setAuthor("Stephen Hawking");
        b2.setDate(new Date());
        b2.setISBN("123-234-345");
        b2.setLink("www.amazon.de/irgendwas/anderes");
        b2.setCurrent(true);
        dm.add(b2);

        Book b3 = new Book();
        b3.setTitle("Made in America");
        b3.setAuthor("Sam Walton");
        b3.setDate(new Date());
        b3.setISBN("789-890-012");
        b3.setLink("www.amazon.de/irgendwas/anderes");
        b3.setCurrent(true);
        dm.add(b3);
        dm.saveBooks();

        Quote q1 = new Quote();
        q1.setText("Es war einmal vor langer Zeit");
        q1.setDate(new Date());
        q1.setBook(b1.getId());
        dm.add(q1);

        Quote q2 = new Quote();
        q2.setText("Und wenn sie nicht gestorben sind, dann leben sie noch heute");
        q2.setDate(new Date());
        q2.setBook(b2.getId());
        dm.add(q2).saveQuotes();

        Profile p = new Profile();
        p.setFirstname("Kevin");
        p.setLastname("Müller");
        dm.add(p).saveProfiles();

        Util u = new Util(this);
        u.restoreDefault();
    }
}
