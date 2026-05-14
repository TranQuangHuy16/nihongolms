import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export const useAuthStore = create(
  persist(
    (set) => ({
      user: null,
      token: null,
      isLoading: false,
      error: null,

      setUser: (user) => set({ user }),
      setToken: (token) => set({ token }),
      setLoading: (isLoading) => set({ isLoading }),
      setError: (error) => set({ error }),

      login: (user, token) => {
        try {
          const id = user?.id ?? user?._id ?? null;
          const role = user?.role ?? null;
          if (id) localStorage.setItem('userId', id);
          if (role) localStorage.setItem('userRole', role);
        } catch (e) {
          // ignore
        }
        set({ user, token, error: null });
      },

      logout: () => {
        try {
          localStorage.removeItem('userId');
          localStorage.removeItem('userRole');
        } catch (e) {}
        set({ user: null, token: null });
      },

      clearError: () => set({ error: null }),
    }),
    {
      name: 'auth-store',
      partialize: (state) => ({
        user: state.user,
        token: state.token,
      }),
    }
  )
);
