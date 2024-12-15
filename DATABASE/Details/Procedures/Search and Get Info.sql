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
	SELECT ID, firstName, lastName, contact, jobTitle, reportToID
    FROM Staff
    WHERE ID = findID;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS searchBook;
DELIMITER //
CREATE PROCEDURE searchBook (IN 
	titleKey VARCHAR(50),
    authorKey VARCHAR(50),
    releaseYearKey YEAR,
    categoryKey INT unsigned,
    theLanguage VARCHAR(20)
)
BEGIN
	SELECT 
		books.ISBN AS ISBN,
		documents.title AS title,
		documents.quantityLeft AS quantityAvailable,
		books.author AS author,
		books.publisher AS publisher,
		books.releaseYear AS releaseYear,
		documents.Description AS Description,
		documents.category AS category,
        documents.language AS language
	FROM books left join documents ON (books.isbn = documents.ID)
	WHERE ((titleKey is null) OR (documents.title LIKE CONCAT('%', titleKey, '%')))
    AND ((authorKey is null) OR (books.author LIKE CONCAT('%', authorKey, '%')))
    AND ((releaseYearKey is null) OR (books.releaseYear = releaseYearKey))
    AND ((categoryKey is null) OR (documents.category = categoryKey))
    AND (theLanguage IS NULL OR documents.language LIKE CONCAT('%', theLanguage, '%'));
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS searchDocument;
DELIMITER //
CREATE PROCEDURE searchDocument (IN documentID VARCHAR(50))
BEGIN
			SELECT 
				books.ISBN AS ISBN,
				documents.title AS title,
				documents.quantityLeft AS quantityAvailable,
				books.author AS author,
				books.publisher AS publisher,
				books.releaseYear AS releaseYear,
				documents.Description AS Description,
				documents.category AS category,
                documents.language AS language,
				documents.genre AS genre
			FROM books left join documents ON (books.isbn = documents.ID)
			WHERE books.isbn = documentID;
END;
// DELIMITER ;