import { create } from 'zustand';

export const useUserStore = create((set) => ({
  users: [],
  isLoading: false,
  error: null,

  setUsers: (users) => set({ users }),
  setLoading: (isLoading) => set({ isLoading }),
  setError: (error) => set({ error }),

  clearError: () => set({ error: null }),
}));
