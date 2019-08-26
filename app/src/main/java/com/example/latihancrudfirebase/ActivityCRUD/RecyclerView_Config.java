package com.example.latihancrudfirebase.ActivityCRUD;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latihancrudfirebase.Model.Book;
import com.example.latihancrudfirebase.R;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private BooksAdapter mBooksAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Book> books, List<String> keys){
        mContext=context;
        mBooksAdapter=new BooksAdapter(books,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mBooksAdapter);
    }


    class BookItemView extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mAuthor;
        private TextView mISBN;
        private TextView mCategory;

        private String key;

        public BookItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.book_list_item,parent,false));

            mTitle = (TextView) itemView.findViewById(R.id.title_books);
            mAuthor = (TextView) itemView.findViewById(R.id.author_books);
            mISBN = (TextView) itemView.findViewById(R.id.isbn_books);
            mCategory = (TextView) itemView.findViewById(R.id.category_books);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, BookDetailActivity.class);
                    intent.putExtra("key",key);
                    intent.putExtra("author",mAuthor.getText().toString());
                    intent.putExtra("title",mTitle.getText().toString());
                    intent.putExtra("category",mCategory.getText().toString());
                    intent.putExtra("isbn",mISBN.getText().toString());

                    mContext.startActivity(intent);
                }
            });

        }

        public void bind(Book book, String key){
            mTitle.setText(book.getTitle());
            mAuthor.setText(book.getAuthor());
            mISBN.setText(book.getIsbn());
            mCategory.setText(book.getCategory_name());
            this.key = key;
        }

    }

    class BooksAdapter extends RecyclerView.Adapter<BookItemView>{
        private List<Book> mBooksList;
        private List<String> mKeys;

        public BooksAdapter(List<Book> mBooksList, List<String> mKeys) {
            this.mBooksList = mBooksList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public BookItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookItemView holder, int position) {
            holder.bind(mBooksList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mBooksList.size();
        }
    }
}
