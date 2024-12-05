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