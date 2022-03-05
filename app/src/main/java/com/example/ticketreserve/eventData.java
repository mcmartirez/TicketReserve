package com.example.ticketreserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class eventData extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    String eKey, eHost, eName, eVenue, eDesc, userName;

    TextView eNameTV, eVenueTV, eDescTV, countTV;

    Button reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_data);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(v->{finish();});

        Bundle ex = getIntent().getExtras();
        userName = ex.getString("userName");
        eKey = ex.getString("eKey");
        eHost = ex.getString("eHost");
        eName = ex.getString("eName");
        eVenue = ex.getString("eVenue");
        eDesc = ex.getString("eDesc");

        eNameTV = findViewById(R.id.eventNameLoad);
        eVenueTV = findViewById(R.id.eventVenueLoad);
        eDescTV = findViewById(R.id.eventDescriptionLoad);

        eNameTV.setText(eName);
        eVenueTV.setText(eVenue);
        eDescTV.setText(eDesc);

        countTV = findViewById(R.id.countLoad);

        reserve = findViewById(R.id.reservation);
        reserve.setOnClickListener(v->{
            databaseReference.child("reserved").child(eKey).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.child(userName).exists()){
                        AlertDialog.Builder build = new AlertDialog.Builder(eventData.this);
                        build.setTitle("Cancel Reservation for "+eName+"?")
                                .setMessage("Revoke your reservation")
                                .setPositiveButton("Yes", (dialog, which) ->{
                                    databaseReference.child("reserved").child(eKey).child(userName).removeValue();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(eventData.this);
                                    builder.setTitle("Your Reservation has been Revoked")
                                            .setMessage("Hope you try...")
                                            .setNeutralButton("Close", (dialog1, which1) -> {

                                            });
                                    builder.create();
                                    builder.show();

                                })
                                .setNeutralButton("No", (dialog, which) -> {

                                });

                        build.create();
                        build.show();

                        databaseReference.child("reserved").child(eKey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int counted = (int) snapshot.getChildrenCount();
                                if (counted != 0) {
                                    countTV.setText(String.valueOf(counted));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }else{
                        AlertDialog.Builder build = new AlertDialog.Builder(eventData.this);
                        build.setTitle("Set Reservation for "+eName+"?")
                                .setMessage("Confirm your reservation")
                                .setPositiveButton("Yes", (dialog, which) ->{
                                    databaseReference.child("reserved").child(eKey).child(userName).child("username").setValue(userName);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(eventData.this);
                                    builder.setTitle("Your Reservation has been Submitted")
                                            .setMessage("Thank you for trying us out...")
                                            .setNeutralButton("Close", (dialog1, which1) -> {


                                            });
                                    builder.create();
                                    builder.show();
                                })
                                .setNeutralButton("No", (dialog, which) -> {

                                });

                        build.create();
                        build.show();

                        databaseReference.child("reserved").child(eKey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int counted = (int) snapshot.getChildrenCount();
                                if (counted != 0) {
                                    countTV.setText(String.valueOf(counted));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });



            }
}