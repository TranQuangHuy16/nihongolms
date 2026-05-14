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
    <header className="bg-white shadow">
      <nav className="container flex items-center justify-between h-16">
        <Link to="/" className="text-xl font-bold text-blue-600">
          Nihongo LMS
        </Link>

        {user && (
          <div className="flex items-center gap-6">
            <Link to="/dashboard" className="text-gray-700 hover:text-blue-600">
              Dashboard
            </Link>
            <Link to="/tags" className="text-gray-700 hover:text-blue-600">
              Tags
            </Link>
            <Link to="/users" className="text-gray-700 hover:text-blue-600">
              Users
            </Link>
            <Link to="/profile" className="text-gray-700 hover:text-blue-600">
              {user.fullName || user.username}
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
