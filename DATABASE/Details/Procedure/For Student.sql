DELIMITER //
DROP PROCEDURE IF EXISTS addStudent;
CREATE PROCEDURE addStudent (IN 
	ID NVARCHAR(10),
	Name NVARCHAR(50),
	Birth DATE,
	phone VARCHAR(15),
    studentClassID NVARCHAR(50),
    years_old INT
)
BEGIN
	INSERT INTO Students (userID, userName, dateOfBirth, contact, classID, age) 
    VALUES (ID, Name, Birth, phone, studentClassID, years_old);
END;
// DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS deleteUser;
CREATE PROCEDURE deleteUser (IN ID NVARCHAR(10))
BEGIN
	DELETE FROM Users WHERE userID = ID;
END;
// DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS findStudent;
CREATE PROCEDURE findStudent (IN ID VARCHAR(10))
BEGIN
	SELECT * FROM Students
    WHERE userID = ID;
END;
// DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS changeUserName;
CREATE PROCEDURE changeUserName (IN ID VARCHAR(10), newName VARCHAR(50))
BEGIN
	UPDATE Students
    SET userName = newName
    WHERE userID = ID;
END;
// DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS changeContact;
CREATE PROCEDURE changeContact (IN ID VARCHAR(10), newContact VARCHAR(50))
BEGIN
	UPDATE Students
    SET contact = newContact
    WHERE userID = ID;
END;
// DELIMITER ;