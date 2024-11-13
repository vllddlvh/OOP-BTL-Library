DROP DATABASE IF EXISTS Library_2nd_Edition;
CREATE DATABASE Library_2nd_Edition;

use Library_2nd_Edition;

CREATE TABLE User (
	ID VARCHAR(10),
    
    primary key (ID)
);

CREATE TABLE Login (
	ID VARCHAR(10),
    password VARCHAR(30),
    Role ENUM('Staff', 'Member'),
    
    primary key (ID),
    foreign key (ID) references User(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Member (
	ID VARCHAR(10),
    firstName VARCHAR(10) NOT NULL,
    lastName VARCHAR(20) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    dateOfBirth DATE NOT NULL,
    
    primary key (ID),
    foreign key (ID) references User(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Staff (
	ID VARCHAR(10),
    firstName VARCHAR(10) NOT NULL,
    lastName VARCHAR(20) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    jobTitle VARCHAR(20),
    IntroducerID VARCHAR(10),
    
    primary key (ID),
    foreign key (ID) references User(ID) ON UPDATE CASCADE ON DELETE CASCADE,
    foreign key (IntroducerID) references Staff(ID) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Documents (
	ID VARCHAR(15),
    Title VARCHAR(50) not null,
    genre ENUM('Thesis', 'Book') not null,
    totalQuantity INT(5) not null,
    quantityLeft INT(5) not null,
    primary key (ID)
);

CREATE TABLE Books (
	ISBN VARCHAR(15),
    author VARCHAR(50) NOT NULL,
    publisher VARCHAR(50),
    releaseYear YEAR,
    category ENUM('Fiction', 'Non-fiction'),
    
    primary key (ISBN),
    foreign key (ISBN) references Documents(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Thesis (
	ID VARCHAR(15),
	writerID VARCHAR(10),
    advisor VARCHAR(50),
    fieldOfStudy VARCHAR(50),
    Description VARCHAR(100),
    
    primary key (ID),
    foreign key (ID) references Documents(ID) ON UPDATE CASCADE ON DELETE CASCADE,
    foreign key (writerID) references User(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- CREATE TABLE storedDocument (
-- 	ID VARCHAR(15),
--     Cover BLOB,
--     PDF MEDIUMBLOB,
--     
--     primary key (ID),
--     foreign key (ID) references Documents(ID) ON UPDATE CASCADE ON DELETE CASCADE
-- );


CREATE TABLE Request (
	requestID VARCHAR(15),
    userID VARCHAR(10),
    documentID VARCHAR(15),
    quantityBorrow INT(5),
    borrowDate DATE not null,
    returnDate DATE,
    
    primary key (requestID),
    foreign key (userID) references User(ID) ON UPDATE CASCADE ON DELETE CASCADE,
    foreign key (documentID) references Documents(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- ------------------------------------------------------------------------------------------


DROP TRIGGER IF EXISTS genMemberID;
DROP TRIGGER IF EXISTS genStaffID;
DELIMITER //
CREATE TRIGGER genStaffID
BEFORE INSERT ON Staff
FOR EACH ROW
BEGIN
	INSERT INTO User(ID) VALUES (new.ID);
    INSERT INTO Login(ID, password, Role) VALUES (new.ID, new.ID, 'Staff');
END;
// DELIMITER ;

DELIMITER //
CREATE TRIGGER genMemberID
BEFORE INSERT ON Member
FOR EACH ROW
BEGIN
	INSERT INTO User(ID) VALUES (new.ID);
    INSERT INTO Login(ID, password, Role) VALUES (new.ID, new.ID, 'Member');
END;
// DELIMITER ;

/*
*
*
*
*/

DROP TRIGGER IF EXISTS genRequestID;

DELIMITER //
CREATE TRIGGER genRequestID
BEFORE INSERT ON Request
FOR EACH ROW
BEGIN
	DECLARE newMonth INT;
    DECLARE month_prefix CHAR(3);
    DECLARE count INT;
    
    SET newMonth = MONTH(NEW.borrowDate);
    
    SET month_prefix = CASE newMonth
        WHEN 1 THEN 'Jan' WHEN 2 THEN 'Feb'
        WHEN 3 THEN 'Mar' WHEN 4 THEN 'Apr'
        WHEN 5 THEN 'May' WHEN 6 THEN 'Jun'
		WHEN 7 THEN 'Jul' WHEN 8 THEN 'Aug'
        WHEN 9 THEN 'Sep' WHEN 10 THEN 'Oct'
        WHEN 11 THEN 'Nov' WHEN 12 THEN 'Dec'
    END;
    
    SET count = (SELECT COUNT(*) + 1 FROM Request WHERE MONTH(borrowDate) = newMonth);
    
    SET NEW.requestID = CONCAT(YEAR(NEW.borrowDate), month_prefix, count);
END;
// DELIMITER ;

/*
*
*
*
*/

DROP TRIGGER IF EXISTS makeQuantityLeft;
DROP TRIGGER IF EXISTS minusQuantityLeft;
DROP TRIGGER IF EXISTS plusQuantityLeft;

DELIMITER //
CREATE TRIGGER makeQuantityLeft
BEFORE INSERT ON Documents
FOR EACH ROW
BEGIN
    SET NEW.quantityLeft = NEW.totalQuantity;
END;
// DELIMITER ;

DELIMITER //
CREATE TRIGGER minusQuantityLeft
BEFORE INSERT ON Request
FOR EACH ROW
BEGIN
    -- Cập nhật lại số lượng tồn kho sau khi mượn sách
    IF new.returnDate is null THEN
		UPDATE Documents
		SET quantityLeft = quantityLeft - NEW.quantityBorrow
		WHERE ID = NEW.documentID;
    END IF;
END;
// DELIMITER ;

DELIMITER //
CREATE TRIGGER plusQuantityLeft
BEFORE UPDATE ON Request
FOR EACH ROW
BEGIN
    UPDATE Documents
    SET quantityLeft = quantityLeft + NEW.quantityBorrow
    WHERE ID = NEW.documentID;
END;
// DELIMITER ;


-- ------------------------------------------------------------------------------------------

SET SQL_SAFE_UPDATES = 0;
DELETE FROM User;
DELETE FROM Documents;
SET SQL_SAFE_UPDATES = 1;

INSERT INTO Member (ID, firstName, lastName, contact, dateOfBirth) VALUES 
('23021686', 'Sonw', 'Ngx', 'NgxS@ops.com', '2005-01-14'),
('U003', 'John', 'Doe', 'john.doe@example.com', '1995-06-15'), 
('U004', 'Jane', 'Smith', 'jane.smith@example.com', '1997-09-30'), 
('U005', 'Emily', 'Johnson', 'emily.johnson@example.com', '1996-12-22');

INSERT INTO Staff (ID, firstName, lastName, contact, jobTitle, IntroducerID) VALUES 
('U001', 'Robert', 'Brown', 'robert.brown@example.com', 'Librarian', NULL), 
('U002', 'Alice', 'Davis', 'alice.davis@example.com', 'Assistant Librarian', 'U001'),
('1401', 'Sonw', 'Ngx', 'NgxS@ops.com', 'Special Agent', NULL);

INSERT INTO Documents (ID, Title, genre, totalQuantity) VALUES 
('D001', 'Effective Java', 'Book', 10), 
('D002', 'The Great Gatsby', 'Book', 5), 
('D003', 'Mastering Algorithms', 'Book', 7), 
('D004', 'Data Science Thesis 2023', 'Thesis', 2), 
('D005', 'Machine Learning Advances', 'Thesis', 3);

INSERT INTO Books (ISBN, author, publisher, releaseYear, category) VALUES 
('D001', 'Joshua Bloch', 'Addison-Wesley', 2018, 'Non-fiction'), 
('D002', 'F. Scott Fitzgerald', 'Charles Scribner\'s Sons', 1925, 'Fiction'), 
('D003', 'Thomas Cormen', 'MIT Press', 2009, 'Non-fiction');


INSERT INTO Thesis (ID, writerID, advisor, fieldOfStudy, Description) VALUES 
('D004', 'U003', 'Dr. Smith', 'Data Science', 'A comprehensive study on data science trends in 2023'), 
('D005', 'U004', 'Prof. Johnson', 'Machine Learning', 'Exploration of advancements in machine learning algorithms');

INSERT INTO Request (userID, documentID, quantityBorrow, borrowDate) VALUES 
('U003', 'D001', 1, '2024-01-15'), 
('U004', 'D002', 1, '2024-02-01'), 
('U005', 'D003', 1, '2024-03-05'), 
('U003', 'D004', 1, '2024-03-10'), 
('U004', 'D005', 1, '2024-04-15');



-- ------------------------------------------------------------------------------------------


DROP PROCEDURE IF EXISTS getMemberInfo;
DELIMITER //
CREATE PROCEDURE getMemberInfo (IN findID VARCHAR(10))
BEGIN
	SELECT ID, firstName, lastName, contact, dateOfBirth 
    FROM Member 
    WHERE ID = findID;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS getStaffInfo;
DELIMITER //
CREATE PROCEDURE getStaffInfo (IN findID VARCHAR(10))
BEGIN
	SELECT ID, firstName, lastName, contact, jobTitle, introducerID
    FROM Staff
    WHERE ID = findID;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS searchThesis;
DELIMITER //
CREATE PROCEDURE searchThesis (IN 
	titleKey VARCHAR(50),
    fieldOfStudyKey VARCHAR(50),
    byWriterID VARCHAR(10),
    byAdvisor VARCHAR(50)
)
BEGIN
	SELECT 
		thesis.ID AS ID,
		documents.title AS title,
		documents.quantityLeft AS quantityAvailable,
		thesis.writerID AS writerID,
		thesis.advisor AS advisor,
		thesis.fieldOfStudy AS fieldOfStudy,
		thesis.Description AS Description
	FROM thesis left join documents using(ID)
    WHERE if (byWriterID is null, true, thesis.writerID = byWriterID)
    AND thesis.advisor LIKE CONCAT('%', byAdvisor, '%')
    AND documents.title LIKE CONCAT('%', titleKey, '%')
    AND thesis.fieldOfStudy LIKE CONCAT('%', fieldOfStudyKey, '%');
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS searchBook;
DELIMITER //
CREATE PROCEDURE searchBook (IN 
	titleKey VARCHAR(50),
    authorKey VARCHAR(50),
    releaseYearKey YEAR,
    categoryKey ENUM('Fiction', 'Non-fiction')
)
BEGIN
	SELECT 
		books.ISBN AS ISBN,
		documents.title AS title,
		documents.quantityLeft AS quantityAvailable,
		books.author AS author,
		books.publisher AS publisher,
		books.releaseYear AS releaseYear,
		books.category AS category
	FROM books left join documents ON (books.isbn = documents.ID)
	WHERE documents.title LIKE CONCAT('%', titleKey, '%')
    AND books.author LIKE CONCAT('%', authorKey, '%')
    AND if(releaseYearKey is null, true, books.releaseYear = releaseYearKey)
    AND if(categoryKey is null, true, books.category = categoryKey);
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS searchDocument;
DELIMITER //
CREATE PROCEDURE searchDocument (IN documentID VARCHAR(15))
BEGIN
	IF (SELECT genre FROM Documents WHERE ID = documentID) = 'Book'
    THEN
		SELECT 
			books.ISBN AS ISBN,
			documents.title AS title,
			documents.quantityLeft AS quantityAvailable,
			books.author AS author,
			books.publisher AS publisher,
			books.releaseYear AS releaseYear,
			books.category AS category,
            documents.genre
		FROM books left join documents ON (books.isbn = documents.ID)
        WHERE books.isbn = documentID;
	ELSE
		SELECT 
			thesis.ID,
			documents.title,
			documents.quantityLeft,
			thesis.writerID,
			thesis.advisor,
			thesis.fieldOfStudy,
			thesis.Description,
            documents.genre
		FROM thesis left join documents using(ID)
        WHERE thesis.ID = documentID;
    END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS borrowDocument;
DELIMITER //
CREATE PROCEDURE borrowDocument (IN s_ID VARCHAR(10), b_ID VARCHAR(10), borrowQuantity INT)
BEGIN
	DECLARE quantityAvailable INT;
	SELECT quantityLeft INTO quantityAvailable
    FROM Documents WHERE ID = b_ID;
    
	IF (borrowQuantity <= quantityAvailable)
    THEN 
		INSERT INTO Request (userID, documentID, quantityBorrow, borrowDate)
		VALUES (s_ID, b_ID, borrowQuantity, CURRENT_DATE());
        SELECT true AS Result;
	ELSE 
		SELECT false AS Result;
	END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS returnDocument;
DELIMITER //
CREATE PROCEDURE returnDocument (IN r_ID VARCHAR(15), s_ID VARCHAR(10))
BEGIN
	IF s_ID = (SELECT userID FROM Request WHERE requestID = r_ID)
    THEN 
		UPDATE Request
		SET returnDate = CURRENT_DATE()
		WHERE requestID = r_ID;
        
        SELECT true AS Result;
	ELSE 
		SELECT false AS Result;
	END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS checkForUnreturn_DocumentList;
DELIMITER //
CREATE PROCEDURE checkForUnreturn_DocumentList(IN s_ID VARCHAR(10))
BEGIN
    SELECT
		requestID,
        documentID,
        quantityBorrow,
        borrowDate
	FROM Request
	WHERE userID = s_ID AND returnDate IS NULL;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS checkForUnreturn_StudentList;
DELIMITER //
CREATE PROCEDURE checkForUnreturn_StudentList(IN b_ID VARCHAR(10))
BEGIN
   
	 SELECT requestID,
			userID,
			quantityBorrow,
			borrowDate
	FROM Request
	WHERE documentID = b_ID AND returnDate IS NULL;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS checkForHistory_DocumentList;
DELIMITER //
CREATE PROCEDURE checkForHistory_DocumentList(IN s_ID VARCHAR(10))
BEGIN
    SELECT requestID,
			documentID,
			quantityBorrow,
			borrowDate,
			returnDate
	FROM Request
	WHERE userID = s_ID
    ORDER BY '2024-10-30' - returnDate ASC, borrowDate DESC;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS addBook;
DELIMITER //
CREATE PROCEDURE addBook (IN 
	newISBN VARCHAR(15),
    newTitle VARCHAR(50),
    theAuthor VARCHAR(50),
	storedQuantity INT(5),
    theCategory ENUM('Fiction', 'Non-fiction'),
    thePublisher VARCHAR(50),
    theReleaseYear YEAR
)
BEGIN
	IF (SELECT COUNT(*) From Documents WHERE ID = newISBN) = 0
    
	THEN 
		INSERT INTO Documents (ID, Title, genre, totalQuantity) 
			VALUES (newISBN, newTitle, 'Book', storedQuantity);
		INSERT INTO Books (ISBN, author, publisher, releaseYear, category) 
			VALUES (newISBN, theAuthor, thePublisher, theReleaseYear, theCategory);
		SELECT true as Result;
        
	ELSE 
		SELECT false as Result; -- if the ID already exists
    END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS addThesis;
DELIMITER //
CREATE PROCEDURE addThesis (IN 
	newID VARCHAR(15),
    newTitle VARCHAR(50),
    theWriterID VARCHAR(10),
    theAdvisor VARCHAR(50),
    newFieldOfStudy VARCHAR(50),
    smallDescription VARCHAR(100),
	storedQuantity INT(5)
)
BEGIN
	IF (SELECT COUNT(*) From Documents WHERE ID = newID) = 0
    
	THEN 
		INSERT INTO Documents (ID, Title, genre, totalQuantity) 
			VALUES (newID, newTitle, 'Thesis', storedQuantity);
		INSERT INTO Thesis (ID, writerID, advisor, fieldOfStudy, Description)
			VALUES (newID, theWriterID, theAdvisor, newFieldOfStudy, smallDescription);
		SELECT true as Result;
        
	ELSE 
		SELECT false as Result; -- if the ID already exists
    END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS loadMoreDocumentCopies;
DELIMITER //
CREATE PROCEDURE loadMoreDocumentCopies (IN documentID VARCHAR(15), quantityChange INT ,staffWhoDid NVARCHAR(10))
BEGIN
	IF (SELECT COUNT(*) FROM Staff WHERE ID = staffWhoDid) <> 0
	THEN 
		UPDATE Documents
        SET totalQuantity = totalQuantity + quantityChange,
			quantityLeft = quantityLeft + quantityChange
        WHERE ID = documentID;
    END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS deleteDocument;
DELIMITER //
CREATE PROCEDURE deleteDocument (IN delDocumentID VARCHAR(15), whoDelete NVARCHAR(10))
BEGIN
	IF (SELECT COUNT(*) FROM Staff WHERE ID = whoDelete) <> 0
	THEN 
		DELETE FROM Documents WHERE ID = delDocumentID;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS login;
DELIMITER //
CREATE PROCEDURE login (IN userID VARCHAR(10), passwordTry VARCHAR(30))
BEGIN
	DECLARE thisRole VARCHAR(10);
    DECLARE thisPassword VARCHAR(30);
    
    SELECT Role, password 
    INTO thisRole, thisPassword
    FROM login WHERE ID = userID;
    
    IF (thisPassword = passwordTry) THEN 
		IF (thisPassword = userID) THEN 
			SELECT thisRole AS Role, true AS firstTry;
        ELSE
			SELECT thisRole AS Role, false AS firstTry;
        END IF;
	ELSE 
		SELECT null AS Role, false AS firstTry;
    END IF;
END;
// DELIMITER ;



DROP PROCEDURE IF EXISTS changePassword;
DELIMITER //
CREATE PROCEDURE changePassword (IN userID VARCHAR(10), oldPassword VARCHAR(30), newPassword VARCHAR(30))
BEGIN
	IF (newPassword <> userID)
    THEN
		UPDATE login
		SET password = newPassword
		WHERE ID = userID AND password = oldPassword;
    
		SELECT ROW_COUNT() AS Result;
	ELSE 
		SELECT 0 AS Result;
	END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS addMember;
DELIMITER //
CREATE PROCEDURE addMember (IN 
	newID VARCHAR(10),
    1stName VARCHAR(10),
    nastName VARCHAR(20),
    contact VARCHAR(50),
    BirthDate DATE
)
BEGIN
	IF (SELECT COUNT(*) From User WHERE ID = newID) = 0
    
	THEN 
		INSERT INTO Member (ID, firstName, lastName, contact, dateOfBirth) 
			VALUES (newID, 1stName, nastName, contact, BirthDate);
		SELECT true as Result;
        
	ELSE 
		SELECT false as Result; -- if duplicate userID
    END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS addStaff;
DELIMITER //
CREATE PROCEDURE addStaff (IN 
	newID VARCHAR(10),
    1stName VARCHAR(10),
    nastName VARCHAR(20),
    contax VARCHAR(50),
    job VARCHAR(20),
    introducer VARCHAR(10)
)
BEGIN
	IF (SELECT COUNT(*) From User WHERE ID = newID) = 0 AND (SELECT COUNT(*) FROM Staff WHERE ID = introducer) = 1
    
	THEN 
		INSERT INTO Staff (ID, firstName, lastName, contact, jobTitle, IntroducerID) 
			VALUES (newID, 1stName, nastName, contax, job, introducer);
		SELECT true as Result;
        
	ELSE 
		SELECT false as Result; -- if duplicate userID
    END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS deleteUser;
DELIMITER //
CREATE PROCEDURE deleteUser (IN delID NVARCHAR(10), whoDelete NVARCHAR(10))
BEGIN
	IF (SELECT COUNT(*) FROM Staff WHERE ID = whoDelete)
	THEN DELETE FROM User WHERE ID = delID;
    END IF;
END;
// DELIMITER ;