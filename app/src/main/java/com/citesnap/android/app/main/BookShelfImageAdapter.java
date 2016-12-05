package com.citesnap.android.app.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Book;
import java.util.List;

public class BookShelfImageAdapter extends RecyclerView.Adapter<BookShelfImageAdapter.ImageViewHolder> {

    private List<Book> bookList;

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageViewHolder(View view) {
            super(view);
        }
    }

    public BookShelfImageAdapter(List<Book> inBookList) {
        this.bookList = inBookList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_shelf_item_image, parent, false);

        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}