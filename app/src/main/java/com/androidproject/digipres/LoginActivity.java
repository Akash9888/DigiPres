package com.androidproject.digipres;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout loginEmail,loginPass;
    Button loginBtn,forgotBtn,signupBtn;
    ProgressBar Progress_Bar;

    CheckBox check_Box;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn=findViewById(R.id.login_btn);
        forgotBtn=findViewById(R.id.forgot_btn);
        signupBtn=findViewById(R.id.signup_btn);
        Progress_Bar=findViewById(R.id.progress_bar);
        loginEmail=findViewById(R.id.login_email);
        loginPass=findViewById(R.id.login_pass);
        check_Box = findViewById(R.id.checkBox);


        loginBtn.setOnClickListener(this::onClick);


    }


    private boolean valid_password() {
        String Password= Objects.requireNonNull(loginPass.getEditText()).getText().toString();

        if(Password.isEmpty()){
            loginPass.setError("Field cann't be empty");
            return false;
        }

        else{
            loginPass.setError(null);
            loginPass.setErrorEnabled(false);
            return true;
        }
    }


    private boolean valid_email() {
        String Email= loginEmail.getEditText().getText().toString();

        if(Email.isEmpty()){
            loginEmail.setError("Field cann't be empty");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            loginEmail.setError("Wrong email format");
            return false;
        }
        else{
            loginEmail.setError(null);
            loginEmail.setErrorEnabled(false);
            return true;
        }
    }

    private void onClick(View view) {

        if(!valid_email() || !valid_password()){
            return;
        }
        else{
            isUser();
        }
    }



    private void isUser() {

         mAuth = FirebaseAuth.getInstance();

         Progress_Bar.setVisibility(View.VISIBLE);

         String enteredEmail=loginEmail.getEditText().getText().toString().trim();
         String enteredPass=loginPass.getEditText().getText().toString().trim();



         mAuth.signInWithEmailAndPassword(enteredEmail, enteredPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){

                        if(check_Box.isChecked()){

                            String email = enteredEmail;
                            String password = enteredPass;
                            int temp=2;
                            Session session = new Session(email,password,temp);
                            SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                            sessionManagement.saveSession(session);

                            move_to_decider();
                        }else{
                            move_to_decider();
                        }

                 }
                 else{
                     Exception error = task.getException();
                     Toast.makeText(LoginActivity.this, "Error " + error,Toast.LENGTH_LONG).show();
                     Progress_Bar.setVisibility(View.GONE);
                 }
             }
         });

             /*else{
                 loginPass.setError("Wrong password");
                 Progress_Bar.setVisibility(View.GONE);
             }*/


    }

    private void move_to_decider() {

        Intent intent = new Intent (LoginActivity.this,DeciderActivity.class);
        startActivity(intent);

    }


    public void signup_page(View view) {
        Intent intent = new Intent (LoginActivity.this,SignupActivity.class);
        startActivity(intent);
    }

    public void reset_pass(View view) {
        Intent intent = new Intent(LoginActivity.this,ResetActivity.class);
        startActivity(intent);
    }
}