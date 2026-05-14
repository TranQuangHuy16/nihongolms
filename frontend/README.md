# Nihongo LMS - Frontend (ReactJS)

Website quản lý Nihongo LMS được xây dựng với **ReactJS**, sử dụng các API từ backend Spring Boot.

## 🎯 Tính Năng

- ✅ **Xác Thực (Authentication)**: Đăng ký, đăng nhập với JWT token
- ✅ **Quản Lý Hồ Sơ**: Cập nhật thông tin cá nhân, đổi mật khẩu
- ✅ **Quản Lý Tags**: Tạo, sửa, xóa, tìm kiếm tags với phân trang
- ✅ **Quản Lý Người Dùng**: Xem danh sách tất cả người dùng
- ✅ **Dashboard**: Thông tin tổng quan và liên kết nhanh

## 🛠️ Công Nghệ

- **React 18.2.0** - UI Framework
- **React Router 6.20.0** - Routing
- **Axios** - HTTP Client
- **Zustand** - State Management
- **Tailwind CSS** - Styling
- **Vite** - Build Tool
- **React Toastify** - Notifications

## 📁 Cấu Trúc Thư Mục

```
frontend/
├── public/
├── src/
│   ├── components/        # Các component tái sử dụng
│   │   ├── Header.jsx
│   │   └── ProtectedRoute.jsx
│   ├── pages/            # Các trang
│   │   ├── LoginPage.jsx
│   │   ├── RegisterPage.jsx
│   │   ├── DashboardPage.jsx
│   │   ├── ProfilePage.jsx
│   │   ├── TagsPage.jsx
│   │   └── UsersPage.jsx
│   ├── services/         # API calls
│   │   ├── apiClient.js
│   │   ├── authApi.js
│   │   ├── userApi.js
│   │   └── tagApi.js
│   ├── store/            # Zustand stores
│   │   ├── authStore.js
│   │   ├── userStore.js
│   │   └── tagStore.js
│   ├── App.jsx
│   ├── main.jsx
│   └── index.css
├── .env.example          # Biến môi trường mẫu
├── .env.local            # Biến môi trường thực tế (git ignored)
├── .gitignore
├── package.json
├── vite.config.js
├── tailwind.config.js
├── postcss.config.js
└── index.html
```

## 🚀 Cài Đặt & Chạy

### 1. Cài đặt dependencies

```bash
cd frontend
npm install
```

### 2. Cấu hình biến môi trường

Tạo file `.env.local` dựa trên `.env.example`:

```bash
cp .env.example .env.local
```

Cập nhật `VITE_API_BASE_URL` nếu backend chạy trên port khác:

```
VITE_API_BASE_URL=http://localhost:8080/api
```

### 3. Chạy development server

```bash
npm run dev
```

Ứng dụng sẽ mở tự động trên `http://localhost:3000`

### 4. Build cho production

```bash
npm run build
```

Output sẽ được lưu trong folder `dist/`

## 📋 API Endpoints

### Authentication

- **POST** `/auth/register` - Đăng ký tài khoản mới
- **POST** `/auth/login` - Đăng nhập

### Users

- **GET** `/users/me` - Lấy thông tin user đang đăng nhập
- **GET** `/users` - Lấy danh sách tất cả user
- **PUT** `/users` - Cập nhật thông tin user
- **POST** `/users/change-password` - Đổi mật khẩu

### Tags

- **GET** `/tags` - Lấy danh sách tags (hỗ trợ phân trang & tìm kiếm)
- **POST** `/tags` - Tạo tag mới
- **PUT** `/tags/{id}` - Cập nhật tag
- **DELETE** `/tags/{id}` - Xóa tag

## 🔐 Authentication Flow

1. User đăng nhập/đăng ký → Nhận `accessToken` từ backend
2. Token được lưu trong Zustand store và localStorage
3. Mỗi request gửi lên header: `Authorization: Bearer {token}`
4. Nếu token hết hạn (401), user sẽ bị redirect về trang login

## 🎨 Tùy Chỉnh Styling

- **Tailwind CSS**: Chỉnh sửa `tailwind.config.js` để thay đổi theme
- **Custom Styles**: Thêm CSS vào `src/index.css`

## 📦 Dependencies Chính

| Package          | Version | Mục Đích         |
| ---------------- | ------- | ---------------- |
| react            | 18.2.0  | UI Library       |
| react-router-dom | 6.20.0  | Routing          |
| axios            | 1.6.0   | HTTP Client      |
| zustand          | 4.4.0   | State Management |
| tailwindcss      | 3.3.0   | CSS Framework    |
| vite             | 5.0.0   | Build Tool       |

## 🤝 Contribute

1. Fork repository
2. Tạo branch mới: `git checkout -b feature/YourFeature`
3. Commit changes: `git commit -m 'Add YourFeature'`
4. Push branch: `git push origin feature/YourFeature`
5. Tạo Pull Request

## 📝 License

MIT License

## 📞 Support

Liên hệ: quanghuy@example.com

---

**Happy Coding! 🚀**
