package com.example.ticketreserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://testreserve-6af1e-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(v->{finish();});

        final EditText usernameReg = findViewById(R.id.usernameReg);
        final EditText passwordReg = findViewById(R.id.passwordReg);

        Button register = findViewById(R.id.register);

        register.setOnClickListener(v->{
            final String usernameTxt = usernameReg.getText().toString().trim();
            final String passwordTxt = passwordReg.getText().toString().trim();

            if (usernameTxt.isEmpty() || passwordTxt.isEmpty()){
                Toast.makeText(this, "Enter Valid Credentials.", Toast.LENGTH_SHORT).show();
            }else{
                databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(usernameTxt).exists()){
                            Toast.makeText(register.this, "Account Existing...", Toast.LENGTH_SHORT).show();
                        }else{
                            databaseReference.child("user").child(usernameTxt).child("username").setValue(usernameTxt);
                            databaseReference.child("user").child(usernameTxt).child("password").setValue(passwordTxt);

                            Toast.makeText(register.this, "Account Registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(register.this, MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}