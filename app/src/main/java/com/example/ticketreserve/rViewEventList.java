package com.example.ticketreserve;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class rViewEventList extends RecyclerView.Adapter<rViewEventList.MyViewHolder> {

    DatabaseReference countRef = FirebaseDatabase.getInstance().getReference().child("reserved");

    Context context;
    private final List<eventDetails> eventDetailsList;
    String userName;

    public rViewEventList(Context context, List<eventDetails> eventDetailsList, String userName) {
        this.context = context;
        this.eventDetailsList = eventDetailsList;
        this.userName = userName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventcards, parent, false);
        return new MyViewHolder(eventCardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        eventDetails eventDetails = eventDetailsList.get(position);
        String eventKey = eventDetails.getEventKey();
        String eventHost = eventDetails.getEventHost();
        String eventName = eventDetails.getEventName();
        String eventVenue = eventDetails.getEventVenue();
        String eventDesc = eventDetails.getEventDescription();

        holder.eventName.setText(eventDetails.getEventName());
        holder.eventVenue.setText(eventDetails.getEventVenue());
        holder.eventDesc.setText(eventDetails.getEventDescription());

        countRef.child(eventKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counted = (int) snapshot.getChildrenCount();
                if (counted != 0) {
                    holder.countAttend.setText(String.valueOf(counted));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.itemView.setOnClickListener(v->{
            Intent loadEvent = new Intent(context, eventData.class);
            loadEvent.putExtra("userName", userName);
            loadEvent.putExtra("eName", eventName);
            loadEvent.putExtra("eVenue", eventVenue);
            loadEvent.putExtra("eDesc", eventDesc);
            loadEvent.putExtra("eHost", eventHost);
            loadEvent.putExtra("eKey",  eventKey);
            context.startActivity(loadEvent);
        });


    }

    @Override
    public int getItemCount() {
        return eventDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventName;
        TextView eventVenue;
        TextView eventDesc;
        TextView countAttend;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.eventname);
            eventVenue = itemView.findViewById(R.id.eventVenue);
            eventDesc = itemView.findViewById(R.id.eventDescription);
            countAttend = itemView.findViewById(R.id.countsofattendee);
        }
    }
}
