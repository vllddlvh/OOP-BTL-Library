<a name="comebackHear"/>

## Mục lục Controller
* [1. DBDatabaseConnector](#DBDatabaseConnector)
* [2. DBLogin](#DBLogin)
* [3. DBUser](#DBUser)
* [4. DBBook](#DBDocument)
* [5. DBRequest](#DBRequest)
* [6. UpdateFile](#UpdateFile)



package controller:
Bao gồm các static class liên kết thông tin với database.
Bên dưới là danh sách các method được cung cấp:


<a name = "DBDatabaseConnector"/>

## 1. DatabaseConnector 
- connection Connection  
  	-> Dây liên kết tới database

- getJDBCConnection()  
	-> Connection : Tạo connection với database
+ firstTODO()  
	-> void : Tổng hợp các thao tác đầu tiên cần thực hiện.
+ closeConnection  
	-> void : Ngắt kết nối với database khi chương trình kết thúc


<a name = "DBLogin"/>

## 2. DBLogin extends DatabaseConnector

+ login (userID String, password String) model.User.loginAlert  
	-> Đăng nhập. Trả về 1 trong 3 trạng thái thuộc enum loginAlert
+ changePassword (userID String, oldPassword, newPassword) boolean  
	-> Đổi mật khẩu.  
	-> Nếu là lần đầu đăng nhập thì oldPassword = "" hoặc null cũng được.


<a name = "DBUser"/>

## 3. DBUser extends DatabaseConnector
+ getMemberInfo (userID String) Member  
	-> Trả về thông tin đầy đủ của Member đó. null nếu sai ID.
+ getStaffInfo (userID String) Staff  
	-> Trả về thông tin đầy đủ của Staff đó. null nếu sai ID
+ addNewStaff (newStaff Staff, managerStaff Staff) boolean  
	->  Thêm newStaff vào danh sách thủ thư. managerStaff là introducer, hay người trực tiếp add Staff mới vào  
	-> Trả về false nếu trùng userID. Có thể trùng với Staff hoặc Member khác.
+ addNewMember (newMember Member) boolean  
	-> Thêm newMember vào danh sách bạn đọc  
	-> Trả về false nếu trùng userID. Có thể trùng với Staff hoặc Member khác.

+ deleteUser (userWhoBeDeleted User, staffWhoDo Staff) void  
	-> Xóa user khỏi hệ thống. Cần thêm 1 Staff chịu trách nhiệm (không để làm gì cả)
	 	

<a name = "DBDocument"/>

## 4. DBBook  extends DatabaseConnector
+ getDocumentInfo (String documentID) Document  
	-> Lấy thông tin 1 Document bất kỳ, dựa theo ID
+ searchBook (title String, author String, int releaseYear, category) ArrayList<Book>  
	-> Tìm kiếm sách. dựa theo tiêu đề, tác giả, năm xuất bản, thể loại. 
+ searchThesis (title String, fieldOfStudy String, writerID, advisor) ArrayList<Book>  
	-> Tìm kiếm luận văn. dựa theo tiêu đề, lĩnh vực nghiên cứu, ID người viết, tên cố vấn.



<a name = "DBRequest"/>

## 5. DBRequest extends DatabaseConnector
+ borrowBook(userID String, bookID String, quantity int)  
	-> tạo yêu cầu mượn sách
+ returnBook(requestNeedToReturn Request)  
	-> yêu cầu trả sách

+ checkForUnreturn_DocumentList (userID String) ArrayList<Request>  
	-> danh sách các yêu cầu chưa trả của một --bạn đọc--

+ checkForUnreturn_MemberList (documentID String) ArrayList<Request>  
	-> danh sách các yêu cầu chưa trả của một --tài liệu--

+ getAllBorrowHistory_DocumentList (userID String) ArrayList<Request>   
	-> danh sách tất cả yêu cầu mượn tài liệu của một bạn đọc từ trước tới nay.

<a name = "UpdateFile"/>

## 6. UpdateFile extends DatabaseConnector
Tạm thời chưa sử dụng







		
