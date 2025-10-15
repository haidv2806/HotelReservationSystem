# Hệ thống đặt phòng khách sạn

Hệ thống đặt phòng khách sạn được xây dựng nhằm hướng đến đối tượng là các khách sạn nhỏ lẻ, giúp đơn giản hóa và tự động hóa quy trình quản lý đặt phòng.

---

## **Thành viên nhóm 9**

* Đỗ Văn Hải - 2022602812
* Phạm Văn Phong - 2022603243
* Nguyễn Thanh Liêm - 2022601509
* Nguyễn Trung Hiếu - 2022601267

---

## **Hướng dẫn cài đặt**

Để chạy được dự án, bạn cần cài đặt Java và Maven trên máy tính của mình.

### **1. Cài đặt Java (JDK)**

Dự án yêu cầu **Java Development Kit (JDK)**, phiên bản 11 trở lên.

**Đối với Windows:**

1.  Truy cập trang tải xuống của [Oracle Java SE](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) hoặc [OpenJDK](https://jdk.java.net/).
2.  Tải xuống tệp cài đặt `.exe`.
3.  Chạy tệp và làm theo hướng dẫn trên màn hình.
4.  **Thiết lập biến môi trường:**
    * Mở **System Properties** > **Advanced** > **Environment Variables**.
    * Trong **System variables**, tạo một biến mới:
        * **Variable name:** `JAVA_HOME`
        * **Variable value:** `C:\Program Files\Java\jdk-11.x.x` (đường dẫn đến thư mục cài đặt JDK của bạn).
    * Chỉnh sửa biến **Path**:
        * Thêm `%JAVA_HOME%\bin` vào danh sách.

**Đối với macOS (sử dụng Homebrew):**

```bash
brew install openjdk@11
```

**Đối với Linux (Ubuntu/Debian):**

```bash
sudo apt update
sudo apt install openjdk-11-jdk
```

**Để kiểm tra cài đặt thành công, mở terminal hoặc command prompt và chạy lệnh:**
```bash
java -version
```
### 2. Cài đặt Apache Maven

**Maven** là một công cụ mạnh mẽ dùng để quản lý thư viện và tự động hóa quá trình xây dựng (build) dự án Java.

#### **Hướng dẫn cho Windows**

1.  **Tải xuống Maven:**
    * Truy cập [trang tải xuống chính thức của Maven](https://maven.apache.org/download.cgi).
    * Tìm đến mục **Files** và tải về tệp tin có dạng `apache-maven-x.x.x-bin.zip` (đây là phiên bản **binary zip archive**).

2.  **Giải nén:**
    * Giải nén tệp `.zip` vừa tải vào một vị trí cố định trên máy tính của bạn, ví dụ: `C:\Program Files\apache-maven-3.9.6`.

3.  **Thiết lập biến môi trường:**
    * Mở **Edit the system environment variables** từ menu Start.
    * Trong cửa sổ **System Properties**, chọn **Environment Variables...**.
    * Tại mục **System variables**, nhấn **New...** để tạo biến mới:
        * **Variable name:** `MAVEN_HOME`
        * **Variable value:** `C:\Program Files\apache-maven-3.9.6` (đường dẫn đến thư mục bạn vừa giải nén).
    * Vẫn trong mục **System variables**, tìm đến biến `Path`, chọn nó và nhấn **Edit...**.
        * Nhấn **New** và thêm một dòng mới: `%MAVEN_HOME%\bin`.
        * Nhấn **OK** để lưu lại tất cả các thay đổi.

**Đối với macOS (sử dụng Homebrew):**
```bash
brew install maven
```

**Đối với Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install maven
```

**Để kiểm tra cài đặt thành công, mở terminal hoặc command prompt và chạy lệnh:**
```bash
mvn -version
```

### 3. Chạy dự á

Clone repository về máy của bạn.
Mở terminal hoặc command prompt tại thư mục gốc của dự án.
Chạy lệnh sau để build và khởi động dự án:

```bash
mvn spring-boot:run
```
