Hệ thống Quản lý Nhập Hàng Nguyên Liệu Nhà Hàng (RESTAURANT MIT)

1. Tổng quan

Hệ thống này là một ứng dụng web được xây dựng để hỗ trợ nhân viên kho/quản lý nhập hàng thực hiện quy trình mua sắm nguyên vật liệu (nguyên liệu, thực phẩm) từ các nhà cung cấp, đảm bảo tính chính xác về số lượng, đơn giá và theo dõi lịch sử giao dịch.

2. Công nghệ sử dụng

Ngôn ngữ: Java (Servlet, JSP)

Kiến trúc: MVC (Model-View-Controller)

Cơ sở dữ liệu: SQL Server

Giao diện: HTML/CSS (Custom Dark Theme)

3. Kiến trúc Mô hình MVC

Hệ thống tuân thủ mô hình MVC để tách biệt logic nghiệp vụ, xử lý dữ liệu và giao diện người dùng.

Model (DAO/DTO/Model): Chứa các lớp ImportInvoice, WarehouseStaff, và các lớp Data Access Object (ImportInvoiceDAO, IngredientSupDAO) chịu trách nhiệm giao tiếp với Database.

View (JSP): Các trang giao diện (SearchIngredient.jsp, ConfirmInvoice.jsp, Login.jsp) hiển thị dữ liệu và nhận input từ người dùng.

Controller (Servlet): Các lớp Servlet (AuthServlet, IngredientServlet, InvoiceServlet) nhận yêu cầu, xử lý logic, quản lý Session và điều phối đến View phù hợp.

4. Quy trình nghiệp vụ chính: Tạo Hóa đơn Nhập hàng

Quy trình này được quản lý chủ yếu bởi IngredientServlet và InvoiceServlet, sử dụng kỹ thuật Post-Redirect-Get (PRG) và Session Scope để quản lý giỏ hàng.

A. Chọn Nhà cung cấp & Thêm hàng (IngredientServlet)

Bắt đầu (/start POST): Nhân viên chọn Nhà cung cấp. Nếu Nhà cung cấp mới được chọn, giỏ hàng (CURRENT_CART) cũ sẽ bị xóa để tránh nhầm lẫn.

Tìm kiếm (/search GET): Hiển thị danh sách nguyên liệu từ Nhà cung cấp đã chọn.

Thêm hàng (/addLine POST):

Nhận ID nguyên liệu, số lượng (qty) và giá nhập mới (newPrice).

Kiểm tra trong giỏ hàng (CURRENT_CART) nếu đã có mặt hàng tương tự (cùng ID và cùng giá nhập mới).

Nếu Trùng: Cộng dồn số lượng.

Nếu Không trùng: Thêm dòng mới vào giỏ hàng.

Cập nhật CURRENT_CART trong Session và Redirect về /search để tải lại trang hiển thị.

B. Chốt & Lưu Hóa đơn (InvoiceServlet)

Xác nhận (/confirm GET): Hiển thị giỏ hàng và tổng tiền cho người dùng xem lại trên trang ConfirmInvoice.jsp.

Lưu Database (/confirm POST):

Kiểm tra tính hợp lệ của dữ liệu Session (có đủ Nhân viên, NCC, và hàng trong giỏ không).

Lưu Hóa đơn Gốc: Gọi ImportInvoiceDAO.addInvoice(iv) sử dụng mệnh đề OUTPUT INSERTED.id để lấy ID tự động sinh của Hóa đơn vừa tạo.

Lưu Chi tiết: Gán ID Hóa đơn vừa nhận được cho tất cả các dòng trong giỏ hàng, sau đó lưu toàn bộ vào bảng InvoiceDetail.

Dọn dẹp Session:

Lưu thông tin Hóa đơn vừa tạo vào Session Keys (LAST_INVOICE, LAST_LINES, LAST_TOTAL) để in ấn.

Xóa giỏ hàng hiện tại (CURRENT_CART) và NCC (CURRENT_SUPPLIER_ID) để kết thúc giao dịch.

Forward sang trang Xác nhận để hiển thị thông báo thành công.

C. In ấn & Kết thúc

In ấn (/print POST): Truy xuất dữ liệu LAST_... từ Session và hiển thị trang PrintInvoice.jsp.

Hoàn tất (/done POST): Xóa các biến CURRENT_... và LAST_... khỏi Session, Redirect người dùng về trang tìm kiếm nguyên liệu, sẵn sàng cho giao dịch mới.

Hủy bỏ (/cancel POST): Xóa các biến CURRENT_... và Redirect về trang tìm kiếm Nhà cung cấp.
