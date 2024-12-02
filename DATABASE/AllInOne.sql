DROP DATABASE IF EXISTS Library_2nd_Edition;
CREATE DATABASE Library_2nd_Edition;

use Library_2nd_Edition;

CREATE TABLE User (
	ID VARCHAR(10),
    
    primary key (ID)
);

CREATE TABLE Login (
	ID VARCHAR(10),
    password VARCHAR(50),
    Role ENUM('Staff', 'Member'),
    
    primary key (ID),
    foreign key (ID) references User(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Member (
	ID VARCHAR(10),
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    dateOfBirth DATE NOT NULL,
    
    primary key (ID),
    foreign key (ID) references User(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Staff (
	ID VARCHAR(10),
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    jobTitle VARCHAR(30),
    reportToID VARCHAR(10),
    
    primary key (ID),
    foreign key (ID) references User(ID) ON UPDATE CASCADE ON DELETE CASCADE,
    foreign key (reportToID) references Staff(ID) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Documents (
	ID VARCHAR(50),
    Title VARCHAR(50) not null,
    genre ENUM('Thesis', 'Book') not null,
    totalQuantity INT unsigned not null,
    quantityLeft INT unsigned not null,
	Description TEXT,
	category INT unsigned,
    language VARCHAR(20),
    
    primary key (ID)
);

CREATE TABLE Books (
	ISBN VARCHAR(50),
    author VARCHAR(50) NOT NULL,
    publisher VARCHAR(50),
    releaseYear INT(4),
    
    primary key (ISBN),
    foreign key (ISBN) references Documents(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE storedDocument (
	ID VARCHAR(50),
	Cover BLOB,
	PDF MEDIUMBLOB,
     
	primary key (ID),
	foreign key (ID) references Documents(ID) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Request (
	requestID VARCHAR(20),
    userID VARCHAR(10),
    documentID VARCHAR(50),
    quantityBorrow INT unsigned,
    borrowDate DATE not null,
    returnDate DATE,
    
    primary key (requestID),
    foreign key (userID) references User(ID) ON UPDATE CASCADE ON DELETE CASCADE,
    foreign key (documentID) references Documents(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

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
    DECLARE month_prefix CHAR(7);
    DECLARE count INT;
    
    SET month_prefix = CASE MONTH(NEW.borrowDate)
        WHEN 1 THEN 'Jan' WHEN 2 THEN 'Feb'
        WHEN 3 THEN 'Mar' WHEN 4 THEN 'Apr'
        WHEN 5 THEN 'May' WHEN 6 THEN 'Jun'
		WHEN 7 THEN 'Jul' WHEN 8 THEN 'Aug'
        WHEN 9 THEN 'Sep' WHEN 10 THEN 'Oct'
        WHEN 11 THEN 'Nov' WHEN 12 THEN 'Dec'
    END;
    
    SET month_prefix = CONCAT(YEAR(NEW.borrowDate), month_prefix);
    
    SET count = (SELECT COUNT(*) + 1 FROM Request r WHERE left(r.requestID, 7) = month_prefix);
    
    SET NEW.requestID = CONCAT(month_prefix, count);
END;
// DELIMITER ;

/*
*
*
*
*/

DROP TRIGGER IF EXISTS makeQuantityLeft;
DROP TRIGGER IF EXISTS makeFileSlot;
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
CREATE TRIGGER makeFileSlot
AFTER INSERT ON Documents
FOR EACH ROW
BEGIN
    INSERT INTO storedDocument (ID) VALUES (NEW.ID);
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
    SET quantityLeft = quantityLeft + NEW.quantityBorrow - OLD.quantityBorrow
    WHERE ID = NEW.documentID;
END;
// DELIMITER ;


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


DROP PROCEDURE IF EXISTS addMember;
DELIMITER //
CREATE PROCEDURE addMember (IN 
	newID VARCHAR(10),
    newFirstName VARCHAR(50),
    newLastName VARCHAR(50),
    newContact VARCHAR(50),
    newBirthDate DATE
)
BEGIN
	IF (SELECT COUNT(*) From User WHERE ID = newID) = 0
    
	THEN 
		INSERT INTO Member (ID, firstName, lastName, contact, dateOfBirth) 
			VALUES (newID, newFirstName, newLastName, newContact, newBirthDate);
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
    newFirstName VARCHAR(50),
    newLastName VARCHAR(50),
    newContact VARCHAR(50),
    newJobTitle VARCHAR(30),
    newReportToID VARCHAR(10)
)
BEGIN
	IF (SELECT COUNT(*) From User WHERE ID = newID) = 0 
    AND (newReportToID is null OR (SELECT COUNT(*) FROM Staff WHERE ID = newReportToID) = 1)
    
	THEN 
		INSERT INTO Staff (ID, firstName, lastName, contact, jobTitle, reportToID) 
			VALUES (newID, newFirstName, newLastName, newContact, newJobTitle, newReportToID);
		SELECT true as Result;
        
	ELSE 
		SELECT false as Result; -- if duplicate userID
    END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS deleteUser;
DELIMITER //
CREATE PROCEDURE deleteUser (IN delID VARCHAR(10), whoDelete VARCHAR(10))
BEGIN
	IF (SELECT COUNT(*) FROM Staff WHERE ID = whoDelete)
	THEN DELETE FROM User WHERE ID = delID;
    END IF;
END;
// DELIMITER ;

DROP PROCEDURE IF EXISTS login;
DELIMITER //
CREATE PROCEDURE login (IN userID VARCHAR(10), passwordTry VARCHAR(50))
BEGIN
	DECLARE thisRole VARCHAR(10);
    DECLARE thisPassword VARCHAR(50);
    
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
CREATE PROCEDURE changePassword (IN userID VARCHAR(10), oldPassword VARCHAR(50), newPassword VARCHAR(50))
BEGIN
	IF (newPassword <> userID AND oldPassword <> newPassword)
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
CREATE PROCEDURE returnDocument (IN rqID VARCHAR(20), uID VARCHAR(10))
BEGIN
	IF uID = (SELECT userID FROM Request WHERE requestID = rqID)
    THEN 
		UPDATE Request
		SET returnDate = CURRENT_DATE()
		WHERE requestID = rqID AND returnDate IS NULL;
        
        SELECT true AS Result;
	ELSE 
		SELECT false AS Result;
	END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS checkForUnreturn_DocumentList;
DELIMITER //
CREATE PROCEDURE checkForUnreturn_DocumentList(IN uID VARCHAR(10))
BEGIN
    SELECT
		requestID,
        documentID,
        quantityBorrow,
        borrowDate
	FROM Request
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
			documentID,
			quantityBorrow,
			borrowDate,
			returnDate
	FROM Request
	WHERE userID = uID
    ORDER BY DATEDIFF(CURRENT_DATE(), returnDate), borrowDate DESC;
END;
// DELIMITER ;

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
				books.category AS category,
                documents.language AS language,
				documents.genre AS genre
			FROM books left join documents ON (books.isbn = documents.ID)
			WHERE books.isbn = documentID;
END;
// DELIMITER ;


use library_2nd_edition;

SET SQL_SAFE_UPDATES = 0;
-- DELETE FROM Documents;
DELETE FROM Documents;
DELETE FROM User;
SET SQL_SAFE_UPDATES = 1;


-- Thêm 3 Member
CALL addMember('M001', 'Nguyen', 'An', '0901234567', '2000-05-01');
CALL addMember('M002', 'Le', 'Binh', '0912345678', '1999-09-10');
CALL addMember('M003', 'Tran', 'Chi', '0923456789', '1998-12-20');
CALL addMember('1662', 'Nguyễn Minh', 'Fucka', '0934567890', '2005-09-12');
CALL addMember('1750', 'Lê Long Vũ', 'Đào', '123456789', '2005-06-05');
CALL addMember('1666', 'Hải Phương', 'Bùi', '987654321', '2005-09-06');
CALL addMember('1686', 'Trường Sơn', 'Nguyễn', '123456789', '2005-07-27');


-- Thêm 1 Staff
CALL addStaff('23021662', 'Nguyễn Minh', 'Fucka', '0934567890', 'Ăn Tạp', NULL);
CALL addStaff('23021750', 'Lê Long Vũ', 'Đào', '123456789', 'High', '23021662');
CALL addStaff('23021666', 'Hải Phương', 'Bùi', '987654321', 'High', '23021662');
CALL addStaff('23021686', 'Trường Sơn', 'Nguyễn', '123456789', 'High', '23021662');



CALL addBook('006', 'War and Peace', 'Leo Tolstoy', 100, 1, 'A sweeping epic of Russian society during the Napoleonic Wars.', 'The Russian Messenger', 1869, 'English');
CALL addBook('007', 'Crime and Punishment', 'Fyodor Dostoevsky', 100, 2, 'The psychological turmoil of a man who commits a murder.', 'The Russian Messenger', 1866, 'English');
CALL addBook('008', 'The Little Prince', 'Antoine de Saint-Exupéry', 100, 4, 'A whimsical story about a young prince exploring love and friendship.', 'Reynal & Hitchcock', 1943, 'English');
CALL addBook('009', 'Jane Eyre', 'Charlotte Brontë', 100, 8, 'The struggles and growth of an orphaned girl in Victorian England.', 'Smith, Elder & Co.', 1847, 'English');
CALL addBook('010', 'The Lord of the Rings: The Fellowship of the Ring', 'J.R.R. Tolkien', 100, 16, 'The first volume of the epic quest to destroy the One Ring.', 'George Allen & Unwin', 1954, 'English');
CALL addBook('011', 'Brave New World', 'Aldous Huxley', 100, 32, 'A society engineered for maximum happiness but devoid of individuality.', 'Chatto & Windus', 1932, 'English');
CALL addBook('012', 'The Road', 'Cormac McCarthy', 100, 64, 'A father and son journey through a desolate, post-apocalyptic world.', 'Alfred A. Knopf', 2006, 'English');
CALL addBook('013', 'Don Quixote', 'Miguel de Cervantes', 100, 128, 'The comedic adventures of a man who believes he is a knight.', 'Francisco de Robles', 1605, 'English');
CALL addBook('014', 'The Picture of Dorian Gray', 'Oscar Wilde', 100, 2, 'A man sells his soul to retain his youth and beauty.', 'Lippincott\'s Monthly Magazine', 1890, 'English');
CALL addBook('015', 'Animal Farm', 'George Orwell', 100, 256, 'A satirical allegory about a group of farm animals who overthrow their owner.', 'Secker & Warburg', 1945, 'English');