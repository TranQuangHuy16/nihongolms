# Quick Start Guide - Nihongo LMS Frontend

**Dành cho người mới bắt đầu - Hướng dẫn nhanh để chạy ứng dụng**

## 🚀 5 Bước Để Bắt Đầu

### 1️⃣ Cài Đặt Node.js

Nếu chưa cài đặt:

- Download từ https://nodejs.org (LTS version)
- Cài đặt và kiểm tra: `node --version`

### 2️⃣ Cài Đặt Dependencies

```bash
cd frontend
npm install
```

Chờ cho đến khi hoàn tất (2-3 phút)

### 3️⃣ Cấu Hình Environment

File `.env.local` đã có sẵn, kiểm tra:

```
VITE_API_BASE_URL=http://localhost:8080/api
```

Nếu backend chạy trên port khác, cập nhật URL.

### 4️⃣ Bắt Đầu Development Server

```bash
npm run dev
```

Trình duyệt sẽ tự động mở http://localhost:3000

### 5️⃣ Đăng Nhập

Sử dụng thông tin đăng nhập từ backend:

- **Username**: tạo tài khoản mới tại trang Register
- **Password**: mật khẩu của bạn

---

## 📱 Chức Năng Chính

| Trang     | URL          | Mô Tả                         |
| --------- | ------------ | ----------------------------- |
| Login     | `/login`     | Đăng nhập tài khoản           |
| Register  | `/register`  | Tạo tài khoản mới             |
| Dashboard | `/dashboard` | Trang chủ sau khi đăng nhập   |
| Tags      | `/tags`      | Quản lý tags (CRUD)           |
| Users     | `/users`     | Xem danh sách người dùng      |
| Profile   | `/profile`   | Cập nhật hồ sơ & đổi mật khẩu |

---

## 🔑 Tài Khoản Demo

### Đăng Ký Tài Khoản Mới

1. Click vào nút "Đăng Ký" trên trang login
2. Điền form (username, email, password, fullName)
3. Click "Đăng Ký"
4. Tự động đăng nhập và vào dashboard

### Đăng Nhập

1. Trên trang login
2. Nhập username và password
3. Click "Đăng Nhập"

---

## 💾 File Quan Trọng

### 📝 Cấu Hình

- `.env.local` - Biến môi trường (API URL, app settings)
- `package.json` - Dependencies & scripts

### 📂 Mã Nguồn

- `src/App.jsx` - Routing chính
- `src/pages/` - Các trang (Login, Register, Dashboard...)
- `src/services/` - API calls
- `src/store/` - State management

### 📖 Tài Liệu

- `README.md` - Tổng quan dự án
- `API_DOCUMENTATION.md` - Chi tiết API
- `DEVELOPMENT_GUIDE.md` - Hướng dẫn phát triển

---

## 🛠️ Các Lệnh Hữu Dụng

```bash
# Chạy development server
npm run dev

# Build production
npm run build

# Preview production build
npm run preview

# Format code
npm run lint
```

---

## ❌ Troubleshooting

### ❓ Lỗi "Cannot find module"

```bash
npm install
```

### ❓ Port 3000 đã được sử dụng

```bash
npm run dev -- --port 3001
```

### ❓ API connection failed

1. Kiểm tra backend có chạy không: http://localhost:8080/api
2. Kiểm tra VITE_API_BASE_URL trong .env.local
3. Nếu backend khác host, bật CORS trên backend

### ❓ Không thể đăng nhập

1. Kiểm tra username/password chính xác
2. Xem browser console (F12) có error không
3. Kiểm tra network tab xem API response

### ❓ Token hết hạn

- Đăng nhập lại để lấy token mới
- Token được lưu tự động vào localStorage

---

## 📚 Tiếp Theo?

Sau khi quen với ứng dụng:

1. **Tìm hiểu mã nguồn**
   - Xem `DEVELOPMENT_GUIDE.md`
   - Khám phá cấu trúc thư mục

2. **Thêm tính năng mới**
   - Theo pattern có sẵn
   - Tham khảo các trang đã tạo

3. **Tối ưu hóa**
   - Performance tips trong DEVELOPMENT_GUIDE.md
   - Code splitting, lazy loading

4. **Deploy**
   - Build: `npm run build`
   - Upload folder `dist/` lên server

---

## 📞 Cần Hỗ Trợ?

- Xem `README.md` để tổng quan
- Xem `API_DOCUMENTATION.md` cho API details
- Xem `DEVELOPMENT_GUIDE.md` cho advanced topics
- Check browser console (F12) cho errors

---

## ✅ Checklist Trước Khi Deploy

- [ ] Thay đổi `.env` variables (API URL, app name...)
- [ ] Chạy `npm run build` để test production build
- [ ] Kiểm tra `dist/` folder có được tạo không
- [ ] Test tất cả chức năng trước khi deploy
- [ ] Kiểm tra responsive design trên mobile

---

**Chúc bạn phát triển ứng dụng thành công! 🎉**
