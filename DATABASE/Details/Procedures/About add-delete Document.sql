
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