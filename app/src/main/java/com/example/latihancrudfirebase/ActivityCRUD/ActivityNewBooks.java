package com.example.latihancrudfirebase.ActivityCRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.latihancrudfirebase.Model.Book;
import com.example.latihancrudfirebase.R;

import java.util.List;

public class ActivityNewBooks extends AppCompatActivity {
    private EditText mAuthor_editTxt;
    private EditText mTitle_editTxt;
    private EditText mISBN_editTxt;
    private Spinner mBook_categories_spinner;
    private Button mAdd_btn;
    private Button mBack_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_books);

        mAuthor_editTxt = (EditText)findViewById(R.id.EtAuthor);
        mTitle_editTxt = (EditText)findViewById(R.id.EtTitle);
        mISBN_editTxt  = (EditText)findViewById(R.id.EtISBN);
        mBook_categories_spinner = (Spinner)findViewById(R.id.book_category_sp);

        mAdd_btn = (Button) findViewById(R.id.btnAdd);
        mBack_btn = (Button) findViewById(R.id.btnBack);

        mAdd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setAuthor(mAuthor_editTxt.getText().toString());
                book.setTitle(mTitle_editTxt.getText().toString());
                book.setIsbn(mISBN_editTxt.getText().toString());
                book.setCategory_name(mBook_categories_spinner.getSelectedItem().toString());
                new FirebaseDatabaseHelper().addBook(book, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Book> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(ActivityNewBooks.this, "the book record has been inserted successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });


        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();return;
            }
        });
    }
}
