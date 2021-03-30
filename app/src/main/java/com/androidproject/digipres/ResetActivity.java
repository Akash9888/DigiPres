package com.androidproject.digipres;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    TextInputLayout RecoEmail;
    Button SubmitButton;
    ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        RecoEmail = findViewById(R.id.recovery_email);
        SubmitButton = findViewById(R.id.submit_btn);
        progressBar = findViewById(R.id.progress_bar);

        SubmitButton.setOnClickListener(v -> check());
    }

    private boolean valid_email() {
        String Email= RecoEmail.getEditText().getText().toString();

        if(Email.isEmpty()){
            RecoEmail.setError("Field cann't be empty");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            RecoEmail.setError("Wrong email format");
            return false;
        }
        else{
            RecoEmail.setError(null);
            RecoEmail.setErrorEnabled(false);
            return true;
        }
    }

    private void check() {
        if(!valid_email()){
            return;
        }
        else{
            isUser();
        }
    }

    private void isUser() {
        auth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.VISIBLE);

        String enteredEmail=RecoEmail.getEditText().getText().toString().trim();

        auth.sendPasswordResetEmail(enteredEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetActivity.this, "Please check your email",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                    Intent intent = new Intent (ResetActivity.this,LoginActivity.class);
                    startActivity(intent);

                }else{
                    Exception error = task.getException();
                    Toast.makeText(ResetActivity.this, "Error " + error,Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }


}