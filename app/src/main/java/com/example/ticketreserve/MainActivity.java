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

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button register = findViewById(R.id.gotoregister);
        register.setOnClickListener(v->{startActivity(new Intent(this, register.class));});

        login = findViewById(R.id.login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login.setOnClickListener(v->{
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("user").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userinput = username.getText().toString().trim();
                    String passinput = password.getText().toString().trim();

                    Intent loginsuccess = new Intent(MainActivity.this, feed.class);
                    loginsuccess.putExtra("userName", userinput);

                    if (userinput.isEmpty() || passinput.isEmpty()){
                        Toast.makeText(MainActivity.this, "Fill up Credentials..", Toast.LENGTH_SHORT).show();
                    }else{
                        if(snapshot.child(userinput).exists()){
                            if(snapshot.child(userinput).child("password").getValue(String.class).equals(passinput) && snapshot.child(userinput).child("password").getValue(String.class) != null){
                                startActivity(loginsuccess);
                            }else{
                                Toast.makeText(MainActivity.this, "Incorrect Password...", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Account does not exist...", Toast.LENGTH_SHORT).show();
                        }
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });



    }
}