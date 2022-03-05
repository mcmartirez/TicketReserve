package com.example.ticketreserve;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class eventList extends Fragment {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("events");

    RecyclerView rView;
    List<eventDetails> eventDetailsList;
    rViewEventList rAdapt;

    String userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        feed fd = (feed) getActivity();
        userName = fd.userNameStr;

        rView = view.findViewById(R.id.listEvent);

        eventDetailsList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((getActivity()));
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rView.setLayoutManager(linearLayoutManager);

        rAdapt = new rViewEventList(getActivity(), eventDetailsList, userName);
        rView.setAdapter(rAdapt);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                eventDetailsList.clear();
                rAdapt.notifyDataSetChanged();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    eventDetails eventDetails = dataSnapshot.getValue(eventDetails.class);
                    eventDetailsList.add(eventDetails);
                }

                rAdapt.notifyDataSetChanged();
                linearLayoutManager.smoothScrollToPosition(rView, null, rView.getAdapter().getItemCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}