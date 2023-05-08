#  Notes:
*The original project descrption is below these notes*

*These notes explain some code design choices I wanted to correct and my corrections and assumptions of the functional requirements listed in the instructions*

1. Theater having an `ArrayList` of showings is good, however the showings details being printed out means 'Showing' class should contain all necessary information such as timings, *and* final price since it contains show time which is needed for the new requirements.
2. Reservations need to be able to declined if a specific showing is booked to be full. I set a limit for 30, but if this were a real theater, actual seats per showing would have to be determined
3. The instructions did not say if there will be one special movie, either per day, or even over long periods of time, I assumed a special movie would be able to be set at the discretion of a system admin, I added a function to allow that.
4. Design change: movie code special will be a static vairable, each movie has a special code unique to it
   1. if the system needed to scale up and be more autonomous we could design a unique id generator for each movie with a fixed time interval such that after a certain point in time we remove certain movies and their id's are no longer in use. then each day a specific movie id will be tagged as the movie special of the day
5. The statement `$3 discount for the movie showing 1st of the day` was extremely vague and contained improper grammar.
   1. I understood this to mean: for any given theater schedule, the first showing, regardless of the movie, will be 3$ off
   2. The same assumption will be applied for the 2$ discount of the 2nd showing of the day
6. The 'schedule' that is currently represented by an 'ArrayList' only makes sense if it contains schedules for a single day. If this were to be operated in reality, at the start of the new day, the schedule would be cleared and showings added, I added a function for that as well. Alternatively there could be a schedule class created to function as schedules for specific days, depending on how the 'Special Movie' is determined per day or all time, that could be a better approach
7. The sequence of the day for showings is manually entered. While this could cause issues in a production environment due to human error, I am the only one inputting values for this specific take home so I will not automate that portion 
8. The 'description' parameter of movie class is unused and necessary, a schedule usually does not have space to show a description.
9. Customer ID will be changed to be a staticly increased ID number, every time a new customer is created, limitation will be 2^31 possible id's, however its unlikely that many customers will come to a single theater. If some people were to make multiple accounts it would still be unlikely, it would have to be something that is watched by management
10. The "theater" class has movies and showings being instantiated inside of it, I assume this would be for testing but that is not considered a good practice, that was removed.
11. Printing in JSON format was done by utilizing javax.json library

# Introduction

This is a poorly written application, and we're expecting the candidate to greatly improve this code base.

## Instructions
* **Consider this to be your project! Feel free to make any changes**
* There are several deliberate design, code quality and test issues in the current code, they should be identified and resolved
* Implement the "New Requirements" below
* Keep it mind that code quality is very important
* Focus on testing, and feel free to bring in any testing strategies/frameworks you'd like to implement
* You're welcome to spend as much time as you like, however, we're expecting that this should take no more than 2 hours

## `movie-theater`

### Current Features
* Customer can make a reservation for the movie
  * And, system can calculate the ticket fee for customer's reservation
* Theater have a following discount rules
  * 20% discount for the special movie
  * $3 discount for the movie showing 1st of the day
  * $2 discount for the movie showing 2nd of the day
* System can display movie schedule with simple text format

## New Requirements
* New discount rules; In addition to current rules
  * Any movies showing starting between 11AM ~ 4pm, you'll get 25% discount
  * Any movies showing on 7th, you'll get 1$ discount
  * The discount amount applied only one if met multiple rules; biggest amount one
* We want to print the movie schedule with simple text & json format