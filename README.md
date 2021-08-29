# social-sports
Sports Booking App using Java 9 reactive streams

Problem Statement:

Social sports center

Customer is an owner of a sports center with 3 tennis courts. Players can come and register interest to play for a particular day, once 4 players are interested one court could be occupied. 
Once a court is occupied i.e. 4 players shown interest to play, a notification is sent to players to indicate that game is on. Notification means just printing in console.
Once 3 courts are occupied for a day, further registration will be rejected for that day. 

Assumptions:
- Changes after showing interest is not allowed
- Court is occupied for full day, i.e. only one session per day


Booking Controller has two endpoints exposed:

- book(playerName, bookingDate, facilityType)
  This will return the async response with the Booking object with Id.
- status(bookingId)
  This will let the customer know about the status of their booking using the bookingId

![image](https://user-images.githubusercontent.com/8542350/131237477-db7d0005-fbd9-40ea-919c-b67870ae5628.png)
