import axios from 'axios';
import { useAuthStore } from '../store/authStore';

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add request interceptor to include JWT token
apiClient.interceptors.request.use(
  (config) => {
    let token = useAuthStore.getState().token;

    // If zustand persist hasn't rehydrated yet, fallback to localStorage
    if (!token) {
      try {
        const raw = localStorage.getItem('auth-store');
        if (raw) {
          const parsed = JSON.parse(raw);
          // persisted shape may be { state: { token, user } }
          token = parsed?.state?.token ?? parsed?.token ?? null;
        }
      } catch (e) {
        token = null;
      }
    }

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Add response interceptor to handle token refresh
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Handle unauthorized access
      useAuthStore.getState().logout();
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default apiClient;
