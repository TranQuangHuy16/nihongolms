# API Documentation - Nihongo LMS

## 📌 Base URL

```
http://localhost:8080/api
```

## 🔑 Authentication

### Register - Tạo tài khoản mới

**Endpoint:** `POST /auth/register`

**Request:**

```json
{
  "username": "quanghuy",
  "email": "quanghuy@example.com",
  "password": "password123",
  "fullName": "Quang Huy"
}
```

**Response (201 Created):**

```json
{
  "status": 201,
  "message": "Registration successful",
  "data": {
    "user": {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "username": "quanghuy",
      "email": "quanghuy@example.com",
      "fullName": "Quang Huy",
      "createdAt": "2024-05-14T10:30:00Z"
    },
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

**Frontend Integration:** [RegisterPage.jsx](src/pages/RegisterPage.jsx)

---

### Login - Đăng nhập

**Endpoint:** `POST /auth/login`

**Request:**

```json
{
  "username": "quanghuy",
  "password": "password123"
}
```

**Response (200 OK):**

```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "user": {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "username": "quanghuy",
      "email": "quanghuy@example.com",
      "fullName": "Quang Huy"
    },
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

**Frontend Integration:** [LoginPage.jsx](src/pages/LoginPage.jsx) & [authApi.js](src/services/authApi.js)

---

## 👤 User Management

### Get Current User - Lấy thông tin user hiện tại

**Endpoint:** `GET /users/me`

**Headers:**

```
Authorization: Bearer <accessToken>
```

**Response (200 OK):**

```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "username": "quanghuy",
    "email": "quanghuy@example.com",
    "fullName": "Quang Huy",
    "createdAt": "2024-05-14T10:30:00Z"
  }
}
```

**Frontend Integration:** [Header.jsx](src/components/Header.jsx), [authStore.js](src/store/authStore.js)

---

### Get All Users - Lấy danh sách tất cả user

**Endpoint:** `GET /users`

**Headers:**

```
Authorization: Bearer <accessToken>
```

**Response (200 OK):**

```json
{
  "status": 200,
  "message": "Success",
  "data": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "username": "quanghuy",
      "email": "quanghuy@example.com",
      "fullName": "Quang Huy",
      "createdAt": "2024-05-14T10:30:00Z"
    }
  ]
}
```

**Frontend Integration:** [UsersPage.jsx](src/pages/UsersPage.jsx), [userApi.js](src/services/userApi.js)

---

### Update User - Cập nhật thông tin user

**Endpoint:** `PUT /users`

**Headers:**

```
Authorization: Bearer <accessToken>
Content-Type: application/json
```

**Request:**

```json
{
  "fullName": "Quang Huy Updated",
  "email": "newemail@example.com"
}
```

**Response (200 OK):**

```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "username": "quanghuy",
    "email": "newemail@example.com",
    "fullName": "Quang Huy Updated"
  }
}
```

**Frontend Integration:** [ProfilePage.jsx](src/pages/ProfilePage.jsx), [userApi.js](src/services/userApi.js)

---

### Change Password - Đổi mật khẩu

**Endpoint:** `POST /users/change-password`

**Headers:**

```
Authorization: Bearer <accessToken>
Content-Type: application/json
```

**Request:**

```json
{
  "oldPassword": "password123",
  "newPassword": "newpassword456"
}
```

**Response (200 OK):**

```json
{
  "status": 200,
  "message": "Đổi mật khẩu thành công",
  "data": "Đổi mật khẩu thành công"
}
```

**Frontend Integration:** [ProfilePage.jsx](src/pages/ProfilePage.jsx), [userApi.js](src/services/userApi.js)

---

## 🏷️ Tag Management

### Get Tags - Lấy danh sách tag

**Endpoint:** `GET /tags`

**Headers:**

```
Authorization: Bearer <accessToken>
```

**Query Parameters:**
| Param | Type | Description |
|-------|------|-------------|
| search | string | Tìm kiếm theo tên (optional) |
| page | int | Số trang (bắt đầu từ 1, default: 1) |
| size | int | Số lượng item mỗi trang (default: 10) |
| sortDir | string | Hướng sắp xếp: "asc" hoặc "desc" (default: "desc") |

**Example Request:**

```
GET /tags?search=grammar&page=1&size=10&sortDir=desc
```

**Response (200 OK):**

```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "data": [
      {
        "id": "123e4567-e89b-12d3-a456-426614174000",
        "name": "Grammar",
        "description": "Bài học về ngữ pháp",
        "createdAt": "2024-05-14T10:30:00Z"
      }
    ],
    "total": 1,
    "page": 1,
    "size": 10
  }
}
```

