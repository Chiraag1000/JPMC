package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterTests {
    @Test
    void totalFeeForCustomer() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Movie test = new Movie("test", Duration.ofMinutes(90), 10, 1);
        theater.addShowing(new Showing(test,
                1,
                LocalDateTime.of(theater.provider.currentDate(),
                        LocalTime.of(9, 0))));
        Customer john = new Customer("John Doe");
        Reservation reservation = theater.reserve(john, 1, 4);
        assertEquals(reservation.totalFee(), 28); // (10-3) * 4 = 28
    }

    @Test
    void printMovieSchedule() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        theater.addShowing(new Showing(turningRed,
                1,
                LocalDateTime.of(theater.provider.currentDate(),
                        LocalTime.of(9, 0))));
        theater.addShowing(new Showing(spiderMan,
                2,
                LocalDateTime.of(theater.provider.currentDate(),
                        LocalTime.of(11, 0))));
        theater.addShowing(new Showing(theBatMan,
                3,
                LocalDateTime.of(theater.provider.currentDate(),
                        LocalTime.of(12, 50))));
        theater.addShowing(new Showing(turningRed,
                4,
                LocalDateTime.of(theater.provider.currentDate(),
                        LocalTime.of(14, 30))));
        theater.addShowing(new Showing(spiderMan,
                5,
                LocalDateTime.of(theater.provider.currentDate(),
                        LocalTime.of(16, 10))));
        theater.addShowing(new Showing(theBatMan,
                6,
                LocalDateTime.of(theater.provider.currentDate(),
                        LocalTime.of(17, 50))));
        theater.addShowing(new Showing(turningRed,
                7,
                LocalDateTime.of(theater.provider.currentDate(),
                        LocalTime.of(19, 30))));
        theater.addShowing(new Showing(spiderMan,
                8,
                LocalDateTime.of(theater.provider.currentDate(),
                        LocalTime.of(21, 10))));
        theater.addShowing(new Showing(theBatMan,
                9,
                LocalDateTime.of(theater.provider.currentDate(),
                        LocalTime.of(23, 0))));
        theater.printSchedule();
        theater.printScheduleJson();
    }
}
