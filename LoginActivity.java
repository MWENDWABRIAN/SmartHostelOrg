package com.example.smarthostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView txtviewreg, txtviewforgot;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.emaillogin);
        editTextPassword = findViewById(R.id.passwordlogin);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        buttonLogin = findViewById(R.id.btnLogin);
        txtviewreg = findViewById(R.id.registernow);
        txtviewforgot = findViewById(R.id.forgotpassword);

        txtviewforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        txtviewreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(false);
            }
        });

    }

    private void login(boolean isOwner) {
        progressBar.setVisibility(View.VISIBLE);
        String email = String.valueOf(editTextEmail.getText());
        String password = String.valueOf(editTextPassword.getText());

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_LONG).show();
            editTextEmail.setError("email required");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_LONG).show();
            editTextPassword.setError("password required");
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Successful.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), isOwner ? LoginActivity.class : MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(LoginActivity.this, "Incorrect Password. Forgot password? Click the text 'Forgot Password' to reset.", Toast.LENGTH_LONG).show();
                            } else {
                                mAuth.fetchSignInMethodsForEmail(email)
                                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                                if (task.isSuccessful()) {
                                                    SignInMethodQueryResult result = task.getResult();
                                                    if (result.getSignInMethods().isEmpty()) {
                                                        Toast.makeText(LoginActivity.this, "Account not found. Please register.", Toast.LENGTH_LONG).show();
                                                    }
                                                } else {
                                                    Log.e("LoginActivity", "Fetch sign in methods failed: ", task.getException());
                                                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                Log.e("LoginActivity", "Authentication failed: ", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
