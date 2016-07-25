# javatest
Below are the rest service end points, In order to run the app use the below command

mvn tomcat7:run

http://localhost:8080/eventsApp/event
http://localhost:8080/eventsApp/user

Resource URL's

Events-

GET

Get all events = http://localhost:8080/eventsApp/event
Get event Info by id = http://localhost:8080/eventsApp/event/{eventId}
Gell all users for an event =http://localhost:8080/eventsApp/event/{eventId}/users

PUT

Update Event Info = http://localhost:8080/eventsApp/event
Add user to event = http://localhost:8080/eventsApp/event/{eventId}/users/{userId}

DELETE

Remove user from event = http://localhost:8080/eventsApp/event/{eventId}/users/{userId}
Remove event = http://localhost:8080/eventsApp/event/{eventId}

POST
Create Event = http://localhost:8080/eventsApp/event

Users

GET

Get all users = http://localhost:8080/eventsApp/user
Get  user by Id = http://localhost:8080/eventsApp/user/{userId}
Gell all events for a user =http://localhost:8080/eventsApp/user/{userId}/events

PUT

Update user Info = http://localhost:8080/eventsApp/user

DELETE

Remove user = http://localhost:8080/eventsApp/user/{userId}

POST
Create user = http://localhost:8080/eventsApp/user
