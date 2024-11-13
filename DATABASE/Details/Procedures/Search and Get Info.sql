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