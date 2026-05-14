# ✅ Installation & Setup Verification

**Tài liệu này giúp bạn xác nhận mọi thứ được cài đặt đúng.**

## 🔍 Step 1: Kiểm Tra Project Structure

Chạy lệnh này từ folder `frontend/`:

```bash
ls -la
```

Bạn sẽ thấy:

```
.env.example
.env.local
.gitignore
.prettierrc
API_DOCUMENTATION.md
DEVELOPMENT_GUIDE.md
INSTALLATION_GUIDE.md
PROJECT_SUMMARY.md
QUICKSTART.md
README.md
index.html
package.json
postcss.config.js
src/
tailwind.config.js
vite.config.js
```

✅ **Nếu tất cả đều có, bạn có thể tiếp tục bước 2**

---

## 🔍 Step 2: Kiểm Tra Src Directory

```bash
ls -la src/
```

Sẽ thấy:

```
App.jsx
components/
index.css
main.jsx
pages/
services/
store/
utils/
```

✅ **Nếu tất cả folder đều có, bạn có thể tiếp tục bước 3**

---

## 🔍 Step 3: Cài Đặt Dependencies

```bash
npm install
```

Chờ cho đến khi hoàn tất (2-3 phút). Bạn sẽ thấy:

```
added XXX packages, and audited XXX packages in XXs
```

✅ **Nếu không có lỗi, bạn có thể tiếp tục bước 4**

---

## 🔍 Step 4: Chạy Development Server

```bash
npm run dev
```

Bạn sẽ thấy:

```
VITE v5.0.0  ready in 234 ms

➜  Local:   http://localhost:3000/
➜  press h to show help
```

✅ **Nếu server chạy không lỗi, hãy mở browser ở http://localhost:3000**

---

## 🔍 Step 5: Kiểm Tra Login Page

Bạn nên thấy:

- ✅ Trang login với form username/password
- ✅ Link đến trang Register
- ✅ Style Tailwind CSS đúng (background xám, form đẹp)
- ✅ Nút "Đăng Nhập"

---

## 🔍 Step 6: Kiểm Tra Kết Nối API

1. **Mở Browser DevTools** (F12)
2. **Vào tab Network**
3. **Click "Đăng Nhập"** (có thể fail vì chưa cấu hình backend)
4. Bạn sẽ thấy request đến `http://localhost:8080/api/auth/login`

✅ **Nếu bạn thấy request gửi đi, API integration hoạt động!**

---

## 🔍 Step 7: Kiểm Tra LocalStorage

1. **Mở Browser DevTools** (F12)
2. **Vào tab Application → Storage → Local Storage**
3. Kiểm tra `localhost:3000`
4. Bạn sẽ thấy key `auth-store` (sau khi đăng nhập thành công)

✅ **Nếu thấy, state persistence hoạt động!**

---

## ⚠️ Troubleshooting

### Issue: "Cannot find module 'react'"

**Solution:**

```bash
npm install
```

### Issue: Port 3000 đã được sử dụng

**Solution:**

```bash
npm run dev -- --port 3001
```

### Issue: "CORS error" khi API call

**Solution:**

1. Kiểm tra backend có chạy trên `http://localhost:8080/api`
2. Nếu backend khác, cập nhật `.env.local`:
   ```
   VITE_API_BASE_URL=http://your-backend-url/api
   ```

### Issue: Blank page / Error 404

**Solution:**

1. Refresh page (Ctrl+F5)
2. Xem browser console (F12) có error không
3. Kiểm tra terminal có error không

---

## 📋 Checklist - Tất Cả Sẵn Sàng?

Đánh dấu ✅ khi hoàn tất:

```
□ Tất cả file configuration có mặt
□ Folder src/ có đầy đủ subfolders
□ npm install hoàn tất không lỗi
□ npm run dev chạy thành công
□ http://localhost:3000 mở được
□ Trang login hiển thị đúng
□ DevTools Network tab thấy API requests
□ LocalStorage có auth-store key
```

---

## 🎉 Nếu Tất Cả Đều ✅

Chúc mừng! Frontend của bạn đã sẵn sàng:

1. **Tiếp theo:**
   - Đảm bảo backend chạy trên port 8080
   - Cấu hình CORS trên backend (cho phép origin `http://localhost:3000`)
   - Đăng nhập để test flow complete

2. **Để phát triển thêm:**
   - Đọc `DEVELOPMENT_GUIDE.md`
   - Tham khảo các page có sẵn
   - Follow coding patterns

3. **Để hiểu API:**
   - Đọc `API_DOCUMENTATION.md`
   - Test API bằng cURL hoặc Postman

---

## 📚 Tài Liệu Tiếp Theo

Đã hoàn tất setup? Tiếp tục với:

1. **QUICKSTART.md** - Hướng dẫn nhanh (5 bước)
2. **README.md** - Tổng quan dự án
3. **API_DOCUMENTATION.md** - Chi tiết API endpoints
4. **DEVELOPMENT_GUIDE.md** - Phát triển tính năng mới
5. **PROJECT_SUMMARY.md** - Tổng thể hoàn chỉnh

---

## 💡 Tips

- Giữ terminal `npm run dev` chạy trong quá trình phát triển
- Mở DevTools (F12) để debug
- Sử dụng `console.log()` để debug
- Kiểm tra Network tab khi API fail
- Đọc error messages cẩn thận!

---

**Ready? Let's Code! 🚀**

Nếu gặp vấn đề, check lại các bước hoặc xem troubleshooting section.
