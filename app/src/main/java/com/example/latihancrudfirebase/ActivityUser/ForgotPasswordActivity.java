package com.example.latihancrudfirebase.ActivityUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.latihancrudfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText userEmail;

    private Button btnForgotPass;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        userEmail = findViewById(R.id.etUserEmail);
        btnForgotPass = findViewById(R.id.btnForgotPass);

        firebaseAuth = FirebaseAuth.getInstance();

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final String Email = userEmail.getText().toString();

                if (Email.equals("")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Masukkan Email Anda", Toast.LENGTH_SHORT).show();
                    userEmail.requestFocus();
                } else {

                    loading = ProgressDialog.show(ForgotPasswordActivity.this,
                            null,
                            "Mohon Tunggu",
                            true,
                            false);

                    firebaseAuth.sendPasswordResetEmail(Email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    loading.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPasswordActivity.this, "Password send to your email", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(ForgotPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    userEmail.setText("");
                                }
                            });
                }
            }
        });

    }
}
