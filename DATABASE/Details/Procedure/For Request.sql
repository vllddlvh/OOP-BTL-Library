DELIMITER //
DROP PROCEDURE IF EXISTS checkForBookLeft;
CREATE PROCEDURE checkForBookLeft(IN b_ID VARCHAR(10))
BEGIN
    SELECT quantityLeft FROM Books WHERE bookID = b_ID;
END;
// DELIMITER ;


-- input<bookID, studentID, quantityBorrow>
-- output<true/false>
DELIMITER //
DROP PROCEDURE IF EXISTS borrowBook;
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
DROP PROCEDURE IF EXISTS returnBook;
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
DROP PROCEDURE IF EXISTS checkForUnreturn_BookList;
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
DROP PROCEDURE IF EXISTS checkForUnreturn_StudentList;
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
DROP PROCEDURE IF EXISTS checkForHistory_BookList;
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


call checkForHistory_BookList('S001');
call checkForBookLeft('B001');