package com.jpmc.theater;

import java.time.LocalDateTime;

public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    private double finalTicketPrice;

    public final int maxOccupancy = 30;

    private int currentOccupancy;

    /**
     * @param movie movie object
     * @param sequenceOfTheDay the position of the movie in the day's schedule, eg first, second, etc
     * @param showStartTime time the specific show starts
     */
    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
        finalTicketPrice = calculateDiscountedPrice();
        currentOccupancy = 0;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    public double getFee() {
        return finalTicketPrice;
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    public int getCurrentOccupancy(){
        return currentOccupancy;
    }

    public void reserve(int count){
        if(currentOccupancy + count <= maxOccupancy){
            currentOccupancy += count;
        }
        else throw new RuntimeException("showing reached maxium capacity limit if you want to book that many, there are " +
                Integer.toString(maxOccupancy-currentOccupancy) + " seat(s) left");
    }

    private double calculateDiscountedPrice() {
        //look for the lowest price for both percent based discount and the flat discount and compare the 2
        double percentageDiscount = movie.getTicketPrice();
        if(showStartTime.getHour() >= 11 && showStartTime.getHour() <= 16){
            percentageDiscount = movie.getTicketPrice() * 0.75; // 25% discount off for 11am-4pm
        }
        else if (movie.isSpecial()) {
            percentageDiscount = movie.getTicketPrice() * 0.8;  // 20% discount off for special movie
        }

        double flatDiscount = movie.getTicketPrice();
        if (getSequenceOfTheDay() == 1) {
            flatDiscount = movie.getTicketPrice() - 3; // $3 discount for 1st show
        }
        else if (getSequenceOfTheDay() == 2) {
            flatDiscount = movie.getTicketPrice() - 2; // $2 discount for 2nd show
        }
        else if(showStartTime.getDayOfMonth() == 7){
            flatDiscount = movie.getTicketPrice() - 1; // $1 discount for 7th day
        }

        // biggest discount as in the smallest end price wins
        return Math.min(flatDiscount, percentageDiscount);
    }
}
