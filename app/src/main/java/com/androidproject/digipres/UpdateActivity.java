package com.androidproject.digipres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {
    TextInputLayout Office, Position, Name, Un, Degree, Phone,Field;
    Button Update_btn;
    ProgressBar Progress_bar;

    FirebaseUser user;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    String userID, s_office, s_position, s_name, s_un, s_degree, s_phone, s_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Doctors");
        userID = user.getUid();

        Office=findViewById(R.id.office);
        Position=findViewById(R.id.position);
        Degree=findViewById(R.id.degree);
        Name=findViewById(R.id.name);
        Un=findViewById(R.id.un);
        Phone=findViewById(R.id.phone);
        Field=findViewById(R.id.field);
        show_dr_info();

        Update_btn=findViewById(R.id.update_btn);
        Progress_bar=findViewById(R.id.progress_Bar);

        get_value();

    }

    private void get_value() {
        Intent intent = getIntent();

        s_office=intent.getStringExtra("key_office");
        s_position=intent.getStringExtra("key_position");
        s_degree=intent.getStringExtra("key_degree");
        s_field=intent.getStringExtra("key_field");
        s_name=intent.getStringExtra("key_name");
        s_un=intent.getStringExtra("key_un");
        s_phone=intent.getStringExtra("key_phone");
    }



    private boolean phone_changed() {
        if(!s_phone.equals(Phone.getEditText().getText().toString())){
            reference.child(userID).child("doctor_phone").setValue(Phone.getEditText().getText().toString());
            s_phone=Phone.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean office_changed() {
        if(!s_office.equals(Office.getEditText().getText().toString())){
            reference.child(userID).child("doctor_office").setValue(Office.getEditText().getText().toString());
            s_office=Office.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean position_changed() {
        if(!s_position.equals(Position.getEditText().getText().toString())){
            reference.child(userID).child("doctor_position").setValue(Name.getEditText().getText().toString());
            s_position=Position.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean field_changed() {
        if(!s_field.equals(Field.getEditText().getText().toString())){
            reference.child(userID).child("doctor_field").setValue(Field.getEditText().getText().toString());
            s_field=Field.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean degree_changed() {
        if(!s_degree.equals(Degree.getEditText().getText().toString())){
            reference.child(userID).child("doctor_degree").setValue(Degree.getEditText().getText().toString());
            s_degree=Degree.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean un_changed() {
        if(!s_un.equals(Un.getEditText().getText().toString())){
            reference.child(userID).child("doctor_un").setValue(Un.getEditText().getText().toString());
            s_un=Un.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean name_changed() {
        if(!s_name.equals(Name.getEditText().getText().toString())){
            reference.child(userID).child("doctor_name").setValue(Name.getEditText().getText().toString());
            s_name=Name.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }



    private void show_dr_info() {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors");
        Query chekUser = reference.orderByChild(userID);

        chekUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String nameFromDB = snapshot.child(userID).child("doctor_name").getValue(String.class);
                String degreeFromDB = snapshot.child(userID).child("doctor_degree").getValue(String.class);
                String fieldFromDB = snapshot.child(userID).child("doctor_field").getValue(String.class);
                String unFromDB = snapshot.child(userID).child("doctor_un").getValue(String.class);
                String positionFromDB = snapshot.child(userID).child("doctor_position").getValue(String.class);
                String officeFromDB = snapshot.child(userID).child("doctor_office").getValue(String.class);
                String phoneFromDB = snapshot.child(userID).child("doctor_phone").getValue(String.class);

                Name.getEditText().setText(nameFromDB);
                Un.getEditText().setText(unFromDB);
                Degree.getEditText().setText(degreeFromDB);
                Field.getEditText().setText(fieldFromDB);
                Position.getEditText().setText(positionFromDB);
                Office.getEditText().setText(officeFromDB);
                Phone.getEditText().setText(phoneFromDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private boolean valid_un() {
        String un=Un.getEditText().getText().toString();
        String noWhitespace = "\\A\\w{4,20}\\z";

        if(un.isEmpty()){
            Un.setError("Field cann't be empty");
            return false;
        }
        else if(!un.matches(noWhitespace)){
            Un.setError("Remove whitespace");
            return false;
        }
        else{
            Un.setError(null);
            Un.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_phone() {
        String phone= Phone.getEditText().getText().toString();

        if(phone.isEmpty()){
            Phone.setError("Field cann't be empty");
            return false;
        }
        else{
           Phone.setError(null);
           Phone.setErrorEnabled(false);
            return true;
        }
    }


    private boolean valid_field() {
        String field= Field.getEditText().getText().toString();

        if(field.isEmpty()){
            Field.setError("Field cann't be empty");
            return false;
        }
        else{
            Field.setError(null);
            Field.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_degree() {
        String degree= Degree.getEditText().getText().toString();

        if(degree.isEmpty()){
           Degree.setError("Field cann't be empty");
           return false;
        }
        else{
            Degree.setError(null);
            Degree.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_name() {
        String name= Name.getEditText().getText().toString();
        if(name.isEmpty()){
            Name.setError("Field cann't be empty");
            return false;
        }
        else{
            Name.setError(null);
            Name.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_position() {
        String position= Position.getEditText().getText().toString();
        if(position.isEmpty()){
            Position.setError("Field cann't be empty");
            return false;
        }
        else{
           Position.setError(null);
           Position.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_office() {
        String office= Office.getEditText().getText().toString();
        if(office.isEmpty()){
            Office.setError("Field cann't be empty");
            return false;
        }
        else{
            Office.setError(null);
            Office.setErrorEnabled(false);
            return true;
        }
    }

    public void update_info(View view) {
        if (!valid_name() || !valid_degree() || !valid_field() || !valid_position() || !valid_office() || !valid_phone() || !valid_un() ){
            return;
        }

        Progress_bar.setVisibility(View.VISIBLE);

        if(name_changed() || un_changed() || degree_changed() || field_changed() || position_changed() || office_changed() || phone_changed()){

            Toast.makeText(UpdateActivity.this, "Profile updated successfully",Toast.LENGTH_LONG).show();
            Progress_bar.setVisibility(View.GONE);

            Intent intent = new Intent (UpdateActivity.this,ProfileActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(UpdateActivity.this, "Profile remain same",Toast.LENGTH_LONG).show();
            Progress_bar.setVisibility(View.GONE);
        }
    }
}