**Frontend Integration:** [TagsPage.jsx](src/pages/TagsPage.jsx), [tagApi.js](src/services/tagApi.js), [tagStore.js](src/store/tagStore.js)

---

### Create Tag - Tạo tag mới

**Endpoint:** `POST /tags`

**Headers:**

```
Authorization: Bearer <accessToken>
Content-Type: application/json
```

**Request:**

```json
{
  "name": "Grammar",
  "description": "Bài học về ngữ pháp"
}
```

**Response (200 OK):**

```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "name": "Grammar",
    "description": "Bài học về ngữ pháp",
    "createdAt": "2024-05-14T10:30:00Z"
  }
}
```

**Frontend Integration:** [TagsPage.jsx](src/pages/TagsPage.jsx) - Create form

---

### Update Tag - Cập nhật tag

**Endpoint:** `PUT /tags/{id}`

**Headers:**

```
Authorization: Bearer <accessToken>
Content-Type: application/json
```

**Request:**

```json
{
  "name": "Grammar Updated",
  "description": "Bài học về ngữ pháp - Cập nhật"
}
```

**Response (200 OK):**

```json
{
  "status": 200,
  "message": "Success",
  "data": {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "name": "Grammar Updated",
    "description": "Bài học về ngữ pháp - Cập nhật"
  }
}
```

**Frontend Integration:** [TagsPage.jsx](src/pages/TagsPage.jsx) - Edit form

---

### Delete Tag - Xóa tag

**Endpoint:** `DELETE /tags/{id}`

**Headers:**

```
Authorization: Bearer <accessToken>
```

**Response (200 OK):**

```json
{
  "status": 200,
  "message": "Xóa thẻ tag thành công",
  "data": "Xóa thẻ tag thành công"
}
```

**Frontend Integration:** [TagsPage.jsx](src/pages/TagsPage.jsx) - Delete action

---

## 🔐 Authentication Flow

### 1. Token Storage

```javascript
// Token được lưu vào localStorage qua Zustand
localStorage.setItem('auth-store', JSON.stringify({
  state: {
    user: {...},
    token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}));
```

### 2. Token Usage

```javascript
// Tự động thêm token vào mỗi request via interceptor
apiClient.interceptors.request.use((config) => {
  const token = useAuthStore.getState().token;
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
```

### 3. Token Validation

```javascript
// Nếu token hết hạn (401), redirect về login
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      useAuthStore.getState().logout();
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);
```

---

## 🛠️ API Client Configuration

**File:** [src/services/apiClient.js](src/services/apiClient.js)

```javascript
import axios from 'axios';

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request Interceptor - Thêm JWT token
apiClient.interceptors.request.use(
  (config) => {
    const token = useAuthStore.getState().token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response Interceptor - Xử lý lỗi
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      useAuthStore.getState().logout();
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default apiClient;
```

---

## 📋 Status Codes

| Code | Meaning                                        |
| ---- | ---------------------------------------------- |
| 200  | OK - Request thành công                        |
| 201  | Created - Resource được tạo thành công         |
| 400  | Bad Request - Dữ liệu không hợp lệ             |
| 401  | Unauthorized - Token không hợp lệ hoặc hết hạn |
| 403  | Forbidden - Không có quyền                     |
| 404  | Not Found - Resource không tồn tại             |
| 500  | Server Error - Lỗi server                      |

---

## 🧪 Testing API

### Với cURL:

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@example.com","password":"pass123","fullName":"Test User"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"pass123"}'

# Get Current User (dengan token)
curl -X GET http://localhost:8080/api/users/me \
  -H "Authorization: Bearer YOUR_TOKEN"

# Get All Tags
curl -X GET "http://localhost:8080/api/tags?page=1&size=10" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## 📞 Troubleshooting

### CORS Issues

Nếu gặp lỗi CORS, đảm bảo backend có cấu hình:

```java
@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurer corsConfigurer() {
        return (config) -> {
            config
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
        };
    }
}
```

### Token Not Working

1. Kiểm tra token có trong localStorage hay không
2. Kiểm tra token có hợp lệ không (không hết hạn)
3. Xem browser console có lỗi gì không

### API Connection Failed

1. Kiểm tra backend có chạy trên port 8080 không
2. Kiểm tra VITE_API_BASE_URL trong .env.local
3. Kiểm tra firewall/proxy settings
