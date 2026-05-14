import React from 'react';
import { useAuthStore } from '../store/authStore';
import { Link } from 'react-router-dom';

const quickLinks = [
  {
    to: '/tags',
    title: 'Quản Lý Tags',
    desc: 'Tạo, chỉnh sửa và quản lý các tags cho từ vựng',
    gradient: 'linear-gradient(135deg, #6366f1, #8b5cf6)',
    shadowColor: 'rgba(99, 102, 241, 0.2)',
    icon: (
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
        <path d="M20.59 13.41l-7.17 7.17a2 2 0 01-2.83 0L2 12V2h10l8.59 8.59a2 2 0 010 2.82z" />
        <line x1="7" y1="7" x2="7.01" y2="7" />
      </svg>
    ),
  },
  {
    to: '/profile',
    title: 'Hồ Sơ Cá Nhân',
    desc: 'Cập nhật thông tin cá nhân và đổi mật khẩu',
    gradient: 'linear-gradient(135deg, #ec4899, #8b5cf6)',
    shadowColor: 'rgba(236, 72, 153, 0.2)',
    icon: (
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
        <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2" />
        <circle cx="12" cy="7" r="4" />
      </svg>
    ),
  },
];

export const DashboardPage = () => {
  const user = useAuthStore((state) => state.user);

  return (
    <div className="p-6 lg:p-8 max-w-6xl mx-auto">
      {/* Welcome Section */}
      <div className="card animate-fade-in-up mb-8" style={{ background: 'var(--gradient-primary)', border: 'none' }}>
        <div className="flex items-start justify-between">
          <div>
            <p className="text-sm font-medium text-white/70 mb-1">👋 Xin chào</p>
            <h1 className="text-2xl lg:text-3xl font-bold text-white mb-2">
              {user?.displayName || user?.username || 'Bạn'}!
            </h1>
            <p className="text-white/60 text-sm max-w-lg">
              Chào mừng bạn đến với Nihongo LMS — Hệ thống quản lý học Nhật ngữ hiện đại.
              Quản lý tags, từ vựng và theo dõi tiến trình học tập của bạn.
            </p>
          </div>
          <div className="hidden md:block text-6xl opacity-30">🎌</div>
        </div>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-5 mb-8">
        <div className="card card-interactive animate-fade-in-up stagger-1">
          <div className="flex items-center gap-3 mb-3">
            <div
              className="w-10 h-10 rounded-xl flex items-center justify-center"
              style={{ background: 'rgba(99, 102, 241, 0.15)' }}
            >
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="var(--accent-indigo)" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2" />
                <circle cx="12" cy="7" r="4" />
              </svg>
            </div>
            <p className="text-xs font-semibold uppercase tracking-wider" style={{ color: 'var(--text-muted)' }}>
              Tài Khoản
            </p>
          </div>
          <p className="text-xl font-bold" style={{ color: 'var(--accent-indigo)' }}>
            {user?.username}
          </p>
          <p className="text-xs mt-1" style={{ color: 'var(--text-muted)' }}>Đang đăng nhập</p>
        </div>

        <div className="card card-interactive animate-fade-in-up stagger-2">
          <div className="flex items-center gap-3 mb-3">
            <div
              className="w-10 h-10 rounded-xl flex items-center justify-center"
              style={{ background: 'rgba(52, 211, 153, 0.15)' }}
            >
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="var(--accent-emerald)" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z" />
                <polyline points="22,6 12,13 2,6" />
              </svg>
            </div>
            <p className="text-xs font-semibold uppercase tracking-wider" style={{ color: 'var(--text-muted)' }}>
              Email
            </p>
          </div>
          <p className="text-base font-bold truncate" style={{ color: 'var(--accent-emerald)' }}>
            {user?.email || '—'}
          </p>
          <p className="text-xs mt-1" style={{ color: 'var(--text-muted)' }}>Liên hệ chính</p>
        </div>

        <div className="card card-interactive animate-fade-in-up stagger-3">
          <div className="flex items-center gap-3 mb-3">
            <div
              className="w-10 h-10 rounded-xl flex items-center justify-center"
              style={{ background: 'rgba(251, 191, 36, 0.15)' }}
            >
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="var(--accent-amber)" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M22 11.08V12a10 10 0 11-5.93-9.14" />
                <polyline points="22 4 12 14.01 9 11.01" />
              </svg>
            </div>
            <p className="text-xs font-semibold uppercase tracking-wider" style={{ color: 'var(--text-muted)' }}>
              Trạng Thái
            </p>
          </div>
          <p className="text-xl font-bold" style={{ color: 'var(--accent-amber)' }}>
            Hoạt Động
          </p>
          <p className="text-xs mt-1" style={{ color: 'var(--text-muted)' }}>Tất cả chức năng</p>
        </div>
      </div>

      {/* Quick Links */}
      <div className="animate-fade-in-up stagger-4">
        <h2 className="text-lg font-bold mb-4" style={{ color: 'var(--text-primary)' }}>
          Liên Kết Nhanh
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
          {quickLinks.map((link) => (
            <Link key={link.to} to={link.to} className="block group">
              <div
                className="card card-interactive flex items-start gap-4"
                style={{ cursor: 'pointer' }}
              >
                <div
                  className="w-12 h-12 rounded-xl flex items-center justify-center text-white shrink-0 group-hover:scale-110 transition-transform duration-300"
                  style={{ background: link.gradient, boxShadow: `0 4px 15px ${link.shadowColor}` }}
                >
                  {link.icon}
                </div>
                <div>
                  <h3 className="font-semibold mb-1" style={{ color: 'var(--text-primary)' }}>
                    {link.title}
                  </h3>
                  <p className="text-sm" style={{ color: 'var(--text-muted)' }}>
                    {link.desc}
                  </p>
                </div>
              </div>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
