DROP PROCEDURE IF EXISTS addBook;
DELIMITER //
CREATE PROCEDURE addBook (IN 
	newISBN VARCHAR(50),
    newTitle VARCHAR(50),
    theAuthor VARCHAR(50),
	storedQuantity INT unsigned,
    newCategory INT unsigned,
    newDescription TEXT,
    thePublisher VARCHAR(50),
    theReleaseYear INT(4),
    theLanguage VARCHAR(20)
)
BEGIN
	IF (SELECT COUNT(*) From Documents WHERE ID = newISBN) = 0
    
	THEN 
		INSERT INTO Documents (ID, Title, genre, totalQuantity, Description, category, language) 
			VALUES (newISBN, newTitle, 'Book', storedQuantity, newDescription, newCategory, theLanguage);
		INSERT INTO Books (ISBN, author, publisher, releaseYear) 
			VALUES (newISBN, theAuthor, thePublisher, theReleaseYear);
		SELECT true as Result;
        
	ELSE 
		SELECT false as Result; -- if the ID already exists
    END IF;
END;
// DELIMITER ;



DROP PROCEDURE IF EXISTS loadMoreDocumentCopies;
DELIMITER //
CREATE PROCEDURE loadMoreDocumentCopies (IN documentID VARCHAR(50), quantityChange INT ,staffWhoDid VARCHAR(10))
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

DROP PROCEDURE IF EXISTS loadDocumentCover;
DELIMITER //
CREATE PROCEDURE loadDocumentCover (IN documentID VARCHAR(50), newCover BLOB)
BEGIN
	UPDATE storeddocument 
    SET cover = newCover
    WHERE ID = documentID;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS loadDocumentPDF;
DELIMITER //
CREATE PROCEDURE loadDocumentPDF (IN documentID VARCHAR(50), newPDF MEDIUMBLOB)
BEGIN
	UPDATE storeddocument 
    SET PDF = newPDF
    WHERE ID = documentID;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS deleteDocument;
DELIMITER //
CREATE PROCEDURE deleteDocument (IN delDocumentID VARCHAR(50), whoDelete VARCHAR(10))
BEGIN
	IF (SELECT COUNT(*) FROM Staff WHERE ID = whoDelete) <> 0
	THEN 
		DELETE FROM Documents WHERE ID = delDocumentID;
    END IF;
END;
// DELIMITER ;