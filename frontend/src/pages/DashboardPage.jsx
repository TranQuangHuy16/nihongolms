import React from 'react';
import { useAuthStore } from '../store/authStore';

export const DashboardPage = () => {
  const user = useAuthStore((state) => state.user);

  return (
    <div className="container py-8">
      <h1 className="text-3xl font-bold mb-8">Dashboard</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {/* Welcome Card */}
        <div className="card md:col-span-3">
          <h2 className="text-2xl font-bold mb-2">
            Chào mừng, {user?.fullName || user?.username}!
          </h2>
          <p className="text-gray-600">
            Hệ thống quản lý Nhật ngữ - Quản lý tags, người dùng và hỗ trợ học
            tập
          </p>
        </div>

        {/* Stats Cards */}
        <div className="card">
          <h3 className="text-gray-600 text-sm font-medium mb-2">Tài Khoản</h3>
          <p className="text-3xl font-bold text-blue-600">{user?.username}</p>
          <p className="text-gray-500 text-sm mt-2">Đang đăng nhập</p>
        </div>

        <div className="card">
          <h3 className="text-gray-600 text-sm font-medium mb-2">Email</h3>
          <p className="text-lg font-bold text-green-600">{user?.email}</p>
          <p className="text-gray-500 text-sm mt-2">Liên hệ chính</p>
        </div>

        <div className="card">
          <h3 className="text-gray-600 text-sm font-medium mb-2">Trạng Thái</h3>
          <p className="text-lg font-bold text-green-600">Hoạt Động</p>
          <p className="text-gray-500 text-sm mt-2">Tất cả chức năng</p>
        </div>
      </div>

      {/* Quick Links */}
      <div className="card mt-8">
        <h2 className="text-xl font-bold mb-6">Liên Kết Nhanh</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <a
            href="/tags"
            className="p-4 border border-blue-300 rounded-lg hover:bg-blue-50 transition"
          >
            <h3 className="font-semibold text-blue-600 mb-1">Quản Lý Tags</h3>
            <p className="text-sm text-gray-600">
              Tạo, chỉnh sửa và quản lý các tags
            </p>
          </a>
          <a
            href="/users"
            className="p-4 border border-green-300 rounded-lg hover:bg-green-50 transition"
          >
            <h3 className="font-semibold text-green-600 mb-1">
              Quản Lý Người Dùng
            </h3>
            <p className="text-sm text-gray-600">
              Xem và quản lý danh sách người dùng
            </p>
          </a>
          <a
            href="/profile"
            className="p-4 border border-purple-300 rounded-lg hover:bg-purple-50 transition"
          >
            <h3 className="font-semibold text-purple-600 mb-1">
              Hồ Sơ Cá Nhân
            </h3>
            <p className="text-sm text-gray-600">
              Cập nhật thông tin và đổi mật khẩu
            </p>
          </a>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
