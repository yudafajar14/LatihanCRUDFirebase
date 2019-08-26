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

public class BookDetailActivity extends AppCompatActivity {
    private EditText mAuthor_editTxt;
    private EditText mTitle_editTxt;
    private EditText mISBN_editTxt;
    private Spinner mBook_categories_spinner;

    private Button mUpdate_btn;
    private Button mDelete_btn;
    private Button mBack_btn;

    private String key;
    private String author;
    private String title;
    private String category;
    private String isbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        key = getIntent().getStringExtra("key");
        title = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        category = getIntent().getStringExtra("category");
        isbn = getIntent().getStringExtra("isbn");

        mAuthor_editTxt = (EditText)findViewById(R.id.EtAuthor);
        mAuthor_editTxt.setText(author);
        mTitle_editTxt = (EditText)findViewById(R.id.EtTitle);
        mTitle_editTxt.setText(title);
        mISBN_editTxt  = (EditText)findViewById(R.id.EtISBN);
        mISBN_editTxt.setText(isbn);
        mBook_categories_spinner = (Spinner)findViewById(R.id.book_category_sp);
        mBook_categories_spinner.setSelection(getIndex_SpinnerItem(mBook_categories_spinner,category));

        mUpdate_btn = (Button) findViewById(R.id.btnUpdate);
        mDelete_btn = (Button) findViewById(R.id.btnDelete);
        mBack_btn = (Button) findViewById(R.id.btnBack);

        mUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book= new Book();
                book.setAuthor(mAuthor_editTxt.getText().toString());
                book.setTitle(mTitle_editTxt.getText().toString());
                book.setIsbn(mISBN_editTxt.getText().toString());
                book.setCategory_name(mBook_categories_spinner.getSelectedItem().toString());

                new FirebaseDatabaseHelper().updateBook(key, book, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Book> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(BookDetailActivity.this, "book record has been update successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });


        mDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FirebaseDatabaseHelper().deleteBooks(key, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Book> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(BookDetailActivity.this, "book record has been deleted successfully", Toast.LENGTH_SHORT).show();
                        finish(); return;
                    }
                });
            }
        });

        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); return;
            }
        });
    }

    private int getIndex_SpinnerItem(Spinner spinner, String item){
        int index = 0;
        for (int i = 0 ; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).equals(item)){
                index = i;
                break;
            }
        }
        return index;
    }
}
