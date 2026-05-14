import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import { useUserStore } from '../store/userStore';
import userApi from '../services/userApi';

export const UsersPage = () => {
  const { users, isLoading, setUsers, setLoading } = useUserStore();

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    setLoading(true);
    try {
      const response = await userApi.getAll();
      setUsers(response.data.data || []);
    } catch (error) {
      toast.error('Không thể tải danh sách người dùng');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container py-8">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold">Quản Lý Người Dùng</h1>
        <button onClick={fetchUsers} className="btn-primary">
          Làm Mới
        </button>
      </div>

      {isLoading && <p className="text-center text-gray-600">Đang tải...</p>}

      {!isLoading && users.length === 0 && (
        <p className="text-center text-gray-600">Không có người dùng nào</p>
      )}

      {!isLoading && users.length > 0 && (
        <div className="overflow-x-auto card">
          <table className="w-full">
            <thead>
              <tr className="border-b">
                <th className="text-left py-3 px-4 font-semibold">Username</th>
                <th className="text-left py-3 px-4 font-semibold">Email</th>
                <th className="text-left py-3 px-4 font-semibold">Họ và Tên</th>
                <th className="text-left py-3 px-4 font-semibold">Ngày Tạo</th>
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr key={user.id} className="border-b hover:bg-gray-50">
                  <td className="py-3 px-4">{user.username}</td>
                  <td className="py-3 px-4">{user.email}</td>
                  <td className="py-3 px-4">{user.fullName || '-'}</td>
                  <td className="py-3 px-4 text-sm text-gray-600">
                    {new Date(user.createdAt).toLocaleDateString('vi-VN')}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default UsersPage;
