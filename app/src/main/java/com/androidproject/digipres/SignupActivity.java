 package com.androidproject.digipres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    TextInputLayout RegName, RegDegree, RegField, RegRegi, RegEmail, RegPhone,RegUN, RegPassword;
    Button RegSignupBtn,RegBackLoginBtn;
    ProgressBar Progress_Bar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        RegName = findViewById(R.id.Reg_Name);
        RegDegree= findViewById(R.id.Reg_Degree);
        RegField = findViewById(R.id.Reg_Field);
        RegRegi = findViewById(R.id.Reg_Regi);
        RegEmail = findViewById(R.id.Reg_Email);
        RegPhone = findViewById(R.id.Reg_Phone);
        RegUN = findViewById(R.id.Reg_UN);
        RegPassword = findViewById(R.id.Reg_Password);
        RegSignupBtn=findViewById(R.id.Reg_Signup_Btn);
        RegBackLoginBtn=findViewById(R.id.Reg_Back_Login_Btn);
        Progress_Bar=findViewById(R.id.Progress_bar);

        RegSignupBtn.setOnClickListener(this::onClick);

    }

    private boolean valid_pass() {
        String pass= RegPassword.getEditText().getText().toString();
       /* String passwordFormat = "^" +
                "(?=.*[a-zA-Z])" +   //any letter
                "(?=.*[@#$%^&+=])" + //atleast 1 special char
                "(\\A\\w{4,20}\\z)" + //no white space
                ".{4,}" +  //at least 4 char
                 "$";*/

        if(pass.isEmpty()){
            RegPassword.setError("Field cann't be empty");
            return false;
        }
       // else if(!pass.matches(passwordFormat)){
            //RegPassword.setError("Password is too weak");
           // return false;
      //  }
        else{
            RegPassword.setError(null);
            RegPassword.setErrorEnabled(false);
            return true;
        }

    }

    private boolean valid_un() {
        String un=RegUN.getEditText().getText().toString();
        String noWhitespace = "\\A\\w{4,20}\\z";

        if(un.isEmpty()){
            RegUN.setError("Field cann't be empty");
            return false;
        }
        else if(un.length()>10){
            RegUN.setError("User name is too long");
            return false;
        }
        else if(!un.matches(noWhitespace)){
            RegUN.setError("Remove whitespace");
            return false;
        }
        else{
            RegUN.setError(null);
            RegPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_phone() {
        String phone= RegPhone.getEditText().getText().toString();


        if(phone.isEmpty()){
            RegEmail.setError("Field cann't be empty");
            return false;
        }
        else{
            RegPhone.setError(null);
            RegPhone.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_email() {
        String email= RegEmail.getEditText().getText().toString();
        //String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(email.isEmpty()){
            RegEmail.setError("Field cann't be empty");
            return false;
        }
        /*else if(!email.matches(emailPattern)){
            RegEmail.setError("Invalid email address");
            return false;
        }*/
        else{
            RegEmail.setError(null);
            RegEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_regi() {
        String regi= RegRegi.getEditText().getText().toString();

        if(regi.isEmpty()){
            RegRegi.setError("Field cann't be empty");
            return false;
        }
        else{
            RegRegi.setError(null);
            RegRegi.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_field() {
        String field= RegField.getEditText().getText().toString();

        if(field.isEmpty()){
            RegField.setError("Field cann't be empty");
            return false;
        }
        else{
            RegField.setError(null);
            RegField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_degree() {
        String degree= RegDegree.getEditText().getText().toString();

        if(degree.isEmpty()){
            RegDegree.setError("Field cann't be empty");
            return false;
        }
        else{
            RegDegree.setError(null);
            RegDegree.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_name() {
        String name= RegName.getEditText().getText().toString();
        if(name.isEmpty()){
            RegName.setError("Field cann't be empty");
            return false;
        }
        else{
            RegName.setError(null);
            RegName.setErrorEnabled(false);
            return true;
        }
    }



    public void login_page(View view) {
        Intent intent = new Intent (SignupActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private void onClick(View v) {


        if (!valid_name() || !valid_degree() || !valid_field() || !valid_regi() || !valid_email() || !valid_phone() || !valid_un() || !valid_pass()){
            return;
        }

        Progress_Bar.setVisibility(View.VISIBLE);

        String doctor_name = RegName.getEditText().getText().toString();
        String doctor_degree = RegDegree.getEditText().getText().toString();
        String doctor_field = RegField.getEditText().getText().toString();
        String doctor_regi = RegRegi.getEditText().getText().toString();
        String doctor_email = RegEmail.getEditText().getText().toString();
        String doctor_phone = RegPhone.getEditText().getText().toString();
        String doctor_un = RegUN.getEditText().getText().toString();
        String doctor_password = RegPassword.getEditText().getText().toString();



        mAuth.createUserWithEmailAndPassword(doctor_email, doctor_password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        UserHelperClass helperClass = new UserHelperClass(doctor_name, doctor_degree, doctor_field, doctor_regi, doctor_email, doctor_phone, doctor_un, doctor_password);

                        FirebaseDatabase.getInstance().getReference("Doctors")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(helperClass).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        Toast.makeText(SignupActivity.this, "Account created successfully",Toast.LENGTH_LONG).show();
                                        Progress_Bar.setVisibility(View.GONE);

                                        Intent intent = new Intent (SignupActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Exception error = task1.getException();
                                        Toast.makeText(SignupActivity.this, "Error " + error,Toast.LENGTH_LONG).show();
                                        Progress_Bar.setVisibility(View.GONE);

                                    }
                                });
                    }
                    else{
                        Exception error = task.getException();
                        Toast.makeText(SignupActivity.this, "Error " + error,Toast.LENGTH_LONG).show();
                        Progress_Bar.setVisibility(View.GONE);

                    }
                });
    }
}