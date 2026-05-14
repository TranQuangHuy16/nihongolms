import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import { useAuthStore } from '../store/authStore';
import userApi from '../services/userApi';

export const ProfilePage = () => {
  const setUser = useAuthStore((state) => state.setUser);
  const [formData, setFormData] = useState({
    displayName: '',
    email: '',
    phoneNumber: '',
    avatarUrl: '',
  });
  const [passwordData, setPasswordData] = useState({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  });
  const [isLoading, setIsLoading] = useState(false);
  const [isFetching, setIsFetching] = useState(true);
  const [userData, setUserData] = useState(null);

  // Fetch user data from /users/me on mount
  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    setIsFetching(true);
    try {
      const response = await userApi.getMe();
      const data = response.data.data;
      setUserData(data);
      setFormData({
        displayName: data?.displayName || '',
        email: data?.email || '',
        phoneNumber: data?.phoneNumber || '',
        avatarUrl: data?.avatarUrl || '',
      });
      // Also update global auth store
      setUser(data);
    } catch (error) {
      toast.error('Không thể tải thông tin hồ sơ');
    } finally {
      setIsFetching(false);
    }
  };

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
      // PUT /users with { displayName, email, phoneNumber, avatarUrl }
      const response = await userApi.update({
        displayName: formData.displayName,
        email: formData.email,
        phoneNumber: formData.phoneNumber,
        avatarUrl: formData.avatarUrl,
      });
      const updated = response.data.data;
      setUser(updated);
      setUserData(updated);
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
      toast.error('Mật khẩu mới không khớp!');
      return;
    }

    setIsLoading(true);

    try {
      await userApi.changePassword({
        currentPassword: passwordData.currentPassword,
        newPassword: passwordData.newPassword,
        confirmPassword: passwordData.confirmPassword,
      });
      setPasswordData({
        currentPassword: '',
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

  if (isFetching) {
    return (
      <div className="flex items-center justify-center min-h-[60vh]">
        <div className="flex items-center gap-3" style={{ color: 'var(--text-muted)' }}>
          <svg className="animate-spin h-5 w-5" viewBox="0 0 24 24">
            <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
            <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
          </svg>
          Đang tải hồ sơ...
        </div>
      </div>
    );
  }

  return (
    <div className="p-6 lg:p-8 max-w-3xl mx-auto">
      {/* Header */}
      <div className="mb-8 animate-fade-in-up">
        <h1 className="text-2xl lg:text-3xl font-bold" style={{ color: 'var(--text-primary)' }}>
          Hồ Sơ Cá Nhân
        </h1>
        <p className="text-sm mt-1" style={{ color: 'var(--text-muted)' }}>
          Quản lý thông tin cá nhân và bảo mật tài khoản
        </p>
      </div>

      {/* Avatar Preview */}
      <div className="card mb-6 animate-fade-in-up stagger-1">
        <div className="flex items-center gap-5">
          <div className="relative">
            {formData.avatarUrl ? (
              <img
                src={formData.avatarUrl}
                alt="Avatar"
                className="w-20 h-20 rounded-2xl object-cover"
                style={{ border: '2px solid var(--border-glass)' }}
                onError={(e) => {
                  e.target.style.display = 'none';
                  e.target.nextSibling.style.display = 'flex';
                }}
              />
            ) : null}
            <div
              className={`w-20 h-20 rounded-2xl items-center justify-center text-white font-bold text-2xl ${formData.avatarUrl ? 'hidden' : 'flex'}`}
              style={{ background: 'var(--gradient-accent)' }}
            >
              {(userData?.displayName || userData?.username || '?')[0].toUpperCase()}
            </div>
          </div>
          <div>
            <h2 className="text-xl font-bold" style={{ color: 'var(--text-primary)' }}>
              {userData?.displayName || userData?.username || '—'}
            </h2>
            <p className="text-sm" style={{ color: 'var(--text-muted)' }}>
              @{userData?.username || '—'}
            </p>
            <span
              className="inline-block text-xs px-2.5 py-0.5 rounded-lg mt-1.5 font-medium"
              style={{
                background: 'rgba(52, 211, 153, 0.1)',
                color: 'var(--accent-emerald)',
                border: '1px solid rgba(52, 211, 153, 0.2)',
              }}
            >
              {userData?.role || 'USER'}
            </span>
          </div>
        </div>
      </div>

      {/* Update Profile Form */}
      <div className="card mb-6 animate-fade-in-up stagger-2">
        <div className="flex items-center gap-3 mb-6">
          <div
            className="w-9 h-9 rounded-xl flex items-center justify-center"
            style={{ background: 'rgba(99, 102, 241, 0.15)' }}
          >
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="var(--accent-indigo)" strokeWidth="2">
              <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2" />
              <circle cx="12" cy="7" r="4" />
            </svg>
          </div>
          <h2 className="text-lg font-bold" style={{ color: 'var(--text-primary)' }}>
            Cập Nhật Thông Tin
          </h2>
        </div>

        <form onSubmit={handleUpdateProfile}>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-x-5">
            <div className="form-group">
              <label htmlFor="profile-displayName" className="form-label">
                Tên Hiển Thị
              </label>
              <input
                id="profile-displayName"
                type="text"
                name="displayName"
                className="form-input"
                placeholder="Nhập tên hiển thị..."
                value={formData.displayName}
                onChange={handleProfileChange}
              />
            </div>

            <div className="form-group">
              <label htmlFor="profile-email" className="form-label">
                Email
              </label>
              <input
                id="profile-email"
                type="email"
                name="email"
                className="form-input"
                placeholder="Nhập email..."
                value={formData.email}
                onChange={handleProfileChange}
              />
            </div>

            <div className="form-group">
              <label htmlFor="profile-phoneNumber" className="form-label">
                Số Điện Thoại
              </label>
              <input
                id="profile-phoneNumber"
                type="tel"
                name="phoneNumber"
                className="form-input"
                placeholder="Nhập số điện thoại..."
                value={formData.phoneNumber}
                onChange={handleProfileChange}
              />
            </div>

            <div className="form-group">
              <label htmlFor="profile-avatarUrl" className="form-label">
                Avatar URL
              </label>
              <input
                id="profile-avatarUrl"
                type="url"
                name="avatarUrl"
                className="form-input"
                placeholder="https://example.com/avatar.jpg"
                value={formData.avatarUrl}
                onChange={handleProfileChange}
              />
            </div>
          </div>

          <button type="submit" className="btn-primary mt-1" disabled={isLoading}>
            {isLoading ? (
              <span className="flex items-center gap-2">
                <svg className="animate-spin h-4 w-4" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
                </svg>
                Đang xử lý...
              </span>
            ) : (
              'Lưu Thay Đổi'
            )}
          </button>
        </form>
      </div>

      {/* Change Password */}
      <div className="card animate-fade-in-up stagger-3">
        <div className="flex items-center gap-3 mb-6">
          <div
            className="w-9 h-9 rounded-xl flex items-center justify-center"
            style={{ background: 'rgba(251, 191, 36, 0.15)' }}
          >
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="var(--accent-amber)" strokeWidth="2">
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2" />
              <path d="M7 11V7a5 5 0 0110 0v4" />
            </svg>
          </div>
          <h2 className="text-lg font-bold" style={{ color: 'var(--text-primary)' }}>
            Đổi Mật Khẩu
          </h2>
        </div>

        <form onSubmit={handleChangePassword}>
          <div className="form-group">
            <label htmlFor="profile-currentPassword" className="form-label">
              Mật Khẩu Hiện Tại
            </label>
            <input
              id="profile-currentPassword"
              type="password"
              name="currentPassword"
              className="form-input"
              placeholder="Nhập mật khẩu hiện tại..."
              value={passwordData.currentPassword}
              onChange={handlePasswordChange}
              required
            />
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-x-5">
            <div className="form-group">
              <label htmlFor="profile-newPassword" className="form-label">
                Mật Khẩu Mới
              </label>
              <input
                id="profile-newPassword"
                type="password"
                name="newPassword"
                className="form-input"
                placeholder="Tối thiểu 6 ký tự..."
                value={passwordData.newPassword}
                onChange={handlePasswordChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="profile-confirmPassword" className="form-label">
                Xác Nhận Mật Khẩu Mới
              </label>
              <input
                id="profile-confirmPassword"
                type="password"
                name="confirmPassword"
                className="form-input"
                placeholder="Nhập lại mật khẩu mới..."
                value={passwordData.confirmPassword}
                onChange={handlePasswordChange}
                required
              />
            </div>
          </div>

          <button type="submit" className="btn-primary" disabled={isLoading}>
            {isLoading ? (
              <span className="flex items-center gap-2">
                <svg className="animate-spin h-4 w-4" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
                </svg>
                Đang xử lý...
              </span>
            ) : (
              'Đổi Mật Khẩu'
            )}
          </button>
        </form>
      </div>
    </div>
  );
};

export default ProfilePage;
