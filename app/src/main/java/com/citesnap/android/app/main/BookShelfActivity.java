package com.citesnap.android.app.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Book;
import com.citesnap.android.app.model.DataManager;
import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.ArrayList;
import java.util.List;

public class BookShelfActivity extends Activity {
    private static final String TAG = "BookShelfActivity";
    private static final int RC_ADD_BOOK = 9006;

    private List<Book> movieList = new ArrayList<>();
    private RecyclerView recyclerViewHorizontal;
    private RecyclerView recyclerViewVertical;

    private BookShelfImageAdapter adapterImage;
    private BookShelfListAdapter adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bookshelf);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_bookshelf);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), AddBookActivity.class);
                startActivityForResult(intent, RC_ADD_BOOK);
            }
        });

        LinearLayoutManager layoutManagerHorizontal
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewHorizontal = (RecyclerView) findViewById(R.id.recycler_view_horizontal);
        recyclerViewHorizontal.setLayoutManager(layoutManagerHorizontal);
        movieList = DataManager.get(getApplication()).getBooks();
        adapterImage = new BookShelfImageAdapter(movieList);
        recyclerViewHorizontal.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHorizontal.setAdapter(adapterImage);
        recyclerViewHorizontal.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        LinearLayoutManager layoutManagerVertical
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewVertical = (RecyclerView) findViewById(R.id.recycler_view_vertical);
        recyclerViewVertical.setLayoutManager(layoutManagerVertical);
        movieList = DataManager.get(getApplication()).getBooks();
        adapterList = new BookShelfListAdapter(movieList);
        recyclerViewVertical.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVertical.setAdapter(adapterList);
        recyclerViewVertical.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.bookshelf));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_ADD_BOOK) {
            if (resultCode == CommonStatusCodes.SUCCESS || data.getBooleanExtra(AddBookActivity.BOOK_ADDED, true)) {
                adapterList.notifyDataSetChanged();
                adapterImage.notifyDataSetChanged();
            }
        }
    }
}
