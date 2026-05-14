import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuthStore } from '../store/authStore';

export const Header = () => {
  const user = useAuthStore((state) => state.user);
  const logout = useAuthStore((state) => state.logout);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <header
      className="border-b backdrop-blur-xl sticky top-0 z-50"
      style={{
        background: 'rgba(15, 15, 26, 0.8)',
        borderColor: 'var(--border-glass)',
      }}
    >
      <nav className="container flex items-center justify-between h-16">
        <Link
          to="/"
          className="text-xl font-bold"
          style={{
            background: 'var(--gradient-primary)',
            WebkitBackgroundClip: 'text',
            WebkitTextFillColor: 'transparent',
          }}
        >
          Nihongo LMS
        </Link>

        {user && (
          <div className="flex items-center gap-6">
            <Link
              to="/dashboard"
              className="text-sm font-medium transition-colors duration-200"
              style={{ color: 'var(--text-secondary)' }}
              onMouseEnter={(e) => (e.target.style.color = 'var(--accent-indigo)')}
              onMouseLeave={(e) => (e.target.style.color = 'var(--text-secondary)')}
            >
              Dashboard
            </Link>
            <Link
              to="/tags"
              className="text-sm font-medium transition-colors duration-200"
              style={{ color: 'var(--text-secondary)' }}
              onMouseEnter={(e) => (e.target.style.color = 'var(--accent-indigo)')}
              onMouseLeave={(e) => (e.target.style.color = 'var(--text-secondary)')}
            >
              Tags
            </Link>
            <Link
              to="/profile"
              className="text-sm font-medium transition-colors duration-200"
              style={{ color: 'var(--text-secondary)' }}
              onMouseEnter={(e) => (e.target.style.color = 'var(--accent-indigo)')}
              onMouseLeave={(e) => (e.target.style.color = 'var(--text-secondary)')}
            >
              {user.displayName || user.username}
            </Link>
            <button onClick={handleLogout} className="btn-secondary text-sm">
              Logout
            </button>
          </div>
        )}
      </nav>
    </header>
  );
};

export default Header;
