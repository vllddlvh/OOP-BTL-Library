DROP DATABASE IF EXISTS library_demo_contents;
CREATE DATABASE library_demo_contents;

use library_demo_contents;

CREATE TABLE Books
(   
	bookID NVARCHAR(10) NOT NULL,
    b_name NVARCHAR(50) NOT NULL,
    author NVARCHAR(50) NOT NULL,
	category NVARCHAR (50) NOT NULL,
    releaseDate DATE,
    publisher NVARCHAR (50),
	quantityInStock INT NOT NULL,
    quantityLeft INT NOT NULL,
    PDF MEDIUMBLOB,
	CONSTRAINT pk_bookID PRIMARY KEY (bookID)
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


-- DROP Teachers


CREATE TABLE Classes
(
	classID NVARCHAR(50) NOT NULL,
	section NVARCHAR(50) NOT NULL,
	class_name NVARCHAR(50) NOT NULL,
    advisorID NVARCHAR(10),
	CONSTRAINT pk_classID PRIMARY KEY (classID)
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
    age INT NOT NULL,
	CONSTRAINT pk_studentID PRIMARY KEY (userID),
	CONSTRAINT fk_Students_classID FOREIGN KEY (classID) REFERENCES Classes(classID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_logInID_Students FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE ON UPDATE CASCADE
);
-- DROP Students

CREATE TABLE Admin
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
	CONSTRAINT fk_Requests_studentID FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE ON UPDATE CASCADE ,
	CONSTRAINT fk_Requests_bookID FOREIGN KEY (bookID) REFERENCES Books(bookID) ON DELETE CASCADE ON UPDATE CASCADE
);
-- DROP TABLE Requests

-- ----------------------------------------------------------------------------




DELIMITER //
CREATE TRIGGER genUserID_Student
BEFORE INSERT ON Students
FOR EACH ROW
BEGIN
	INSERT INTO Users(userID) VALUES (NEW.userID);
END;
// DELIMITER ;


DELIMITER //
CREATE TRIGGER genUserID_Admin
BEFORE INSERT ON Admin
FOR EACH ROW
BEGIN
	DECLARE ID VARCHAR(10);
    SET ID = NEW.userID;
	INSERT INTO Users(userID) VALUES (ID);
END;
// DELIMITER ;


DELIMITER //

CREATE TRIGGER genRequestID
BEFORE INSERT ON Requests
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
    
    SET count = (SELECT COUNT(*) + 1 FROM Requests WHERE MONTH(borrowDate) = newMonth);
    
    SET NEW.requestID = CONCAT(YEAR(NEW.borrowDate), month_prefix, count);
END //

DELIMITER ;


DELIMITER //
CREATE TRIGGER makeQuantityLeft
BEFORE INSERT ON Books
FOR EACH ROW
BEGIN
    SET NEW.quantityLeft = NEW.quantityInStock;
END;
// DELIMITER ;

DROP TRIGGER minusQuantityLeft;
DROP TRIGGER plusQuantityLeft;

DELIMITER //

CREATE TRIGGER minusQuantityLeft
BEFORE INSERT ON Requests
FOR EACH ROW
BEGIN
    DECLARE oldQuantityLeft INT;
    
    -- Lấy số lượng tồn kho hiện tại của sách
    SELECT quantityLeft INTO oldQuantityLeft
    FROM Books
    WHERE bookID = NEW.bookID;
    
    
    -- Cập nhật lại số lượng tồn kho sau khi mượn sách
    UPDATE Books
    SET quantityLeft = oldQuantityLeft - NEW.quantityBorrow
    WHERE bookID = NEW.bookID;

END;
// DELIMITER ;

DELIMITER //
CREATE TRIGGER plusQuantityLeft
BEFORE UPDATE ON Requests
FOR EACH ROW
BEGIN
    DECLARE oldQuantityLeft INT;
    
    -- Lấy số lượng tồn kho hiện tại của sách
    SELECT quantityLeft INTO oldQuantityLeft
    FROM Books
    WHERE bookID = NEW.bookID;
    
    -- Điều chỉnh lại số lượng tồn kho sau khi mượn sách
    UPDATE Books
    SET quantityLeft = oldQuantityLeft + OLD.quantityBorrow
    WHERE bookID = NEW.bookID;
    
END;
// DELIMITER ;


-- ------------------------------------------------------------------------------

-- Xóa thằng cha to nhất thì sẽ xóa hết toàn bộ các bảng còn lại
SET SQL_SAFE_UPDATES = 0;
DELETE FROM Users;
DELETE FROM Classes;
DELETE FROM Books;
SET SQL_SAFE_UPDATES = 1;


INSERT INTO Books (bookID, b_name, author, releaseDate, publisher, category, quantityInStock) VALUES
('B001', 'The Great Gatsby', 'F. Scott Fitzgerald', '1925-04-10', 'Scribner', 'Fiction', 5),
('B002', '1984', 'George Orwell', '1949-06-08', 'Secker & Warburg', 'Dystopian', 8),
('B003', 'To Kill a Mockingbird', 'Harper Lee', '1960-07-11', 'J.B. Lippincott & Co.', 'Fiction', 10),
('B004', 'Moby Dick', 'Herman Melville', '1851-10-18', 'Harper & Brothers', 'Adventure', 7),
('B005', 'Pride and Prejudice', 'Jane Austen', '1813-01-28', 'T. Egerton', 'Romance', 6),
('B006', 'War and Peace', 'Leo Tolstoy', '1869-01-01', 'The Russian Messenger', 'Historical', 4),
('B007', 'The Hobbit', 'J.R.R. Tolkien', '1937-09-21', 'George Allen & Unwin', 'Fantasy', 9),
('B008', 'Brave New World', 'Aldous Huxley', '1932-01-01', 'Chatto & Windus', 'Dystopian', 7),
('B009', 'The Catcher in the Rye', 'J.D. Salinger', '1951-07-16', 'Little, Brown and Company', 'Fiction', 12),
('B010', 'The Odyssey', 'Homer', '800-01-01', 'Ancient Greece', 'Epic', 5),
('B000', 'Tutor OOP', 'Trang', '2024-10-22', 'UET', 'Document', 10);

INSERT INTO Classes (classID, section, class_name) VALUES
('CS4', 'FIT', 'Khoa hoc May Tinh'),
('C001', 'A', 'Class A - Science'),
('C002', 'B', 'Class B - Mathematics'),
('C003', 'C', 'Class C - Literature');


INSERT INTO Students (userID, userName, dateOfBirth, contact, classID, age) VALUES
('S001', 'Alice Johnson', '2002-05-15', 1234567890, 'C001', 22),
('S002', 'Bob Smith', '2001-08-20', 2345678901, 'C002', 23),
('S003', 'Charlie Davis', '2000-10-05', 3456789012, 'C003', 24),
('S004', 'Diana Lee', '2001-11-10', 4567890123, 'C001', 23),
('S005', 'Edward King', '2002-03-22', 5678901234, 'C002', 22),
('S006', 'Fiona Brown', '2000-07-17', 6789012345, 'C003', 24),
('S007', 'George White', '2001-12-01', 7890123456, 'C001', 23),
('S008', 'Hannah Black', '2002-09-13', 8901234567, 'C002', 22),
('S009', 'Isaac Green', '2000-04-30', 9012345678, 'C003', 24),
('S010', 'Jackie Blue', '2002-01-25', 9123456789, 'C001', 22);


INSERT INTO Requests (userID, bookID, quantityBorrow, borrowDate, returnDate) VALUES
( 'S001', 'B001', 1, '2024-10-01', NULL),
( 'S002', 'B002', 1, '2024-10-03', NULL),
( 'S003', 'B003', 1, '2024-09-25', NULL),
( 'S004', 'B004', 2, '2024-09-20', NULL),
( 'S005', 'B005', 1, '2024-10-02', NULL),
( 'S006', 'B006', 1, '2024-09-30', NULL),
( 'S007', 'B007', 1, '2024-10-05', NULL),
( 'S008', 'B008', 1, '2024-10-04', NULL),
( 'S009', 'B009', 1, '2024-10-01', NULL),
( 'S010', 'B010', 1, '2024-10-03', NULL),
( 'S001', 'B000', 1, '2024-11-7', NULL);


-- -----------------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE checkPassword(IN ID VARCHAR(10), pass VARCHAR(50))
BEGIN
    WITH findPass AS (
		SELECT password 
		FROM Users 
		WHERE userID = ID
	)
	SELECT 
    CASE
        WHEN (SELECT COUNT(*) FROM Users WHERE userID = ID) = 0 THEN 'noSuchID'
        WHEN (SELECT password FROM findPass) IS NULL THEN 'NotYet'
        WHEN (SELECT password FROM findPass) = pass THEN true
        ELSE false
    END AS Result;
END;
// DELIMITER ;


DELIMITER //
CREATE PROCEDURE firstLogIn (IN ID VARCHAR(10), newPassword VARCHAR(50))
BEGIN
	IF (SELECT COUNT(*) FROM Users WHERE userID = ID AND password IS NULL) = 1
    THEN 
		SET SQL_SAFE_UPDATES = 0;
		UPDATE Users
		SET password = newPassword
		WHERE userID = ID;
		SET SQL_SAFE_UPDATES = 1;
        SELECT true AS Result;
	ELSE
		SELECT false AS Result;
    END IF;
END;
// DELIMITER ;


DELIMITER //
CREATE PROCEDURE changePassword (IN ID VARCHAR(10), oldPassword VARCHAR(50), newPassword VARCHAR(50))
BEGIN
	IF (SELECT COUNT(*) FROM Users WHERE userID = ID AND password = oldPassword) = 1
    THEN 
		SET SQL_SAFE_UPDATES = 0;
		UPDATE Users
		SET password = newPassword
		WHERE userID = ID;
        SET SQL_SAFE_UPDATES = 1;

        SELECT true AS Result;
	ELSE
		SELECT false AS Result;
    END IF;
END;
// DELIMITER ;

-- ------------------------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE checkForBookLeft(IN b_ID VARCHAR(10))
BEGIN
    SELECT quantityLeft FROM Books WHERE bookID = b_ID;
END;
// DELIMITER ;


-- input<bookID, studentID, quantityBorrow>
-- output<true/false>
DELIMITER //
CREATE PROCEDURE borrowBook (IN b_ID VARCHAR(10), s_ID VARCHAR(10), quantity INT)
BEGIN
	DECLARE quantityLeft INT;
	SELECT quantityInStock INTO quantityLeft
    FROM Books WHERE bookID = b_ID;
    
	IF (quantity <= quantityLeft)
    THEN 
		INSERT INTO Requests (userID, bookID, quantityBorrow, borrowDate, returnDate) 
		VALUES (s_ID, b_ID, quantity, CURRENT_DATE(), NULL);
        SELECT true AS Result;
	ELSE 
		SELECT false AS Result;
	END IF;
END;
// DELIMITER ;


-- input<requestID>
DELIMITER //
CREATE PROCEDURE returnBook (IN r_ID VARCHAR(15), s_ID VARCHAR(10))
BEGIN
	IF s_ID = (SELECT userID FROM Requests WHERE requestID = r_ID)
    THEN 
		UPDATE Requests
		SET returnDate = CURRENT_DATE()
		WHERE requestID = r_ID;
        
        SELECT true AS Result;
	ELSE 
		SELECT false AS Result;
	END IF;
END;
// DELIMITER ;


-- input<userID>
-- output<requestID, bookID, bookName, author, quantityBorrow, borrowDate>
DELIMITER //
CREATE PROCEDURE checkForUnreturn_BookList(IN s_ID VARCHAR(10))
BEGIN
    SELECT
		requestID,
        bookID,
        quantityBorrow,
        borrowDate
	FROM Requests 
	WHERE userID = s_ID AND returnDate IS NULL;
END;
// DELIMITER ;


-- input<bookID>
-- output<requestID, userID, userName, contact, quantityBorrow, borrowDate>
DELIMITER //
CREATE PROCEDURE checkForUnreturn_StudentList(IN b_ID VARCHAR(10))
BEGIN
   
	 SELECT requestID,
			userID,
			quantityBorrow,
			borrowDate
	FROM Requests 
	WHERE bookID = b_ID AND returnDate IS NULL;
END;
// DELIMITER ;


-- input<userID>
-- output<requestID, bookID, bookName, author, quantityBorrow, borrowDate>
DELIMITER //
CREATE PROCEDURE checkForHistory_BookList(IN s_ID VARCHAR(10))
BEGIN
    SELECT requestID,
			bookID,
			quantityBorrow,
			borrowDate,
			returnDate
	FROM Requests 
	WHERE userID = s_ID
    ORDER BY '2024-10-30' - returnDate ASC, borrowDate DESC;
END;
// DELIMITER ;


-- ------------------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE addStudent (IN 
	ID NVARCHAR(10),
	Name NVARCHAR(50),
	Birth DATE,
	phone VARCHAR(15),
    studentClassID NVARCHAR(50),
    years_old INT
)
BEGIN
	INSERT INTO Students (userID, userName, dateOfBirth, contact, classID, age) 
    VALUES (ID, Name, Birth, phone, studentClassID, years_old);
END;
// DELIMITER ;


DELIMITER //
CREATE PROCEDURE deleteUser (IN ID NVARCHAR(10))
BEGIN
	DELETE FROM Users WHERE userID = ID;
END;
// DELIMITER ;

DELIMITER //

CREATE PROCEDURE findStudent (IN ID VARCHAR(10))
BEGIN
	SELECT * FROM Students
    WHERE userID = ID;
END;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE changeUserName (IN ID VARCHAR(10), newName VARCHAR(50))
BEGIN
	UPDATE Students
    SET userName = newName
    WHERE userID = ID;
END;
// DELIMITER ;


DELIMITER //
CREATE PROCEDURE changeContact (IN ID VARCHAR(10), newContact VARCHAR(50))
BEGIN
	UPDATE Students
    SET contact = newContact
    WHERE userID = ID;
END;
// DELIMITER ;

-- --------------------------------------------------------------------------------------

DELIMITER //
CREATE PROCEDURE addBook (IN 
	b_ID VARCHAR(50),
    title VARCHAR(50),
    author VARCHAR(50),
    publishDate VARCHAR(50),
    publishir VARCHAR(50),
    classified VARCHAR(50),
    quantity INT
)
BEGIN
	INSERT INTO Books (bookID, b_name, author, releaseDate, publisher, category, quantityInStock)
    VALUES (b_ID, title, author, publishDate, publishir, classified, quantity);
END;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE deleteBook (IN b_ID VARCHAR(50))
BEGIN
	SET SQL_SAFE_UPDATES = 0;
	DELETE FROM Books
    WHERE bookID = b_ID;
    SET SQL_SAFE_UPDATES = 1;
END;
// DELIMITER ;


DELIMITER //
CREATE PROCEDURE findBookFromID (IN b_ID VARCHAR(50))
BEGIN
	SELECT bookID, 
			b_name, 
            author, 
            releaseDate, 
            publisher, 
            category, 
            quantityLeft
    FROM Books WHERE bookID = b_ID;
END;
// DELIMITER ;


DELIMITER //
CREATE PROCEDURE loadNewBook (IN b_ID VARCHAR(10), data MEDIUMBLOB)
BEGIN
	SET SQL_SAFE_UPDATES = 0;
    
	UPDATE Books
    SET PDF = data
    WHERE bookID = b_ID;
    
    SET SQL_SAFE_UPDATES = 1;
END;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE downloadBook (IN b_ID VARCHAR(50))
BEGIN
	SELECT PDF FROM Books WHERE bookID = b_ID;
END;
// DELIMITER ;


DELIMITER //
CREATE PROCEDURE findBook (IN keyword VARCHAR(50), findBy INT, sortBy INT)
/*
findBy option;
	1. find by name
    2. find by category
    3. find by author
    4. find by publisher
sortBy option:
	0. sort by insert index <else?
	1. sort by book's name ASC
    2. sort by book's name DESC
    3. sort by releaseDate ASC
    4. sort by releaseDate DESC
*/
BEGIN
	-- find by book's name
	IF findBy = 1 THEN
		IF sortBy = 1 THEN SELECT * FROM books WHERE b_name LIKE CONCAT('%', keyword, '%') ORDER BY b_name ASC;
        ELSEIF sortBy = 2 THEN SELECT * FROM books WHERE b_name LIKE CONCAT('%', keyword, '%') ORDER BY b_name DESC;
        ELSEIF sortBy = 3 THEN SELECT * FROM books WHERE b_name LIKE CONCAT('%', keyword, '%') ORDER BY releaseDate ASC;
        ELSEIF sortBy = 4 THEN SELECT * FROM books WHERE b_name LIKE CONCAT('%', keyword, '%') ORDER BY releaseDate DESC;
        ELSE SELECT * FROM books WHERE b_name LIKE CONCAT('%', keyword, '%');
		END IF;
	-- find by category
	ELSEIF findBy = 2 THEN
		IF sortBy = 1 THEN SELECT * FROM books WHERE category =  keyword ORDER BY b_name ASC;
        ELSEIF sortBy = 2 THEN SELECT * FROM books WHERE category =  keyword ORDER BY b_name DESC;
        ELSEIF sortBy = 3 THEN SELECT * FROM books WHERE category =  keyword ORDER BY releaseDate ASC;
        ELSEIF sortBy = 4 THEN SELECT * FROM books WHERE category =  keyword ORDER BY releaseDate DESC;
        ELSE SELECT * FROM books WHERE category =  keyword;
		END IF;
	-- find by author
	ELSEIF findBy = 3 THEN
		IF sortBy = 1 THEN SELECT * FROM books WHERE author LIKE CONCAT('%', keyword, '%') ORDER BY b_name ASC;
        ELSEIF sortBy = 2 THEN SELECT * FROM books WHERE author LIKE CONCAT('%', keyword, '%') ORDER BY b_name DESC;
        ELSEIF sortBy = 3 THEN SELECT * FROM books WHERE author LIKE CONCAT('%', keyword, '%') ORDER BY releaseDate ASC;
        ELSEIF sortBy = 4 THEN SELECT * FROM books WHERE author LIKE CONCAT('%', keyword, '%') ORDER BY releaseDate DESC;
        ELSE SELECT * FROM books WHERE author LIKE CONCAT('%', keyword, '%');
		END IF;
	-- find by publisher
	ELSEIF findBy = 4 THEN
		IF sortBy = 1 THEN SELECT * FROM books WHERE publisher LIKE CONCAT('%', keyword, '%') ORDER BY b_name ASC;
        ELSEIF sortBy = 2 THEN SELECT * FROM books WHERE publisher LIKE CONCAT('%', keyword, '%') ORDER BY b_name DESC;
        ELSEIF sortBy = 3 THEN SELECT * FROM books WHERE publisher LIKE CONCAT('%', keyword, '%') ORDER BY releaseDate ASC;
        ELSEIF sortBy = 4 THEN SELECT * FROM books WHERE publisher LIKE CONCAT('%', keyword, '%') ORDER BY releaseDate DESC;
        ELSE SELECT * FROM books WHERE publisher LIKE CONCAT('%', keyword, '%');
		END IF;
	END IF;
END;
// DELIMITER ;