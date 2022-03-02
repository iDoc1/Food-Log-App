# Food Log App

### Introduction
This app allows a user to record details about foods they ate on specific dates
and run a data report to view historical data about eating habits. The main
functionality is described below:
- User can add meals they ate on specific dates
- User view meal history on specific dates or within a range of dates
- User can view all entries containing a specific food
- The user can edit or delete any food log entry
- Caloric and food type details can be added for a specific food
- A historical data report can be viewed for meals eaten yesterday or over the past week


I built this app as a personal project to learn the fundamentals of using 
the JDBC API and MySQL, strengthen my Java coding skills, and to further 
my experience with Object-Oriented Programming.

### Before Installing
Before using this program, you must have Java, Maven, and MySQL installed.
I built this program using Java 11, but Java 7+ should be sufficient.

### How to Install 
1. Navigate to the "sql" directory and run the "database_definition.sql" script
to build the backend database.
2. Clone this GitHub repo to a location on your local machine.
3. Navigate to the local repo and install all dependencies using Maven. 
This can be accomplished using the following command:
   ~~~
   mvn package
   ~~~
   
4. Finally, compile and run the FoodLogMain.java file. This file is the
entry point for the user and will allow you to begin using the app.
  
### Using the App
The below screenshots show a basic overview of the app's functionality.  
  
Main Menu  
![Main Menu](/screenshots/welcome_screen.png)  
Add an entry to the food log  
![Add an entry](/screenshots/add_entry.png)  
View yesterday's meals  
![View yesterday meals](/screenshots/view_yesterday_meals.png)  
Add details about a specific food  
![Add food details](/screenshots/add_food_details.png)  
View data report for yesterday  
![View data report](/screenshots/data_report.png)  
