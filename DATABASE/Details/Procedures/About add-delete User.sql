
DROP PROCEDURE IF EXISTS addMember;
DELIMITER //
CREATE PROCEDURE addMember (IN 
	newID VARCHAR(10),
    1stName VARCHAR(10),
    nastName VARCHAR(20),
    contact VARCHAR(50),
    BirthDate DATE
)
BEGIN
	IF (SELECT COUNT(*) From User WHERE ID = newID) = 0
    
	THEN 
		INSERT INTO Member (ID, firstName, lastName, contact, dateOfBirth) 
			VALUES (newID, 1stName, nastName, contact, BirthDate);
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
    1stName VARCHAR(10),
    nastName VARCHAR(20),
    contax VARCHAR(50),
    job VARCHAR(20),
    introducer VARCHAR(10)
)
BEGIN
	IF (SELECT COUNT(*) From User WHERE ID = newID) = 0 AND (SELECT COUNT(*) FROM Staff WHERE ID = introducer) = 1
    
	THEN 
		INSERT INTO Staff (ID, firstName, lastName, contact, jobTitle, IntroducerID) 
			VALUES (newID, 1stName, nastName, contax, job, introducer);
		SELECT true as Result;
        
	ELSE 
		SELECT false as Result; -- if duplicate userID
    END IF;
END;
// DELIMITER ;


DROP PROCEDURE IF EXISTS deleteUser;
DELIMITER //
CREATE PROCEDURE deleteUser (IN delID NVARCHAR(10), whoDelete NVARCHAR(10))
BEGIN
	IF (SELECT COUNT(*) FROM Staff WHERE ID = whoDelete)
	THEN DELETE FROM User WHERE ID = delID;
    END IF;
END;
// DELIMITER ;