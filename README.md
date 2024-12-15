
# THƯ VIỆN ĐIỆN TỬ PVVS  

- Thư viện điện tử PVVS - bài tập nhóm lớp UET I24-25 INT2204 18  
- Nhóm gồm 4 thành viên:  
  + [Đào Lê Long Vũ](https://github.com/vllddlvh)  
  + [Bùi Hải Phương](https://github.com/PhuongBui69)  
  + [Nguyễn Trường Sơn](https://github.com/NostagiGuideuS)  
  + [Nguyễn Minh Phúc](https://github.com/nguyenminhphuc1209)  

# Mục lục  

1. [Giới thiệu](#intro)  
2. [Các tính năng](#issue)  
3. [Cài đặt](#install)  
    - [Yêu cầu hệ thống](#system-requirement)  
    - [Hướng dẫn cài đặt](#tutorial)  
4. [Sử dụng](#using)  
5. [Công nghệ sử dụng](#technique)  
    - [Tài liệu tham khảo](#references)  
6. [Đóng góp](#contribute)  

<a name = "intro"></a>  
# I. GIỚI THIỆU  

- Dự án app THƯ VIỆN ĐIỆN TỬ PVVS là một dự án bài tập lớn của lớp Lập Trình Hướng Đối Tượng INT2204 18, giảng viên phụ trách Nguyễn Thu Trang.  
- Dự án là một ứng dụng được thiết kế quản lý thư viện để quản lý tài nguyên số, cụ thể là sách điện tử (ebook). Hiện tại chỉ được demo lưu trữ nội dung định dạng PDF.  
- Biểu đồ lớp (Class Diagram):  

  ![image]("Chưa cập nhật").  

<a name = "issue"></a>  
# II. CÁC TÍNH NĂNG  

✅ Đăng nhập, bổ sung tài khoản  

:white_check_mark: Quản lý sách, tài liệu: thêm, sửa, xóa thông tin các loại tài liệu.  

:white_check_mark: Tìm kiếm thông tin tài liệu theo các tiêu chí: tên, tác giả, thể loại,...  

:white_check_mark: Quản lý người dùng: thêm, chỉnh sửa, và thông tin xóa người dùng.  

:white_check_mark: Quản lý mượn, trả sách.  

:white_check_mark: Giao diện khá thân thiện với người dùng.  

<a name = "install"></a>  
# III. CÀI ĐẶT  

## Yêu cầu hệ thống  

+ Ngôn ngữ lập trình Java, cụ thể là Java Swing.  
+ MySQL.  
+ Các thư viện hỗ trợ khác (Maven).  
+ IDE: Netbeans.  

<a name = "tutorial"></a>  
## Hướng dẫn cài đặt  

1. Clone dự án:  
   ```bash  
   git clone [https://github.com/vllddlvh/OOP-BTL-Library](https://github.com/vllddlvh/OOP-BTL-Library)  
   ```  

2. Cài đặt một số thư viện hỗ trợ:  
   Tải và cài đặt Maven vào IDE. Hầu hết thư viện bổ sung đã được đề cập trong `Project Files/pom.xml` và được tải tự động bởi Maven.  

3. Cài đặt cơ sở dữ liệu:  
   - Sơ đồ thiết kế cơ sở dữ liệu:  
     ![Sơ đồ thiết kế cơ sở dữ liệu]("Chưa cập nhật").  
   - Code cơ sở dữ liệu:  
     ![Đường dẫn code cơ sở dữ liệu].  

4. Chạy dự án:  
   - Bạn có thể chạy trực tiếp Project. Hoặc tìm chạy file `main.Main`.  
   - Trong trường hợp Terminal báo "Connecting Database FAILED", có thể do bạn chưa tạo cơ sở dữ liệu, hoặc cần chỉnh thông tin truy cập, tại `model.DatabaseConnector`.  

<a name = "using"></a>  
# IV. SỬ DỤNG  

1. Đăng nhập tài khoản:  

   - **Tài khoản quản lý**  
     + Tên đăng nhập: `23021666`  
     + Mật khẩu: `23021666`  

   - **Tài khoản người dùng**  
     + Tên đăng nhập: `1666`  
     + Mật khẩu: `1666`  
     + Tạo tài khoản khi đăng nhập dưới quyền quản lý. Mật khẩu mặc định ban đầu sẽ là **Tên Đăng Nhập (ID người dùng)**.  

2. Giao diện:  

   - **Trang chủ -> Các trang giao diện khác**:  
     `LoginJFrame`  
     │  
     ├── as Member -> `GDMainNguoiDung`  
     │        ├── `TrangChuJPanel`  
     │        │        └── `ChiTietTaiLieu`  
     │        ├── `TimKiemVoiAPI`  
     │        │        └── `ChiTietTaiLieu`  
     │        └── `HoSoCuaToi`  
     │                  ├── `ThongTinCuaToiJPanel`  
     │                  ├── `DoiMatKhauJFrame`  
     │                  └── `SachDaMuonJPanel`  
     │                          └── `MuonSachJFrame`  
     └── as Staff -> `GDMain`  
              ├── `TrangChuJPanel`  
              │        └── `ChiTietTaiLieu`  
              ├── `TimKiemVoiAPI`  
              │        └── `ChiTietTaiLieu`  
              │                  └── `ThemTaiLieuFrame`  
              ├── `BanDocJPanel`  
              │        ├── `MuonTraTaiLieu`  
              │        │        └── `MuonSachJFrame`  
              │        └── `ThongTinThanhVienJPanel`  
              │                  ├── `SuaThongTinThanhVienJFrame`  
              │                  └── `ThemThanhVienJFrame`  
              └── `TaiLieuPanel`  
                       ├── `ThemTaiLieuFrame`  
                       │        └── `ChonFileAnhJFrame`  
                       ├── `SuaThongTinTaiLieuSachJFrame`  
                       │        └── `ChonFileAnhJFrame`  
                       └── `HoSoCuaToi`  
                                ├── `ThongTinCuaToiJPanel`  
                                ├── `DoiMatKhauJFrame`  
                                └── `SachDaMuonJPanel`  
                                         └── `MuonSachJFrame`.  

<a name = "technique"></a>  
# V. CÔNG NGHỆ SỬ DỤNG  

- Ngôn ngữ lập trình Java.  
- Thư viện GUI (Graphical User Interface): Java Swing.  
- Cơ sở dữ liệu MySQL.  
- Google Books API.  

<a name = "references"></a>  
## Tài liệu tham khảo  

- Kênh YouTube JMaster IO: [https://www.youtube.com/watch?v=QmHBWBA_w-I&list=PLsfLgp1K1xQ7dDUcjtlRQhZJxl0orbEsd](https://www.youtube.com/watch?v=QmHBWBA_w-I&list=PLsfLgp1K1xQ7dDUcjtlRQhZJxl0orbEsd).  
- Giáo trình Lập Trình Hướng Đối Tượng được cô Nguyễn Thu Trang cung cấp.  
- OpenAI ChatGPT.  

<a name = "contribute"></a>  
# VI. ĐÓNG GÓP  

- Báo cáo đóng góp của mỗi thành viên nhóm 17 trong dự án: [link](https://docs.google.com/document/d/1LFbWA8I6Gj0-tSDa5krYfvZvNXzRh8D_Yi5uLfevqGo/edit?usp=sharing).  
