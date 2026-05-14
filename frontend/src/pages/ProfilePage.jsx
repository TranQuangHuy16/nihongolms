import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import { useAuthStore } from '../store/authStore';
import userApi from '../services/userApi';

export const ProfilePage = () => {
  const user = useAuthStore((state) => state.user);
  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
  });
  const [passwordData, setPasswordData] = useState({
    oldPassword: '',
    newPassword: '',
    confirmPassword: '',
  });
  const [isLoading, setIsLoading] = useState(false);
  const setUser = useAuthStore((state) => state.setUser);

  useEffect(() => {
    if (user) {
      setFormData({
        fullName: user.fullName || '',
        email: user.email || '',
      });
    }
  }, [user]);

  const handleProfileChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handlePasswordChange = (e) => {
    const { name, value } = e.target;
    setPasswordData((prev) => ({ ...prev, [name]: value }));
  };

  const handleUpdateProfile = async (e) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      const response = await userApi.update(formData);
      setUser(response.data.data);
      toast.success('Cập nhật thông tin thành công!');
    } catch (error) {
      const message = error.response?.data?.message || 'Cập nhật thất bại';
      toast.error(message);
    } finally {
      setIsLoading(false);
    }
  };

  const handleChangePassword = async (e) => {
    e.preventDefault();

    if (passwordData.newPassword !== passwordData.confirmPassword) {
      toast.error('Mật khẩu không khớp!');
      return;
    }

    setIsLoading(true);

    try {
      await userApi.changePassword({
        oldPassword: passwordData.oldPassword,
        newPassword: passwordData.newPassword,
      });
      setPasswordData({
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
      });
      toast.success('Đổi mật khẩu thành công!');
    } catch (error) {
      const message = error.response?.data?.message || 'Đổi mật khẩu thất bại';
      toast.error(message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="container py-8">
      <div className="max-w-2xl mx-auto">
        <h1 className="text-3xl font-bold mb-8">Thông Tin Hồ Sơ</h1>

        {/* Update Profile */}
        <div className="card mb-8">
          <h2 className="text-xl font-bold mb-6">Cập Nhật Thông Tin</h2>
          <form onSubmit={handleUpdateProfile}>
            <div className="form-group">
              <label htmlFor="fullName" className="form-label">
                Họ và Tên
              </label>
              <input
                id="fullName"
                type="text"
                name="fullName"
                className="form-input"
                value={formData.fullName}
                onChange={handleProfileChange}
              />
            </div>

            <div className="form-group">
              <label htmlFor="email" className="form-label">
                Email
              </label>
              <input
                id="email"
                type="email"
                name="email"
                className="form-input"
                value={formData.email}
                onChange={handleProfileChange}
              />
            </div>

            <button type="submit" className="btn-primary" disabled={isLoading}>
              {isLoading ? 'Đang xử lý...' : 'Cập Nhật'}
            </button>
          </form>
        </div>

        {/* Change Password */}
        <div className="card">
          <h2 className="text-xl font-bold mb-6">Đổi Mật Khẩu</h2>
          <form onSubmit={handleChangePassword}>
            <div className="form-group">
              <label htmlFor="oldPassword" className="form-label">
                Mật Khẩu Cũ
              </label>
              <input
                id="oldPassword"
                type="password"
                name="oldPassword"
                className="form-input"
                value={passwordData.oldPassword}
                onChange={handlePasswordChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="newPassword" className="form-label">
                Mật Khẩu Mới
              </label>
              <input
                id="newPassword"
                type="password"
                name="newPassword"
                className="form-input"
                value={passwordData.newPassword}
                onChange={handlePasswordChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="confirmPassword" className="form-label">
                Xác Nhận Mật Khẩu Mới
              </label>
              <input
                id="confirmPassword"
                type="password"
                name="confirmPassword"
                className="form-input"
                value={passwordData.confirmPassword}
                onChange={handlePasswordChange}
                required
              />
            </div>

            <button type="submit" className="btn-primary" disabled={isLoading}>
              {isLoading ? 'Đang xử lý...' : 'Đổi Mật Khẩu'}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
