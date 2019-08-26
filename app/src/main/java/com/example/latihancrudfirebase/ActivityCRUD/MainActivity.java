package com.example.latihancrudfirebase.ActivityCRUD;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.latihancrudfirebase.ActivityUser.LoginActivity;
import com.example.latihancrudfirebase.ActivityUser.RegisterActivity;
import com.example.latihancrudfirebase.Model.Book;
import com.example.latihancrudfirebase.R;
import com.example.latihancrudfirebase.ActivitySearch.SearchData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressDialog loading;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_books);
        loading = ProgressDialog.show(MainActivity.this,
                null,
                "Please Wait",
                true,
                false);
        new FirebaseDatabaseHelper().readBooks(new FirebaseDatabaseHelper.DataStatus() {

            @Override
            public void DataIsLoaded(List<Book> books, List<String> keys) {

                new RecyclerView_Config().setConfig(mRecyclerView,MainActivity.this,books,keys);
                loading.dismiss();
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.booklist_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.newBook:
                startActivity(new Intent(this,ActivityNewBooks.class));
                return true;

            case R.id.search:
                startActivity(new Intent(this, SearchData.class));

                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
