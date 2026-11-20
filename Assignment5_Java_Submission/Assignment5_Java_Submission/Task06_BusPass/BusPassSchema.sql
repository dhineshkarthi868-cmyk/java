CREATE DATABASE IF NOT EXISTS java_assignment5;
USE java_assignment5;
CREATE TABLE IF NOT EXISTS bus_pass (
 pass_id VARCHAR(20) PRIMARY KEY,
 reg_no VARCHAR(20) NOT NULL,
 name VARCHAR(100) NOT NULL,
 route_no INT NOT NULL,
 amount DECIMAL(10,2) NOT NULL CHECK (amount >= 0)
);
