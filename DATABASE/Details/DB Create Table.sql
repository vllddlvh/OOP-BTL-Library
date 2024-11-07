DROP DATABASE IF EXISTS library_demo_contents;
CREATE DATABASE library_demo_contents;

use library_demo_contents;

CREATE TABLE Books
(   
	bookID NVARCHAR(10) NOT NULL,
    b_name NVARCHAR(50) NOT NULL,
    author NVARCHAR(50) NOT NULL,
	category NVARCHAR (50) NOT NULL,
	quantityInStock INT NOT NULL,
    quantityLeft INT NOT NULL,
	CONSTRAINT pk_bookID PRIMARY KEY (bookID)
);

CREATE TABLE Books_Info
(
	bookID NVARCHAR(10) NOT NULL,
	releaseDate DATE ,
	publisher NVARCHAR (50),
    CONSTRAINT pk_bookID PRIMARY KEY (bookID),
    CONSTRAINT fk_Book_Information FOREIGN KEY (bookID) REFERENCES Books(bookID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Books_File 
(
	bookID NVARCHAR(10) NOT NULL,
    PDF MEDIUMBLOB,
    CONSTRAINT pk_bookID PRIMARY KEY (bookID),
    CONSTRAINT fk_Book_File FOREIGN KEY (bookID) REFERENCES Books(bookID) ON DELETE CASCADE ON UPDATE CASCADE
);
-- DROP TABLE Books
-- ----------------------------------------------------------------------------


CREATE TABLE Users
(
	userID NVARCHAR(10),
    password NVARCHAR(50) DEFAULT NULL,
    CONSTRAINT pk_userID PRIMARY KEY (userID)
);
-- DROP TABLE Users

CREATE TABLE Teachers
(
	userID NVARCHAR(10) NOT NULL,
	userName NVARCHAR(50) NOT NULL,
	dateOfBirth DATE NOT NULL,
	contact VARCHAR(15) NOT NULL,
	section NVARCHAR(50)NOT NULL,
	CONSTRAINT pk_studentID PRIMARY KEY (userID),
    CONSTRAINT fk_logInID_Teachers FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE ON UPDATE CASCADE
);
-- DROP Teachers


CREATE TABLE Classes
(
	classID NVARCHAR(50) NOT NULL,
	section NVARCHAR(50) NOT NULL,
	class_name NVARCHAR(50) NOT NULL,
    advisorID NVARCHAR(10),
	CONSTRAINT pk_classID PRIMARY KEY (classID),
    CONSTRAINT fk_Advisor_Teacher FOREIGN KEY (advisorID) REFERENCES Teachers(userID) ON UPDATE CASCADE
);
-- DROP TABLE Classes
-- ---------------------------------------------------------------------------



CREATE TABLE Students
(
	userID NVARCHAR(10) NOT NULL,
	userName NVARCHAR(50) NOT NULL,
	dateOfBirth DATE NOT NULL,
	contact VARCHAR(15) NOT NULL,
	classID NVARCHAR(50) NOT NULL,
	CONSTRAINT pk_studentID PRIMARY KEY (userID),
	CONSTRAINT fk_Students_classID FOREIGN KEY (classID) REFERENCES Classes(classID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_logInID_Students FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE ON UPDATE CASCADE
);
-- DROP Students

CREATE TABLE AdminfirstLogIn
(	
	userID NVARCHAR(10),
    username NVARCHAR(50) NOT NULL,
    password NVARCHAR(50),
    CONSTRAINT pk_userID PRIMARY KEY (userID),
    CONSTRAINT fk_logInID_Admin FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE ON UPDATE CASCADE
);
-- DROP TABLE admin
-- ----------------------------------------------------------------------------



CREATE TABLE Requests 
(
	requestID NVARCHAR(15) NOT NULL,
	userID NVARCHAR (10) NOT NULL,
	bookID NVARCHAR(10) NOT NULL,
	quantityBorrow INT NOT NULL,
	borrowDate DATE NOT NULL,
	returnDate DATE DEFAULT NULL,
	CONSTRAINT pk_requestID PRIMARY KEY (requestID),
	CONSTRAINT fk_Requests_studentID FOREIGN KEY (userID) REFERENCES Students(userID) ON DELETE CASCADE ON UPDATE CASCADE ,
	CONSTRAINT fk_Requests_bookID FOREIGN KEY (bookID) REFERENCES Books(bookID) ON DELETE CASCADE ON UPDATE CASCADE
);
-- DROP TABLE Requests