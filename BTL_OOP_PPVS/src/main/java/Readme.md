<a name="comebackHear"/>

## Mục lục Package
* [1. View] (#View)
* [2. Models](#Models)
* [3. DatabaseConnector](#Controller)



<a name = "..." />

<a name = "Models"/>

## II, Models
Bao gồm các class biểu diễn các đối tượng thực được sử dụng trong chương trình.


<a name = "Controller" />

## III, Controller
Bao gồm các class có chức năng liên kết và lấy thông tin từ database.
Cụ thể:

  ### DatabaseConnector 
- connection Connection -> Dây liên kết tới database

- getJDBCConnection() -> Connection : Tạo connection với database
+ firstTODO() -> void : Tổng hợp các thao tác đầu tiên cần thực hiện.
+ closeConnection -> void : Ngắt kết nối với database khi chương trình kết thúc


  ### DBLogin extends DatabaseConnector
+ final static int FIRST_LOG_IN, TRUE_LOG_IN, FAIL_LOG_IN

+ logIn(userID String, password String) int -> Check đăng nhập. Trả về 1 trong 3 final
+ firstLogIn(userID String, newPassword) int -> Đăng nhập lần đầu (áp dụng với account chưa có mật khẩu). Trả về 1 trong 2 final
+ changePassword(userID String, oldPassword, newPassword) int -> Đổi mật khẩu. Trả về 1 trong 2 final


  ### DBUser extends DatabaseConnector
+ getUserInfo(currentUser User) User -> Trả về thông tin đầy đủ của user hiện tại. Cơ bản thì User chung chung không lưu thông tin chi tiết, mà thường chỉ có userID để thao tác thôi.
+ changeUserName(userID String, newUserName String) boolean
+ changeContact(userID String, newUserName String) boolean
	 	

  ### DBBook  extends DatabaseConnector
+ findBook(keyword String, searchBy int, sortBy int) List<Book> -> Hàm tìm kiếm chung
+ findBook(bookID String) Book -> Hàm tìm kiếm theo ID cụ thể.


  ### DBRequest extends DatabaseConnector
+ borrowBook(userID String, bookID String, quantity int) -> tạo yêu cầu mượn sách
+ returnBook(requestNeedToReturn Request) -> yêu cầu trả sách
+ UnreturnBookList(userID String) List<Book> -> danh sách sách chưa trả
+ everBorrowBookList(userID String) List<Book> -> danh sách sách đã và đang mượn.


  ### UpdateFile extends DatabaseConnector
+ uploadBookFileToDatabase(bookID String, fin File) boolean -> upload lên, ko check lại thông tin (tức là ghi đè)
- getBookFileFromDatabase(bookID String) File -> download về
- openFile(file File) void -> ném file ra desktop và mở bằng app mặc định
+ saveBookFile(bookID String) void -> Lưu file vào src/OuterData và openFile() luôn
+ oneTimeOpenBook(bookID String) void -> Tương tự trên, nhưng xóa file ngay sau khi mở







		
