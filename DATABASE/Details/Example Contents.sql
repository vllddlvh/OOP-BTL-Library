SET SQL_SAFE_UPDATES = 0;
DELETE FROM User;
DELETE FROM Documents;
SET SQL_SAFE_UPDATES = 1;

INSERT INTO Member (ID, firstName, lastName, contact, dateOfBirth) VALUES 
('23021686', 'Sonw', 'Ngx', 'NgxS@ops.com', '2005-01-14'),
('U003', 'John', 'Doe', 'john.doe@example.com', '1995-06-15'), 
('U004', 'Jane', 'Smith', 'jane.smith@example.com', '1997-09-30'), 
('U005', 'Emily', 'Johnson', 'emily.johnson@example.com', '1996-12-22');

INSERT INTO Staff (ID, firstName, lastName, contact, jobTitle, IntroducerID) VALUES 
('U001', 'Robert', 'Brown', 'robert.brown@example.com', 'Librarian', NULL), 
('U002', 'Alice', 'Davis', 'alice.davis@example.com', 'Assistant Librarian', 'U001'),
('1401', 'Sonw', 'Ngx', 'NgxS@ops.com', 'Special Agent', NULL);

INSERT INTO Documents (ID, Title, genre, totalQuantity) VALUES 
('D001', 'Effective Java', 'Book', 10), 
('D002', 'The Great Gatsby', 'Book', 5), 
('D003', 'Mastering Algorithms', 'Book', 7), 
('D004', 'Data Science Thesis 2023', 'Thesis', 2), 
('D005', 'Machine Learning Advances', 'Thesis', 3);

INSERT INTO Books (ISBN, author, publisher, releaseYear, category) VALUES 
('D001', 'Joshua Bloch', 'Addison-Wesley', 2018, 'Non-fiction'), 
('D002', 'F. Scott Fitzgerald', 'Charles Scribner\'s Sons', 1925, 'Fiction'), 
('D003', 'Thomas Cormen', 'MIT Press', 2009, 'Non-fiction');


INSERT INTO Thesis (ID, writerID, advisor, fieldOfStudy, Description) VALUES 
('D004', 'U003', 'Dr. Smith', 'Data Science', 'A comprehensive study on data science trends in 2023'), 
('D005', 'U004', 'Prof. Johnson', 'Machine Learning', 'Exploration of advancements in machine learning algorithms');

INSERT INTO Request (userID, documentID, quantityBorrow, borrowDate) VALUES 
('U003', 'D001', 1, '2024-01-15'), 
('U004', 'D002', 1, '2024-02-01'), 
('U005', 'D003', 1, '2024-03-05'), 
('U003', 'D004', 1, '2024-03-10'), 
('U004', 'D005', 1, '2024-04-15');
