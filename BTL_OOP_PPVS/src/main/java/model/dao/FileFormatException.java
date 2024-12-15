package model.dao;

/**
 *
 * @author Littl
 */

/**
 * Gộp chung các lỗi có thể cần phải check của FileHancle.
 * Bao gồm định dạng file ảnh không hợp lệ. 
 * Kích thước file pdf.
 * File corrupted hoặc bị cố ý sửa file tag...
 * 
 * Bổ sung: Hiện không bắt pdf > 1MB. Đây do cài đặt của Workbench limit. Nên không chỉnh lại nữa.
 */
public class FileFormatException extends Exception{
    public FileFormatException(String messenge) {
        super(messenge);
    } 
}
