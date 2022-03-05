package com.example.ticketreserve;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class myacc extends Fragment {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("events");

    private AlertDialog.Builder adBuilder;
    private AlertDialog dialog;
    private EditText eventNameET, eventVenueET, eventDescET;
    private Button addEventButton;

    String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myacc, container, false);

        feed fd = (feed) getActivity();
        userName = fd.userNameStr;

        Button logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(v->{getActivity().finish();});

        Button addEvent = view.findViewById(R.id.addEvent);
        addEvent.setOnClickListener(v->{addEventPopup();});
        return view;


    }

    private void addEventPopup() {
        adBuilder = new AlertDialog.Builder(getActivity());
        final View postEvent = getLayoutInflater().inflate(R.layout.popupadd, null);

        eventNameET = postEvent.findViewById(R.id.eventNameIn);
        eventVenueET = postEvent.findViewById(R.id.eventVenueIn);
        eventDescET = postEvent.findViewById(R.id.eventDescIn);
        addEventButton = postEvent.findViewById(R.id.submitEvent);

        adBuilder.setView(postEvent);
        dialog = adBuilder.create();
        dialog.show();

        addEventButton.setOnClickListener(v->{
            if (!eventNameET.getText().toString().isEmpty() && !eventVenueET.getText().toString().isEmpty() && !eventDescET.getText().toString().isEmpty()){
                final String eventHost = userName;
                final String eventName = eventNameET.getText().toString().trim();
                final String eventVenue = eventVenueET.getText().toString().trim();
                final String eventDesc = eventDescET.getText().toString().replace('\n', 's').trim();

                String key = databaseReference.push().getKey();
                eventDetails eventDetails = new eventDetails(eventHost, eventName, eventDesc, eventVenue, key);
                databaseReference.child(key).setValue(eventDetails);

                Toast.makeText(getActivity(), "Event Added", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });




    }
}