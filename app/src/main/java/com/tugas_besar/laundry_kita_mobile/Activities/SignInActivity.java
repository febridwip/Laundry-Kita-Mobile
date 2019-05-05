package com.tugas_besar.laundry_kita_mobile.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tugas_besar.laundry_kita_mobile.R;

public class SignInActivity extends AppCompatActivity {

    private EditText userEmail, userPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar signinProgress;
    private FirebaseAuth mAuth;
    private Intent HomeActivity;
    private ImageView signinPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userEmail           = findViewById(R.id.sign_in_email);
        userPassword        = findViewById(R.id.sign_in_password);
        btnSignIn           = findViewById(R.id.btn_sign_in);
        btnSignUp           = findViewById(R.id.btn_signup);
        btnResetPassword    = findViewById(R.id.btn_reset_password);
        signinProgress      = findViewById(R.id.sign_in_progress);
        mAuth               = FirebaseAuth.getInstance();
        HomeActivity        = new Intent(this, com.tugas_besar.laundry_kita_mobile.Activities.Home.class);

        signinPhoto         = findViewById(R.id.sign_in_photo);
        signinPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinActivity = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signinActivity);
                finish();
            }
        });

        signinProgress.setVisibility(View.INVISIBLE);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinProgress.setVisibility(View.VISIBLE);
                btnSignIn.setVisibility(View.INVISIBLE);

                final String email      = userEmail.getText().toString();
                final String password   = userPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    showMessage("Please Verify All Field");
                    btnSignIn.setVisibility(View.VISIBLE);
                    signinProgress.setVisibility(View.INVISIBLE);
                } else {
                  signIn(email, password);
                }
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            signinProgress.setVisibility(View.INVISIBLE);
                            btnSignIn.setVisibility(View.VISIBLE);
                            updateUI();
                        } else
                            showMessage(task.getException().getMessage());
                            btnSignIn.setVisibility(View.VISIBLE);
                            signinProgress.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private void updateUI() {
        startActivity(HomeActivity);
        finish();
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // user is already connected so we need to redirect him to home page
            updateUI();
        }
    }
}
