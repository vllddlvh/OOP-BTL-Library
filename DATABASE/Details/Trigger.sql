use library_demo_contents;

DELIMITER //
DROP TRIGGER IF EXISTS genUserID_Student;
CREATE TRIGGER genUserID_Student
BEFORE INSERT ON Students
FOR EACH ROW
BEGIN
	DECLARE ID VARCHAR(10);
    SET ID = NEW.userID;
	INSERT INTO Users(userID) VALUES (ID);
END;
// DELIMITER ;


DELIMITER //
DROP TRIGGER IF EXISTS genUserID_Admin;
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
DROP TRIGGER IF EXISTS genRequestID;
CREATE TRIGGER genRequestID
BEFORE INSERT ON Requests
FOR EACH ROW
BEGIN
	DECLARE newMonth INT;
    DECLARE month_prefix CHAR(3);
    DECLARE count INT;
    
    SET newMonth = MONTH(NEW.borrowDate);
    
    SET month_prefix = CASE newMonth
        WHEN 1 THEN 'Jan';
        WHEN 2 THEN 'Feb';
        WHEN 3 THEN 'Mar';
        WHEN 4 THEN 'Apr';
        WHEN 5 THEN 'May';
        WHEN 6 THEN 'Jun';
        WHEN 7 THEN 'Jul';
        WHEN 8 THEN 'Aug';
        WHEN 9 THEN 'Sep';
        WHEN 10 THEN 'Oct';
        WHEN 11 THEN 'Nov';
        WHEN 12 THEN 'Dec';
    END;
    
    SET count = (SELECT COUNT(*) + 1 FROM Requests WHERE MONTH(borrowDate) = newMonth;
    
    SET NEW.requestID = CONCAT(YEAR(NEW.borrowDate), month_prefix, count);
END;
// DELIMITER ;


DELIMITER //
DROP TRIGGER IF EXISTS makeQuantityLeft;
CREATE TRIGGER makeQuantityLeft
BEFORE INSERT ON Books
FOR EACH ROW
BEGIN
    SET NEW.quantityLeft = NEW.quantityInStock;
END;
// DELIMITER ;


DELIMITER //

DROP TRIGGER IF EXISTS minusQuantityLeft;
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
DROP TRIGGER IF EXISTS plusQuantityLeft;
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


