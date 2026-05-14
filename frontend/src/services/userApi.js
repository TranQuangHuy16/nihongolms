import apiClient from './apiClient';

export const userApi = {
  getMe: () => apiClient.get('/users/me'),
  getAll: () => apiClient.get('/users'),
  update: (data) => apiClient.put('/users', data),
  changePassword: (data) => apiClient.post('/users/change-password', data),
};

export default userApi;
