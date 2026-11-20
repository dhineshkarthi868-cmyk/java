CREATE DATABASE IF NOT EXISTS java_assignment5;
USE java_assignment5;
CREATE TABLE student(id INT PRIMARY KEY, name VARCHAR(100) NOT NULL, reg_no VARCHAR(20) UNIQUE NOT NULL);
CREATE TABLE course(id INT PRIMARY KEY, title VARCHAR(120) NOT NULL);
CREATE TABLE marks(student_id INT NOT NULL, course_id INT NOT NULL, score INT NOT NULL,
 PRIMARY KEY(student_id,course_id), FOREIGN KEY(student_id) REFERENCES student(id),
 FOREIGN KEY(course_id) REFERENCES course(id));
