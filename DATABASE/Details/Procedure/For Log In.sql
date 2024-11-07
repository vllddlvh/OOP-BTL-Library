DELIMITER //
DROP PROCEDURE IF EXISTS checkPassword;
CREATE PROCEDURE checkPassword(IN ID VARCHAR(10), pass VARCHAR(50))
BEGIN
    WITH findPass AS (
		SELECT password 
		FROM Users 
		WHERE userID = ID
	)
	SELECT 
    CASE
        WHEN (SELECT COUNT(*) FROM Users WHERE userID = ID) = 0 THEN 'noSuchID'
        WHEN (SELECT password FROM findPass) IS NULL THEN 'NotYet'
        WHEN (SELECT password FROM findPass) = pass THEN true
        ELSE false
    END AS Result;
END;
// DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS firstLogIn;
CREATE PROCEDURE firstLogIn (IN ID VARCHAR(10), newPassword VARCHAR(50))
BEGIN
	IF (SELECT COUNT(*) FROM Users WHERE userID = ID AND password IS NULL) = 1
    THEN 
		SET SQL_SAFE_UPDATES = 0;
		UPDATE Users
		SET password = newPassword
		WHERE userID = ID;
		SET SQL_SAFE_UPDATES = 1;
        SELECT true AS Result;
	ELSE
		SELECT false AS Result;
    END IF;
END;
// DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS changePassword;
CREATE PROCEDURE changePassword (IN ID VARCHAR(10), oldPassword VARCHAR(50), newPassword VARCHAR(50))
BEGIN
	IF (SELECT COUNT(*) FROM Users WHERE userID = ID AND password = oldPassword) = 1
    THEN 
		SET SQL_SAFE_UPDATES = 0;
		UPDATE Users
		SET password = newPassword
		WHERE userID = ID;
        SET SQL_SAFE_UPDATES = 1;

        SELECT true AS Result;
	ELSE
		SELECT false AS Result;
    END IF;
END;
// DELIMITER ;