import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import { useAuthStore } from '../store/authStore';
import authApi from '../services/authApi';

export const RegisterPage = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    fullName: '',
  });
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const { login } = useAuthStore();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      toast.error('Mật khẩu không khớp!');
      return;
    }

    setIsLoading(true);

    try {
      const response = await authApi.register(formData);
      const { data } = response.data;

      login(data.user, data.accessToken);
      toast.success('Đăng ký thành công!');
      navigate('/dashboard');
    } catch (error) {
      const message = error.response?.data?.message || 'Đăng ký thất bại';
      toast.error(message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div
      className="min-h-screen flex items-center justify-center px-4 py-8"
      style={{ background: 'var(--bg-primary)' }}
    >
      {/* Background decoration */}
      <div className="fixed inset-0 overflow-hidden pointer-events-none">
        <div
          className="absolute -top-40 -left-40 w-80 h-80 rounded-full opacity-15 blur-3xl"
          style={{ background: 'var(--gradient-accent)' }}
        />
        <div
          className="absolute -bottom-40 -right-40 w-80 h-80 rounded-full opacity-20 blur-3xl"
          style={{ background: 'var(--gradient-primary)' }}
        />
      </div>

      <div className="card w-full max-w-md animate-fade-in-up relative z-10">
        {/* Logo */}
        <div className="text-center mb-8">
          <div
            className="w-14 h-14 rounded-2xl flex items-center justify-center text-white font-bold text-xl mx-auto mb-4"
            style={{ background: 'var(--gradient-accent)', boxShadow: '0 4px 20px rgba(236, 72, 153, 0.3)' }}
          >
            日
          </div>
          <h2 className="text-2xl font-bold" style={{ color: 'var(--text-primary)' }}>
            Tạo tài khoản mới
          </h2>
          <p className="text-sm mt-1" style={{ color: 'var(--text-muted)' }}>
            Bắt đầu hành trình học Nhật ngữ
          </p>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="reg-fullName" className="form-label">
              Họ và Tên
            </label>
            <input
              id="reg-fullName"
              type="text"
              name="fullName"
              className="form-input"
              placeholder="Nhập họ và tên..."
              value={formData.fullName}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="reg-username" className="form-label">
              Username
            </label>
            <input
              id="reg-username"
              type="text"
              name="username"
              className="form-input"
              placeholder="Nhập username..."
              value={formData.username}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="reg-email" className="form-label">
              Email
            </label>
            <input
              id="reg-email"
              type="email"
              name="email"
              className="form-input"
              placeholder="Nhập email..."
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="reg-password" className="form-label">
              Mật khẩu
            </label>
            <input
              id="reg-password"
              type="password"
              name="password"
              className="form-input"
              placeholder="Tối thiểu 6 ký tự..."
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="reg-confirmPassword" className="form-label">
              Xác nhận mật khẩu
            </label>
            <input
              id="reg-confirmPassword"
              type="password"
              name="confirmPassword"
              className="form-input"
              placeholder="Nhập lại mật khẩu..."
              value={formData.confirmPassword}
              onChange={handleChange}
              required
            />
          </div>

          <button
            type="submit"
            className="btn-primary w-full mb-4 py-3"
            disabled={isLoading}
          >
            {isLoading ? (
              <span className="flex items-center justify-center gap-2">
                <svg className="animate-spin h-4 w-4" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
                </svg>
                Đang xử lý...
              </span>
            ) : (
              'Đăng Ký'
            )}
          </button>
        </form>

        <p className="text-center text-sm" style={{ color: 'var(--text-muted)' }}>
          Đã có tài khoản?{' '}
          <Link
            to="/login"
            className="font-semibold transition-colors duration-200"
            style={{ color: 'var(--accent-indigo)' }}
            onMouseEnter={(e) => (e.target.style.color = 'var(--accent-violet)')}
            onMouseLeave={(e) => (e.target.style.color = 'var(--accent-indigo)')}
          >
            Đăng nhập
          </Link>
        </p>
      </div>
    </div>
  );
};

export default RegisterPage;
