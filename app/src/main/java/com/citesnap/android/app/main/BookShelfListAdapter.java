package com.citesnap.android.app.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.citesnap.android.app.R;
import com.citesnap.android.app.model.Book;
import java.util.List;

public class BookShelfListAdapter extends RecyclerView.Adapter<BookShelfListAdapter.ListViewHolder> {

    private List<Book> bookList;

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView title, author, isbn, link;

        public ListViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.bookshelf_item_title);
            isbn = (TextView) view.findViewById(R.id.bookshelf_item_author);
            author = (TextView) view.findViewById(R.id.bookshelf_item_isbn);
            link = (TextView) view.findViewById((R.id.bookshelf_item_link));
        }
    }


    public BookShelfListAdapter(List<Book> inBookList) {
        this.bookList = inBookList;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookshelf_item, parent, false);

        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.title.setText(book.getTitle());
        holder.isbn.setText(book.getAuthor());
        holder.author.setText(book.getISBN());
        holder.link.setText(book.getLink());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}