CREATE SCHEMA food_log_database;

CREATE TABLE food_log_database.food_log (
entry_id INT NOT NULL AUTO_INCREMENT,
entry_date DATE,
food_name VARCHAR(30),
meal_type VARCHAR(10),
serving_quantity FLOAT,
entry_notes VARCHAR(255),
PRIMARY KEY(entry_id)
);

CREATE TABLE food_log_database.calorie_table (
food_name VARCHAR(30) NOT NULL,
calories_per_serving INT,
food_category VARCHAR(16),
PRIMARY KEY (food_name)
);

CREATE USER 'foodLog'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON food_log_database.* TO 'foodLog'@'localhost';