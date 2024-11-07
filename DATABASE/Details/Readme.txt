**Nạp dữ liệu lần đầu với DATABASE:
- Chạy file CREATE DATABASE 	-> tạo các tables và key
- Chạy file Trigger		-> tạo các trigger nội tại cần thiết
- Chạy các file procedure còn lại
- Mở netBeans, chạy FirstRunExample.Example	-> Upload các BLOB vào database

* Các lỗi thường gặp:
	- Lỗi định dạng DATE. Tùy phiên bản sẽ có thể nhận YYYY/MM/DD hoặc không. Chỉnh hết / -> - là được
	- Lỗi "do not sync" khi tạo trigger hay procedure. 
	  Cam đoan là code chuẩn. Spam, chạy lại một vài lần là được. Không được thì gõ thằng nào code ra đống đấy :v
	- Lỗi khi chạy code trong netBeans. 
	  Cơ bản thì kiểm tra lại file DatabaseConnector, đỗi chiếu với menu ở Workbench.
	  Có thể là hostname, password khác với khai báo trong hàm getJDBCConnector. Chỉnh lại thôi cho code đó thôi. Khá đơn giản.

	- Về FirstRunExample.Example. Tại sao phải chạy cái này. 
	  Thì file BLOB được nạp vào theo LOAD_FILE(path) với path là đường dẫn tới file gốc. Nhưng path cũng phải nằm trong quyền truy cập của Workbench.
	  Tùy máy Workbench có thể truy cập được tất cả hoặc không. Nên tốt nhất nạp thẳng bằng code cho nhàn =))
	  Kiếm pdf về rồi thay lại filePath là được




---------------------------------------------------------------------------------------

**Tổng hợp thông tin về Trigger:


- genUserID_Student  	-> Đơn giản là tạo hồ sơ User khi thêm 1 Student.
- genUserID_Admin  	-> tương tự.

- genRequestID  	-> Tự tạo ID cho phiếu yêu cầu dựa theo ngày mượn sách.


- makeQuantityLeft	-> Tạo số lượng sách tối đa có thể mượn khi thêm đầu sách mới.
- minusQuantityLeft 	-> Cập nhật quantityLeft khi mượn sách.
- plusQuantityLeft 	-> Tương tự, nhưng là khi trả sách.




---------------------------------------------------------------------------------------

** Tổng hợp thông tin về Stored Procedure:


1. For LogIn:
+	checkPassword(IN ID VARCHAR(10), pass VARCHAR(50))  -> Dành cho đăng nhập
	-- input  <userID, password>
	-- output <Result (true/false)>

+	firstLogIn (IN ID VARCHAR(10), newPassword VARCHAR(50))  -> Khởi tạo mật khẩu khi đăng nhập lần đầu
	-- input  <userID, password>
	-- output <Result (true/false)>

+	changePassword (IN ID VARCHAR(10), oldPassword VARCHAR(50), newPassword VARCHAR(50))  -> Đổi mật khẩu
	-- input  <userID, oldPassword, newPassword>
	-- output <Result (true/false)>


---------------------------------------------------------------------------------------

2. Search Book
+	findBook (IN keyword VARCHAR(50), findBy INT, sortBy INT)  -> Dành cho công cụ tìm kiếm
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


+	findBookFromID (IN b_ID VARCHAR(50))  -> Tìm kiếm sách theo ID có sẵn
	-- input  <bookID>
	-- output Everything

+ 	loadNewBook (IN b_ID VARCHAR(10), data MEDIUMBLOB)  -> tải file sách vào trong database. MAX 16MB
	-- input  <bookID>
	-- output null.
				-> Vấn đề là nó không kiểm tra lại file đâu. Ai import null vào là mất tick.

+ 	downloadBook (IN b_ID VARCHAR(50))  -> export file sách
	-- input <bookID>
	-- output <File.pdf> // ko chắc nhá, đây do import vào cái gì.


---------------------------------------------------------------------------------------

3. For Request:
+	checkForBookLeft(IN b_ID VARCHAR(10)) 	-> Số lượng sách thực còn
	-- input  <bookID>
	-- output <quantityLeft INT>

+	borrowBook (IN b_ID VARCHAR(10), s_ID VARCHAR(10), quantity INT)  -> Tiến hành mượn sách
	-- input  <bookID, userID, quantityBorrow>
	-- output <Result (true/false)>

+	returnBook (IN r_ID VARCHAR(15), s_ID VARCHAR(10))  -> Tiến hành trả sách
	-- input  <requestID, userID>
	-- output <Result (true/false)>


+	checkForUnreturn_BookList(IN s_ID VARCHAR(10))  -> Danh sách sách chưa trả của user
	-- input  <userID>
	-- output <requestID, bookID, bookName, author, quantityBorrow, borrowDate>


+	checkForUnreturn_StudentList(IN b_ID VARCHAR(10))  -> Danh sách sinh viên chưa trả 1 quyển sách
	-- input  <bookID>
	-- output <requestID, userID, userName, contact, quantityBorrow, borrowDate>


+ 	checkForHistory_BookList(IN s_ID VARCHAR(10))  -> Lịch sử mượn sách của 1 sinh viên
	-- input  <userID>
	-- output <requestID, userID, userName, contact, quantityBorrow, borrowDate, returnDate>


---------------------------------------------------------------------------------------

4. For Student
+ 	changeUserName (IN ID VARCHAR(10), newName VARCHAR(50))  -> not re-confirm\n
	-- input  <userID, new-name>
	-- output null


+	changeContact (IN ID VARCHAR(10), newContact VARCHAR(50))  -> not re-confirm
	-- input <userID, new-contact-email?>
	-- output null
