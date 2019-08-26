package com.example.latihancrudfirebase.ActivitySearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.latihancrudfirebase.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> titleNameList;
    ArrayList<String> userNameList;
    ArrayList<String> isbnList;
    ArrayList<String> categoryList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView title_name, user_name, isbnList,categoryList;

        public SearchViewHolder(View itemView) {
            super(itemView);
            title_name = (TextView) itemView.findViewById(R.id.title_books);
            user_name = (TextView) itemView.findViewById(R.id.author_books);
            isbnList = (TextView) itemView.findViewById(R.id.isbn_books);
            categoryList = (TextView) itemView.findViewById(R.id.category_books);

        }
    }

    public SearchAdapter(Context context, ArrayList<String> titleNameList, ArrayList<String> userNameList, ArrayList<String> isbnList,  ArrayList<String> categoryList) {
        this.context = context;
        this.titleNameList = titleNameList;
        this.userNameList = userNameList;
        this.isbnList = isbnList;
        this.categoryList = categoryList;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.title_name.setText(titleNameList.get(position));
        holder.user_name.setText(userNameList.get(position));
        holder.isbnList.setText(isbnList.get(position));
        holder.categoryList.setText(categoryList.get(position));

        holder.title_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Full Name Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleNameList.size();
    }
}