# Development Guide - Nihongo LMS Frontend

## 📚 Table of Contents

1. [Cấu Trúc Dự Án](#cấu-trúc-dự-án)
2. [Coding Conventions](#coding-conventions)
3. [Thêm Tính Năng Mới](#thêm-tính-năng-mới)
4. [Best Practices](#best-practices)
5. [Debugging](#debugging)
6. [Performance Tips](#performance-tips)

## 🗂️ Cấu Trúc Dự Án

```
frontend/
├── src/
│   ├── components/      # Reusable UI components
│   │   ├── Header.jsx   # Navigation header
│   │   └── ProtectedRoute.jsx # Route guard
│   │
│   ├── pages/          # Full page components
│   │   ├── LoginPage.jsx
│   │   ├── RegisterPage.jsx
│   │   ├── DashboardPage.jsx
│   │   ├── ProfilePage.jsx
│   │   ├── TagsPage.jsx
│   │   └── UsersPage.jsx
│   │
│   ├── services/       # API integration
│   │   ├── apiClient.js        # Axios instance
│   │   ├── authApi.js          # Auth endpoints
│   │   ├── userApi.js          # User endpoints
│   │   └── tagApi.js           # Tag endpoints
│   │
│   ├── store/          # Zustand state management
│   │   ├── authStore.js
│   │   ├── userStore.js
│   │   └── tagStore.js
│   │
│   ├── utils/          # Utility functions
│   │   └── helpers.js
│   │
│   ├── App.jsx         # Main component + routing
│   ├── main.jsx        # Entry point
│   └── index.css       # Global styles
```

---

## 📋 Coding Conventions

### File Naming

- **Components**: PascalCase (e.g., `Header.jsx`, `ProfilePage.jsx`)
- **Services**: camelCase (e.g., `apiClient.js`, `authApi.js`)
- **Stores**: camelCase (e.g., `authStore.js`)
- **Utils**: camelCase (e.g., `helpers.js`)

### Component Structure

```jsx
// Import React
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

// Import hooks/stores
import { useAuthStore } from '../store/authStore';

// Import services
import userApi from '../services/userApi';

// Import styles
import '../styles/component.css'; // If needed

// Component definition
export const MyComponent = () => {
  // State
  const [data, setData] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  // Store access
  const user = useAuthStore((state) => state.user);

  // Hooks
  const navigate = useNavigate();

  // Effects
  useEffect(() => {
    fetchData();
  }, []);

  // Methods
  const fetchData = async () => {
    // Implementation
  };

  // Render
  return <div>Component JSX</div>;
};

export default MyComponent;
```

### API Call Pattern

```javascript
// src/services/exampleApi.js
import apiClient from './apiClient';

export const exampleApi = {
  getAll: () => apiClient.get('/examples'),
  getById: (id) => apiClient.get(`/examples/${id}`),
  create: (data) => apiClient.post('/examples', data),
  update: (id, data) => apiClient.put(`/examples/${id}`, data),
  delete: (id) => apiClient.delete(`/examples/${id}`),
};

export default exampleApi;
```

### Store Pattern

```javascript
// src/store/exampleStore.js
import { create } from 'zustand';

export const useExampleStore = create((set) => ({
  // State
  items: [],
  isLoading: false,
  error: null,

  // Setters
  setItems: (items) => set({ items }),
  setLoading: (isLoading) => set({ isLoading }),
  setError: (error) => set({ error }),

  // Actions
  clearError: () => set({ error: null }),
}));
```

---

## ✨ Thêm Tính Năng Mới

### Example: Thêm Feature "Quiz Management"

#### Step 1: Tạo API Service

```javascript
// src/services/quizApi.js
import apiClient from './apiClient';

export const quizApi = {
  getQuizzes: (page = 1, size = 10) =>
    apiClient.get('/quizzes', { params: { page, size } }),
  getQuizById: (id) => apiClient.get(`/quizzes/${id}`),
  create: (data) => apiClient.post('/quizzes', data),
  update: (id, data) => apiClient.put(`/quizzes/${id}`, data),
  delete: (id) => apiClient.delete(`/quizzes/${id}`),
  submitAnswers: (quizId, answers) =>
    apiClient.post(`/quizzes/${quizId}/submit`, answers),
};

export default quizApi;
```

#### Step 2: Tạo Zustand Store

```javascript
// src/store/quizStore.js
import { create } from 'zustand';

export const useQuizStore = create((set) => ({
  quizzes: [],
  currentQuiz: null,
  isLoading: false,
  results: null,

  setQuizzes: (quizzes) => set({ quizzes }),
  setCurrentQuiz: (quiz) => set({ currentQuiz: quiz }),
  setLoading: (isLoading) => set({ isLoading }),
  setResults: (results) => set({ results }),
}));
```

#### Step 3: Tạo Page Component

```jsx
// src/pages/QuizzesPage.jsx
import React, { useEffect } from 'react';
import { toast } from 'react-toastify';
import { useQuizStore } from '../store/quizStore';
import quizApi from '../services/quizApi';

export const QuizzesPage = () => {
  const { quizzes, isLoading, setQuizzes, setLoading } = useQuizStore();

  useEffect(() => {
    fetchQuizzes();
  }, []);

  const fetchQuizzes = async () => {
    setLoading(true);
    try {
      const response = await quizApi.getQuizzes();
      setQuizzes(response.data.data);
    } catch (error) {
      toast.error('Failed to load quizzes');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container py-8">
      <h1 className="text-3xl font-bold mb-8">Quizzes</h1>
      {/* Component content */}
    </div>
  );
};

export default QuizzesPage;
```

#### Step 4: Thêm Route

```jsx
// src/App.jsx
import QuizzesPage from './pages/QuizzesPage';

// In Routes:
<Route
  path="/quizzes"
  element={
    <ProtectedRoute>
      <QuizzesPage />
    </ProtectedRoute>
  }
/>;
```

#### Step 5: Thêm Navigation Link

```jsx
// src/components/Header.jsx
<Link to="/quizzes" className="text-gray-700 hover:text-blue-600">
  Quizzes
</Link>
```

---

## 🎯 Best Practices

### 1. Error Handling

```javascript
const handleFetch = async () => {
  try {
    const response = await userApi.getMe();
    return response.data;
  } catch (error) {
    const message = error.response?.data?.message || 'Unknown error';
    toast.error(message);
    throw error;
  }
};
```

### 2. Loading States

```jsx
const Component = () => {
  const [isLoading, setIsLoading] = useState(false);

  return (
    <>
      <button disabled={isLoading}>
        {isLoading ? 'Loading...' : 'Submit'}
      </button>
    </>
  );
};
```

### 3. Form Validation

```jsx
const validateForm = (data) => {
  if (!data.email || !data.password) {
    toast.error('Please fill all fields');
    return false;
  }

  if (!validateEmail(data.email)) {
    toast.error('Invalid email format');
    return false;
  }

  return true;
};
```

### 4. Conditional Rendering

```jsx
// Good
{
  isLoading && <LoadingSpinner />;
}
{
  !isLoading && data && <DataList items={data} />;
}
{
  !isLoading && !data && <EmptyState />;
}

// Avoid
{
  isLoading ? <LoadingSpinner /> : data ? <DataList /> : <EmptyState />;
}
```

### 5. Key in Lists

```jsx
// Good
{
  items.map((item) => <div key={item.id}>{item.name}</div>);
}

// Avoid
{
  items.map((item, index) => <div key={index}>{item.name}</div>);
}
```

---

## 🐛 Debugging

### 1. Browser DevTools

```javascript
// View Zustand store
localStorage.getItem('auth-store');

// Log API calls
console.log('API Request:', config);
```

### 2. Network Tab

- Kiểm tra API requests/responses
- Kiểm tra status codes
- Kiểm tra headers (Authorization token)

### 3. Console Logging

```javascript
// Add debug flag in .env.local
if (import.meta.env.VITE_ENABLE_DEBUG) {
  console.log('Debug Info:', data);
}
```

### 4. React DevTools Extension

- Install React DevTools browser extension
- Inspect component hierarchy
- View props and state

---

## ⚡ Performance Tips

### 1. Lazy Loading

```jsx
import { lazy, Suspense } from 'react';

const QuizzesPage = lazy(() => import('./pages/QuizzesPage'));

// In Routes:
<Suspense fallback={<LoadingSpinner />}>
  <QuizzesPage />
</Suspense>;
```

### 2. Memoization

```jsx
import { memo } from 'react';

export const TagCard = memo(({ tag, onEdit, onDelete }) => {
  return <div className="card">{/* Component content */}</div>;
});
```

### 3. useCallback

```jsx
const handleDelete = useCallback((id) => {
  deleteItem(id);
}, []);
```

### 4. Virtual Lists (for large lists)

```jsx
// Install: npm install react-window
import { FixedSizeList } from 'react-window';
```

---

## 📦 Dependencies Management

### Add New Package

```bash
npm install package-name
```

### Update Package

```bash
npm update package-name
```

### Remove Package

```bash
npm uninstall package-name
```

### Check Outdated

```bash
npm outdated
```

---

## 🚀 Build & Deployment

### Development Build

```bash
npm run dev
```

### Production Build

```bash
npm run build
```

### Preview Build

```bash
npm run preview
```

### Environment Variables for Production

Create `.env.production`:

```
VITE_API_BASE_URL=https://api.example.com/api
VITE_APP_NAME=Nihongo LMS
VITE_ENABLE_DEBUG=false
```

---

## 📖 Resources

- [React Documentation](https://react.dev)
- [React Router Docs](https://reactrouter.com)
- [Axios Docs](https://axios-http.com)
- [Zustand Docs](https://github.com/pmndrs/zustand)
- [Tailwind CSS Docs](https://tailwindcss.com)
- [Vite Docs](https://vitejs.dev)

---

## 💡 Common Issues & Solutions

### Issue: CORS Error

**Solution:** Cấu hình CORS trên backend hoặc sử dụng proxy

### Issue: Token Not Persisting

**Solution:** Kiểm tra localStorage, xem Zustand persist configuration

### Issue: Slow Page Load

**Solution:** Sử dụng code splitting, lazy loading, optimize bundle

### Issue: Form Not Submitting

**Solution:** Kiểm tra validation, console errors, network requests

---

## 📞 Need Help?

- Check [API_DOCUMENTATION.md](API_DOCUMENTATION.md)
- Review existing code patterns in pages/
- Check Zustand store implementations
- Look at similar features for reference

Happy Coding! 🚀
