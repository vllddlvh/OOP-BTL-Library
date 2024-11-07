DELIMITER //
DROP PROCEDURE IF EXISTS addBook;
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
DROP PROCEDURE IF EXISTS deleteBook;
CREATE PROCEDURE deleteBook (IN b_ID VARCHAR(50))
BEGIN
	SET SQL_SAFE_UPDATES = 0;
	DELETE FROM Books
    WHERE bookID = b_ID;
    SET SQL_SAFE_UPDATES = 1;
END;
// DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS findBookFromID;
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
DROP PROCEDURE IF EXISTS loadNewBook;
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
DROP PROCEDURE IF EXISTS downloadBook;
CREATE PROCEDURE downloadBook (IN b_ID VARCHAR(50))
BEGIN
	SELECT PDF FROM Books WHERE bookID = b_ID;
END;
// DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS findBook;
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

CALL findBookFromID('B001');