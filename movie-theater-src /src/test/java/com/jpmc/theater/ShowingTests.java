package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *  Testing the functionality of the 'Showing' class
 *
 *  because the majority of this class is setters and getters, only the functionality of determining
 *  final ticket price will be tested
 */
public class ShowingTests {
    Movie testMovie = new Movie("test", Duration.ofMinutes(5),100, 1);
    Movie testMovie2 = new Movie("test2", Duration.ofMinutes(5),100, 2);
    LocalDateTime timeWithNoDiscount = LocalDateTime.of(2023, Month.MAY, 12, 10, 0, 0);
    LocalDateTime timeWithBigDiscount = LocalDateTime.of(2023, Month.MAY, 12, 11, 0, 0);

    LocalDateTime seventhDayTime = LocalDateTime.of(2023, Month.MAY, 7, 10, 0, 0);
    @Test
    void noDiscountShowingTest(){
        Showing showing = new Showing(testMovie, 5, timeWithNoDiscount);
        //this date and time should not have percentage discounts
        assertEquals(showing.getFee(), 100);
        //assertEquals(10, spiderMan.calculateTicketPrice(showing));
    }
    @Test
    void flatDiscountsNoPercentagesTest(){
        Showing firstShowing = new Showing(testMovie, 1, timeWithNoDiscount);
        assertEquals(firstShowing.getFee(), 97);// first showing of the day should be 3$ off, 100-3 = 97

        Showing secondShowing = new Showing(testMovie, 2, timeWithNoDiscount);
        assertEquals(secondShowing.getFee(), 98);// first showing of the day should be 2$ off, 100-3 = 98

        Showing seventhOfMonthShowing = new Showing(testMovie, 3, seventhDayTime);
        assertEquals(seventhOfMonthShowing.getFee(), 99);// 7th day of month showing should be 1$ off, 100-1 = 99

        Showing thirdShowing = new Showing(testMovie, 3, timeWithNoDiscount);
        assertEquals(thirdShowing.getFee(), 100);// third showing of the day should be no discount
    }
    @Test
    void percentageDiscountsNoFlatDiscountsTest(){
        //testing to see if discounts without flat discounts are accurate ( after showing 1 and 2)
        testMovie2.setSpecial();
        Showing firstShowing = new Showing(testMovie2, 3, timeWithNoDiscount);
        assertEquals(firstShowing.getFee(), 80);// special movie should be 20% off, 100-(100*0.2) = 80

        Showing secondShowing = new Showing(testMovie, 4, timeWithBigDiscount);
        assertEquals(secondShowing.getFee(), 75);// time between 11 - 4 should be 25% off
    }

    @Test
    void biggestPercentageDiscountIsApplied(){
        //scenarios: movie is special, and movie is between 11am and 4pm, and is 3rd+ showing of the day
        testMovie2.setSpecial();
        Showing testShowing = new Showing(testMovie2, 3, timeWithBigDiscount);
        assertEquals(testShowing.getFee(), 75);// time between 11 - 4 should be 25% off
    }
    @Test
    void biggestFlatDiscountIsApplied(){
        //scenarios: movie is either 1st or 2nd in the day and is on the 7th of the month
        Showing testShowing = new Showing(testMovie, 1, seventhDayTime);
        assertEquals(testShowing.getFee(), 97);// should use the bigger discount, 3$ off for being first
        Showing testShowing2 = new Showing(testMovie, 2, seventhDayTime);
        assertEquals(testShowing2.getFee(), 98);// should use the bigger discount, 2$ off for being second
    }
    @Test
    void biggestDiscountisAppliedTest(){
        testMovie2.setSpecial();
        //scenario: movie is either 1st in the day and is between 11am-4pm and is a special
        Showing testShowing = new Showing(testMovie2, 1, timeWithBigDiscount);
        assertEquals(testShowing.getFee(), 75);// should use the bigger discount, 25% off for being first
    }


    @Test
    void occupancyTest(){
        Showing testShowing = new Showing(testMovie2, 1, timeWithBigDiscount);
        testShowing.reserve(26);
        try{
            testShowing.reserve(5);
        } catch (Exception e) {
            assertEquals(e.getMessage().substring(0,31), "showing reached maxium capacity");
        }


    }
}
