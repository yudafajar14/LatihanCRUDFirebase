package com.example.latihancrudfirebase.ActivityUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.latihancrudfirebase.ActivityCRUD.MainActivity;
import com.example.latihancrudfirebase.Model.User;
import com.example.latihancrudfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etUsername,etEmail,etPassword,etNumberPhone;
    Button btnLogin;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etNumberPhone = findViewById(R.id.etNumberPhone);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });



        FloatingActionButton fabRegSubmit = findViewById(R.id.btnRegSubmit);
        fabRegSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = etUsername.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final String numberphone = etNumberPhone.getText().toString();


                if (username.equals("")){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi Username Anda", Toast.LENGTH_SHORT).show();
                    etUsername.requestFocus();
                }else if (email.equals("")){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi Email Anda", Toast.LENGTH_SHORT).show();

                }
                else if (numberphone.equals("")){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi PhoneNumber Anda", Toast.LENGTH_SHORT).show();
                    etNumberPhone.requestFocus();
                }else if (password.length()<7){
                    etPassword.setError("Password Harus 8 Karakter");
//                    Toast.makeText(RegisterActivity.this, "Silahkan isi Password Anda", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                }
                else {




                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    loading = ProgressDialog.show(RegisterActivity.this,
                                            null,
                                            "Mohon Tunggu",
                                            true,
                                            false);
                                    if (task.isSuccessful()) {

                                        User user = new User(
                                                username,email,numberphone
                                        );
                                        FirebaseDatabase.getInstance().getReference("Pengguna")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){
                                                    Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                    finish();
                                                }else {
                                                    loading.dismiss();
                                                    Toast.makeText(RegisterActivity.this, "Register Gagal", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                        // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    Toast.makeText(RegisterActivity.this, "Authentication Succes.",
//                                            Toast.LENGTH_SHORT).show();
//                                    etUsername.setText("");
//                                    etPassword.setText("");


                                    } else {
                                        loading.dismiss();
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Register Gagal",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onStart() {

        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }

    }
}
