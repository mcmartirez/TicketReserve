package com.example.ticketreserve;

public class eventDetails {

    private String eventHost;
    private String eventName;
    private String eventDescription;
    private String eventVenue;
    private String eventKey;

    public eventDetails(){}

    public eventDetails(String eventHost, String eventName, String eventDescription, String eventVenue, String eventKey) {
        this.eventHost = eventHost;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventVenue = eventVenue;
        this.eventKey = eventKey;
    }

    public String getEventHost() {
        return eventHost;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public String getEventKey() {
        return eventKey;
    }
}
