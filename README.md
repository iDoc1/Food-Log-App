**FOOD LOG APP**

**INTRODUCTION**  
This app allows a user to record each food they eat during the day.
The user can also enter caloric information about the foods that they
eat, which allows the user to run a data report to view data regarding
the food they have eaten over the past day or month. This app 
implements all CRUD functions allowing the user to insert, view, 
update, and delete food log entries through a command line 
interface.

I built this application to learn the fundamentals of using the JDBC
API and MySQL, strengthen my Java coding skills, and to further 
enhance my experience with Object-Oriented Programming.

**PREREQUISITES**  
Before running this program, you must have Java, Maven, and MySQL installed.
I built this program using Java 11, but Java 7+ should be sufficient.
Port 3306 must also be free since this is the default local port used
by MySQL.

**HOW TO RUN APP**  
- First, run the following scripts in MySQL on a local connection on 
  port 3306:

CREATE SCHEMA food_log_database;

CREATE TABLE food_log_database.food_log (  
entry_id INT NOT NULL AUTO_INCREMENT,  
entry_date DATE,  
food_name VARCHAR(30),  
meal_type VARCHAR(10),  
serving_quantity FLOAT,  
entry_notes VARCHAR(255),  
PRIMARY KEY(entry_id)  
) ;

CREATE TABLE food_log_database.calorie_table (  
food_name VARCHAR(30) NOT NULL,  
calories_per_serving INT,  
food_category VARCHAR(16),  
PRIMARY KEY (food_name)  
);

CREATE USER 'foodLog'@'localhost' IDENTIFIED BY 'admin';  
GRANT ALL PRIVILEGES ON food_log_database.* TO 'foodLog'@'localhost';

- The above scripts will create the food log database and tables needed
for the app to function. A user named 'foodLog' with the password 
  'admin' is also created for the app to use. This can be changed if 
  needed but will require changing the above script, and the 
  connection string in the FoodLogConnection.java file.
- Clone this GitHub repo to a location on your local machine.
- Navigate to the local repo and install all dependencies using Maven. 
This can be accomplished using the 'mvn package' command in the terminal.
- Finally, compile and run the FoodLogMain.java file. This file is the
entry point for the user and will allow you to begin using the app.
  
Thanks for checking out my project!