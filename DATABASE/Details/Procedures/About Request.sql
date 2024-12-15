DROP PROCEDURE IF EXISTS borrowDocument;
DELIMITER //
CREATE PROCEDURE borrowDocument (IN uID VARCHAR(10), docuID VARCHAR(50), borrowQuantity INT)
BEGIN
	DECLARE quantityAvailable INT;
	SELECT quantityLeft INTO quantityAvailable
    FROM Documents WHERE ID = docuID;
    
	IF (borrowQuantity <= quantityAvailable)
    THEN 
		INSERT INTO Request (userID, documentID, quantityBorrow, borrowDate)
		VALUES (uID, docuID, borrowQuantity, CURRENT_DATE());
        SELECT true AS Result;
	ELSE 
		SELECT false AS Result;
	END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS returnDocument;
DELIMITER //
CREATE PROCEDURE returnDocument (IN docuID VARCHAR(20), uID VARCHAR(10))
BEGIN
	UPDATE Request
	SET returnDate = CURRENT_DATE()
	WHERE returnDate IS NULL AND documentID = docuID AND  userID = uID;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS checkForUnreturn_DocumentList;
DELIMITER //
CREATE PROCEDURE checkForUnreturn_DocumentList(IN uID VARCHAR(10))
BEGIN
    SELECT
		requestID,
        coalesce((SELECT CONCAT_WS(" ", firstName, lastName) FROM Staff WHERE ID = uID),
			(SELECT CONCAT_WS(" ", firstName, lastName) FROM Member WHERE ID = uID)),
        documentID,
        (SELECT title FROM documents WHERE ID = documentID) AS title,
        quantityBorrow,
        borrowDate
	FROM Request r
	WHERE userID = uID AND returnDate IS NULL;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS checkForUnreturn_StudentList;
DELIMITER //
CREATE PROCEDURE checkForUnreturn_StudentList(IN docuID VARCHAR(10))
BEGIN
   
	 SELECT requestID,
			userID,
			quantityBorrow,
			borrowDate
	FROM Request
	WHERE documentID = docuID AND returnDate IS NULL;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS checkForHistory_DocumentList;
DELIMITER //
CREATE PROCEDURE checkForHistory_DocumentList(IN uID VARCHAR(10))
BEGIN
    SELECT requestID,
			coalesce((SELECT CONCAT_WS(" ", firstName, lastName) FROM Staff WHERE ID = uID),
				(SELECT CONCAT_WS(" ", firstName, lastName) FROM Member WHERE ID = uID)),
			documentID,
			(SELECT title FROM documents WHERE ID = documentID) AS title,
			quantityBorrow,
			borrowDate,
			returnDate
	FROM Request
	WHERE userID = uID
    ORDER BY DATEDIFF(CURRENT_DATE(), returnDate), borrowDate DESC;
END;
// DELIMITER ;