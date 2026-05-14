import apiClient from './apiClient';

export const tagApi = {
  getTags: (search = '', page = 1, size = 10, sortDir = 'desc') =>
    apiClient.get('/tags', {
      params: { search, page, size, sortDir },
    }),
  create: (data) => apiClient.post('/tags', data),
  update: (id, data) => apiClient.put(`/tags/${id}`, data),
  delete: (id) => apiClient.delete(`/tags/${id}`),
};

export default tagApi;
