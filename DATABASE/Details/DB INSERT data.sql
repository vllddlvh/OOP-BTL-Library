USE library_demo_contents;

-- Xóa thằng cha to nhất thì sẽ xóa hết toàn bộ các bảng còn lại
SET SQL_SAFE_UPDATES = 0;
DELETE FROM Users;
DELETE FROM Classes;
DELETE FROM Books;
SET SQL_SAFE_UPDATES = 1;


INSERT INTO Books (bookID, b_name, author, releaseDate, publisher, category, quantityInStock) VALUES
('B001', 'The Great Gatsby', 'F. Scott Fitzgerald', '1925-04-10', 'Scribner', 'Fiction', 5),
('B002', '1984', 'George Orwell', '1949-06-08', 'Secker & Warburg', 'Dystopian', 8),
('B003', 'To Kill a Mockingbird', 'Harper Lee', '1960-07-11', 'J.B. Lippincott & Co.', 'Fiction', 10),
('B004', 'Moby Dick', 'Herman Melville', '1851-10-18', 'Harper & Brothers', 'Adventure', 7),
('B005', 'Pride and Prejudice', 'Jane Austen', '1813-01-28', 'T. Egerton', 'Romance', 6),
('B006', 'War and Peace', 'Leo Tolstoy', '1869-01-01', 'The Russian Messenger', 'Historical', 4),
('B007', 'The Hobbit', 'J.R.R. Tolkien', '1937-09-21', 'George Allen & Unwin', 'Fantasy', 9),
('B008', 'Brave New World', 'Aldous Huxley', '1932-01-01', 'Chatto & Windus', 'Dystopian', 7),
('B009', 'The Catcher in the Rye', 'J.D. Salinger', '1951-07-16', 'Little, Brown and Company', 'Fiction', 12),
('B010', 'The Odyssey', 'Homer', '800-01-01', 'Ancient Greece', 'Epic', 5),
('B000', 'Tutor OOP', 'Trang', '2024-10-22', 'UET', 'Document', 10);

INSERT INTO Classes (classID, section, class_name) VALUES
('CS4', 'FIT', 'Khoa hoc May Tinh'),
('C001', 'A', 'Class A - Science'),
('C002', 'B', 'Class B - Mathematics'),
('C003', 'C', 'Class C - Literature');


INSERT INTO Students (userID, userName, dateOfBirth, contact, classID, age) VALUES
('S001', 'Alice Johnson', '2002-05-15', 1234567890, 'C001', 22),
('S002', 'Bob Smith', '2001-08-20', 2345678901, 'C002', 23),
('S003', 'Charlie Davis', '2000-10-05', 3456789012, 'C003', 24),
('S004', 'Diana Lee', '2001-11-10', 4567890123, 'C001', 23),
('S005', 'Edward King', '2002-03-22', 5678901234, 'C002', 22),
('S006', 'Fiona Brown', '2000-07-17', 6789012345, 'C003', 24),
('S007', 'George White', '2001-12-01', 7890123456, 'C001', 23),
('S008', 'Hannah Black', '2002-09-13', 8901234567, 'C002', 22),
('S009', 'Isaac Green', '2000-04-30', 9012345678, 'C003', 24),
('S010', 'Jackie Blue', '2002-01-25', 9123456789, 'C001', 22);


INSERT INTO Requests (userID, bookID, quantityBorrow, borrowDate, returnDate) VALUES
( 'S001', 'B001', 1, '2024-10-01', NULL),
( 'S002', 'B002', 1, '2024-10-03', '2024-10-10'),
( 'S003', 'B003', 1, '2024-09-25', NULL),
( 'S004', 'B004', 2, '2024-09-20', '2024-09-27'),
( 'S005', 'B005', 1, '2024-10-02', NULL),
( 'S006', 'B006', 1, '2024-09-30', '2024-10-07'),
( 'S007', 'B007', 1, '2024-10-05', NULL),
( 'S008', 'B008', 1, '2024-10-04', NULL),
( 'S009', 'B009', 1, '2024-10-01', '2024-10-08'),
( 'S010', 'B010', 1, '2024-10-03', '2024-10-10');
