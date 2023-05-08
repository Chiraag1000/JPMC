package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.json.*;


public class Theater {

    public LocalDateProvider provider;
    private List<Showing> schedule;

    public Theater(LocalDateProvider provider) {
        this.provider = provider;
        schedule = new ArrayList<>();
    }
    public void newDay(){
        //it doesn't make sense to have a sequence# that could be on different dates without a full list for each day
        //the assumption will be that the theater puts in new movies everyday, so the schedule and special
        //movie will be updated every day using this function
        schedule.clear();
    }
    public void addShowing(Showing showing){
        schedule.add(showing);
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return new Reservation(customer, showing, howManyTickets);
    }

    public void printSchedule() {
        System.out.println(provider.currentDate());
        System.out.println("===================================================");
        schedule.forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + ": " + s.getStartTime() + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getFee())
        );
        System.out.println("===================================================");
    }

    public void printScheduleJson(){
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder jsonObjectRoot = factory.createObjectBuilder();
        for(Showing showing : schedule){
            JsonObjectBuilder jsonObject = factory.createObjectBuilder();
            jsonObject.add("Movie Name", showing.getMovie().getTitle());
            jsonObject.add("Date and Time", showing.getStartTime().toString());
            jsonObject.add("Duration", showing.getMovie().getRunningTime().toString());
            jsonObject.add("Price", showing.getFee());
            jsonObjectRoot.add(Integer.toString(showing.getSequenceOfTheDay()), jsonObject);
        }
        JsonObject toPrint = jsonObjectRoot.build();
        System.out.println(toPrint.toString());
        //prints in a single line, but retains JSON format
    }

    public String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

    public static void main(String[] args) {

    }
}
