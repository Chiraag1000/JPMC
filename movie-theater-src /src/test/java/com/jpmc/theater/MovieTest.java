package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * testing the functionalitly of the 'Movie' class, there is not much functionality here.
 */
public class MovieTest {
    @Test
    public void isSpecialTest(){
        Movie movie = new Movie("test", Duration.ofMinutes(10), 10.0, 1);
        Movie movie2 = new Movie("test", Duration.ofMinutes(10), 10.0, 2);
        movie.setSpecial();
        assertTrue(movie.isSpecial());
        assertFalse(movie2.isSpecial());
    }
    @Test
    public void movieSpecialCanChangeTest(){
        Movie movie = new Movie("test", Duration.ofMinutes(10), 10.0, 1);
        Movie movie2 = new Movie("test", Duration.ofMinutes(10), 10.0, 2);
        movie.setSpecial();
        assertTrue(movie.isSpecial());
        movie2.setSpecial();
        assertTrue(movie2.isSpecial());
        assertFalse(movie.isSpecial());
    }
}
