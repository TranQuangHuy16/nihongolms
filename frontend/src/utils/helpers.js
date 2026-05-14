// Utility functions for form handling and validation

export const validateEmail = (email) => {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email);
};

export const validatePassword = (password) => {
  // Minimum 6 characters
  return password && password.length >= 6;
};

export const validateUsername = (username) => {
  // Alphanumeric and underscore, 3-20 characters
  const re = /^[a-zA-Z0-9_]{3,20}$/;
  return re.test(username);
};

export const formatDate = (dateString) => {
  const options = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  };
  return new Date(dateString).toLocaleDateString('vi-VN', options);
};

export const truncateText = (text, length = 50) => {
  if (!text) return '';
  return text.length > length ? text.substring(0, length) + '...' : text;
};

export const handleApiError = (error) => {
  if (error.response) {
    return error.response.data?.message || 'Có lỗi xảy ra';
  }
  if (error.request) {
    return 'Không thể kết nối đến server';
  }
  return 'Có lỗi không xác định';
};
