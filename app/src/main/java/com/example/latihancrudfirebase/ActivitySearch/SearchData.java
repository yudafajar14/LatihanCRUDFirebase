package com.example.latihancrudfirebase.ActivitySearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.latihancrudfirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchData extends AppCompatActivity {
    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    ArrayList<String> fullNameList;
    ArrayList<String> userNameList;
    ArrayList<String> isbnList;
    ArrayList<String> categoryList;
    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_data);
        search_edit_text = (EditText) findViewById(R.id.searchField);

        recyclerView = (RecyclerView) findViewById(R.id.rc_search);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        fullNameList = new ArrayList<>();
        userNameList = new ArrayList<>();
        isbnList = new ArrayList<>();
        categoryList = new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    /*
                     * Clear the list when editText is empty
                     * */
                    fullNameList.clear();
                    userNameList.clear();
                    isbnList.clear();
                    categoryList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                fullNameList.clear();
                userNameList.clear();
                isbnList.clear();
                categoryList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String full_name = snapshot.child("title").getValue(String.class);
                    String user_name = snapshot.child("author").getValue(String.class);
                    String isbn_list = snapshot.child("isbn").getValue(String.class);
                    String category_list = snapshot.child("category_name").getValue(String.class);

                    if (category_list.toLowerCase().contains(searchedString.toLowerCase())) {
                        fullNameList.add(full_name);
                        userNameList.add(user_name);
                        isbnList.add(isbn_list);
                        categoryList.add(category_list);
                        counter++;
                    } else if (category_list.toLowerCase().contains(searchedString.toLowerCase())) {
                        fullNameList.add(full_name);
                        userNameList.add(user_name);
                        isbnList.add(isbn_list);
                        categoryList.add(category_list);
                        counter++;
                    }

                    /*
                     * Get maximum of 15 searched results only
                     * */
                    if (counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(SearchData.this, fullNameList, userNameList, isbnList,categoryList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}




