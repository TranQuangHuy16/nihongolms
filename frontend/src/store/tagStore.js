import { create } from 'zustand';

export const useTagStore = create((set) => ({
  tags: [],
  isLoading: false,
  error: null,
  pagination: {
    page: 1,
    size: 10,
    total: 0,
  },

  setTags: (tags) => set({ tags }),
  setLoading: (isLoading) => set({ isLoading }),
  setError: (error) => set({ error }),
  setPagination: (pagination) => set({ pagination }),

  clearError: () => set({ error: null }),
}));
