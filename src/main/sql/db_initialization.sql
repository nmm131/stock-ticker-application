
/* delete tables if they exist already - ensuring a clean db*/
DROP TABLE IF EXISTS stocks.userstocks CASCADE;
DROP TABLE IF EXISTS stocks.person CASCADE;

/** creates a table to store a list of people */
CREATE TABLE stocks.person
(
  ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(256) NOT NULL,
  last_name VARCHAR(256) NOT NULL,
  birth_date DATETIME NOT NULL
);

/** A list of people and their stocks*/
CREATE TABLE stocks.userstocks
(
  ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  person_id INT NOT NULL,
  stock_symbol VARCHAR(4) NOT NULL,
  FOREIGN KEY (person_id) REFERENCES person (ID)
);

/** now add some sample data */

INSERT INTO stocks.person (first_name,last_name,birth_date) VALUES ('Drew', 'Hope', '1999/10/10');
INSERT INTO stocks.person (first_name,last_name,birth_date) VALUES ('Lang', 'Heckman', '1959/11/11');
INSERT INTO stocks.person (first_name,last_name,birth_date) VALUES ('Lucy', 'Jones', '2010/1/1');
INSERT INTO stocks.person (first_name,last_name,birth_date) VALUES ('Stew', 'Hammer', '1990/3/28');
INSERT INTO stocks.person (first_name,last_name,birth_date) VALUES ('Dan', 'Lane', '1986/4/18');

INSERT INTO stocks.userstocks (ID, person_id, stock_symbol) VALUES (1, 1, 'APPL');
INSERT INTO stocks.userstocks (ID, person_id, stock_symbol) VALUES (2, 1, 'GOOG');
INSERT INTO stocks.userstocks (ID, person_id, stock_symbol) VALUES (3, 2, 'AMZN');
INSERT INTO stocks.userstocks (ID, person_id, stock_symbol) VALUES (4, 3, 'APPL');
INSERT INTO stocks.userstocks (ID, person_id, stock_symbol) VALUES (5, 3, 'GOOG');
INSERT INTO stocks.userstocks (ID, person_id, stock_symbol) VALUES (6, 3, 'AMZN');
INSERT INTO stocks.userstocks (ID, person_id, stock_symbol) VALUES (7, 4, 'APPL');

