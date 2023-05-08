package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * testing the 'Reservation' class
 * since accurate discounting is aleady tested, only customer variance will be tested.
 */
public class ReservationTests {
    LocalDateTime timeWithNoDiscount = LocalDateTime.of(2023, Month.MAY, 12, 10, 0, 0);

    @Test
    void totalFeeTest() {
        var customer = new Customer("John Doe");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 100, 1),
                3,
                timeWithNoDiscount
        );
        assertEquals(new Reservation(customer, showing, 3).totalFee(), 300);
        assertEquals(new Reservation(customer, showing, 1).totalFee(), 100);
    }
}
