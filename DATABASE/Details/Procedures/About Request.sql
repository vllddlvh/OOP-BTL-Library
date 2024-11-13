
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