import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import { useTagStore } from '../store/tagStore';
import tagApi from '../services/tagApi';

const COLOR_PALETTE = [
  { hex: '#6366f1', name: 'Indigo' },
  { hex: '#8b5cf6', name: 'Violet' },
  { hex: '#ec4899', name: 'Pink' },
  { hex: '#ef4444', name: 'Red' },
  { hex: '#f59e0b', name: 'Amber' },
  { hex: '#10b981', name: 'Emerald' },
  { hex: '#14b8a6', name: 'Teal' },
  { hex: '#06b6d4', name: 'Cyan' },
  { hex: '#3b82f6', name: 'Blue' },
  { hex: '#6b7280', name: 'Gray' },
];

export const TagsPage = () => {
  const { tags, isLoading, pagination, setTags, setLoading, setPagination } =
    useTagStore();
  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [search, setSearch] = useState('');
  const [formData, setFormData] = useState({
    name: '',
    color: '#6366f1',
  });

  useEffect(() => {
    fetchTags();
  }, [pagination.page]);

  const fetchTags = async () => {
    setLoading(true);
    try {
      const response = await tagApi.getTags(
        search,
        pagination.page,
        pagination.size
      );
      // Backend returns ApiResponse<PageResponse> → { data: { items, page, size, total } }
      const pageData = response.data.data;
      setTags(pageData?.items || []);
      setPagination({
        ...pagination,
        total: pageData?.total || 0,
      });
    } catch (error) {
      toast.error('Không thể tải tags');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (e) => {
    setSearch(e.target.value);
    setPagination({ ...pagination, page: 1 });
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    fetchTags();
  };

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      // Send { name, color } matching backend TagRequest
      const payload = { name: formData.name, color: formData.color };

      if (editingId) {
        const res = await tagApi.update(editingId, payload);
        const updated = res?.data?.data ?? res?.data ?? null;
        if (updated) {
          const newList = tags.map((t) => (t.id === editingId ? updated : t));
          setTags(newList);
        }
        toast.success('Cập nhật tag thành công!');
      } else {
        const res = await tagApi.create(payload);
        const created = res?.data?.data ?? res?.data ?? null;
        if (created) {
          setTags([created, ...tags]);
        }
        toast.success('Tạo tag thành công!');
      }

      // Refresh from server
      try {
        await fetchTags();
      } catch (e) {
        // ignore - fetchTags already handles errors
      }
      setFormData({ name: '', color: '#6366f1' });
      setEditingId(null);
      setShowForm(false);
    } catch (error) {
      const message = error.response?.data?.message || 'Thao tác thất bại';
      toast.error(message);
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (tag) => {
    setFormData({
      name: tag.name,
      color: tag.color || '#6366f1',
    });
    setEditingId(tag.id);
    setShowForm(true);
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Bạn chắc chắn muốn xóa tag này?')) return;

    setLoading(true);
    try {
      await tagApi.delete(id);
      toast.success('Xóa tag thành công!');

      try {
        await fetchTags();
      } catch (e) {}
    } catch (error) {
      toast.error('Không thể xóa tag');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingId(null);
    setFormData({ name: '', color: '#6366f1' });
  };

  return (
    <div className="p-6 lg:p-8 max-w-6xl mx-auto">
      {/* Header */}
      <div className="flex items-center justify-between mb-8 animate-fade-in-up">
        <div>
          <h1 className="text-2xl lg:text-3xl font-bold" style={{ color: 'var(--text-primary)' }}>
            Quản Lý Tags
          </h1>
          <p className="text-sm mt-1" style={{ color: 'var(--text-muted)' }}>
            Tạo và quản lý tags cho từ vựng của bạn
          </p>
        </div>
        <button
          onClick={() => {
            if (showForm) handleCancel();
            else setShowForm(true);
          }}
          className={showForm ? 'btn-secondary' : 'btn-primary'}
        >
          {showForm ? (
            <>✕ Đóng</>
          ) : (
            <span className="flex items-center gap-2">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5">
                <line x1="12" y1="5" x2="12" y2="19" />
                <line x1="5" y1="12" x2="19" y2="12" />
              </svg>
              Tạo Tag
            </span>
          )}
        </button>
      </div>

      {/* Search */}
      <form onSubmit={handleSearchSubmit} className="mb-6 animate-fade-in-up stagger-1">
        <div className="flex gap-3">
          <div className="relative flex-1">
            <svg
              className="absolute left-3.5 top-1/2 -translate-y-1/2"
              width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="var(--text-muted)" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"
            >
              <circle cx="11" cy="11" r="8" />
              <line x1="21" y1="21" x2="16.65" y2="16.65" />
            </svg>
            <input
              type="text"
              placeholder="Tìm kiếm tags..."
              className="form-input pl-11"
              value={search}
              onChange={handleSearch}
            />
          </div>
          <button type="submit" className="btn-secondary">
            Tìm
          </button>
        </div>
      </form>

      {/* Create/Edit Form */}
      {showForm && (
        <div className="card mb-8 animate-fade-in-up" style={{ borderColor: 'rgba(99, 102, 241, 0.2)' }}>
          <h2 className="text-lg font-bold mb-5" style={{ color: 'var(--text-primary)' }}>
            {editingId ? '✏️ Chỉnh Sửa Tag' : '✨ Tạo Tag Mới'}
          </h2>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="tag-name" className="form-label">
                Tên Tag
              </label>
              <input
                id="tag-name"
                type="text"
                name="name"
                className="form-input"
                placeholder="VD: JLPT_N2, IT_Vocabulary..."
                value={formData.name}
                onChange={handleFormChange}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">Chọn Màu</label>
              <div className="flex items-center gap-2 flex-wrap">
                {COLOR_PALETTE.map((c) => (
                  <button
                    type="button"
                    key={c.hex}
                    onClick={() => setFormData((p) => ({ ...p, color: c.hex }))}
                    className="w-10 h-10 rounded-xl transition-all duration-200 relative group"
                    style={{
                      backgroundColor: c.hex,
                      boxShadow:
                        formData.color === c.hex
                          ? `0 0 0 2px var(--bg-secondary), 0 0 0 4px ${c.hex}, 0 4px 12px ${c.hex}50`
                          : 'none',
                      transform: formData.color === c.hex ? 'scale(1.1)' : 'scale(1)',
                    }}
                    aria-label={`Chọn màu ${c.name}`}
                    title={c.name}
                  >
                    {formData.color === c.hex && (
                      <svg className="absolute inset-0 m-auto" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="white" strokeWidth="3">
                        <polyline points="20 6 9 17 4 12" />
                      </svg>
                    )}
                  </button>
                ))}
              </div>
              <p className="text-xs mt-2" style={{ color: 'var(--text-muted)' }}>
                Đã chọn: <span style={{ color: formData.color }}>{formData.color}</span>
              </p>
            </div>

            <div className="flex gap-3 pt-2">
              <button type="submit" className="btn-primary" disabled={isLoading}>
                {isLoading ? 'Đang xử lý...' : editingId ? 'Cập Nhật' : 'Tạo Tag'}
              </button>
              <button type="button" onClick={handleCancel} className="btn-secondary">
                Hủy
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Tags List */}
      {isLoading && (
        <div className="text-center py-12">
          <div className="inline-flex items-center gap-3" style={{ color: 'var(--text-muted)' }}>
            <svg className="animate-spin h-5 w-5" viewBox="0 0 24 24">
              <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
              <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
            </svg>
            Đang tải...
          </div>
        </div>
      )}

      {!isLoading && tags.length === 0 && (
        <div className="text-center py-16 animate-fade-in">
          <div className="text-5xl mb-4">🏷️</div>
          <p className="text-lg font-medium" style={{ color: 'var(--text-secondary)' }}>
            Chưa có tag nào
          </p>
          <p className="text-sm mt-1" style={{ color: 'var(--text-muted)' }}>
            Tạo tag đầu tiên để bắt đầu phân loại từ vựng
          </p>
        </div>
      )}

      {!isLoading && tags.length > 0 && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {tags.map((tag, index) => {
            const tagColor = tag.color || '#6b7280';

            return (
              <div
                key={tag.id}
                className="card card-interactive animate-fade-in-up"
                style={{ animationDelay: `${index * 50}ms` }}
              >
                <div className="flex items-start justify-between mb-3">
                  <div className="flex items-center gap-3">
                    <div
                      className="w-4 h-4 rounded-full shrink-0"
                      style={{
                        backgroundColor: tagColor,
                        boxShadow: `0 0 8px ${tagColor}40`,
                      }}
                    />
                    <h3 className="font-semibold text-base" style={{ color: 'var(--text-primary)' }}>
                      {tag.name}
                    </h3>
                  </div>
                </div>

                <div className="flex items-center justify-between">
                  <span
                    className="text-xs px-2.5 py-1 rounded-lg font-mono"
                    style={{
                      background: `${tagColor}15`,
                      color: tagColor,
                      border: `1px solid ${tagColor}30`,
                    }}
                  >
                    {tagColor}
                  </span>

                  <div className="flex gap-1.5">
                    <button
                      onClick={() => handleEdit(tag)}
                      className="p-2 rounded-lg transition-all duration-200"
                      style={{ color: 'var(--accent-indigo)' }}
                      onMouseEnter={(e) => {
                        e.currentTarget.style.background = 'rgba(99, 102, 241, 0.1)';
                      }}
                      onMouseLeave={(e) => {
                        e.currentTarget.style.background = 'transparent';
                      }}
                      title="Chỉnh sửa"
                    >
                      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <path d="M11 4H4a2 2 0 00-2 2v14a2 2 0 002 2h14a2 2 0 002-2v-7" />
                        <path d="M18.5 2.5a2.121 2.121 0 013 3L12 15l-4 1 1-4 9.5-9.5z" />
                      </svg>
                    </button>
                    <button
                      onClick={() => handleDelete(tag.id)}
                      className="p-2 rounded-lg transition-all duration-200"
                      style={{ color: 'var(--accent-red)' }}
                      onMouseEnter={(e) => {
                        e.currentTarget.style.background = 'rgba(239, 68, 68, 0.1)';
                      }}
                      onMouseLeave={(e) => {
                        e.currentTarget.style.background = 'transparent';
                      }}
                      title="Xóa"
                    >
                      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <polyline points="3 6 5 6 21 6" />
                        <path d="M19 6v14a2 2 0 01-2 2H7a2 2 0 01-2-2V6m3 0V4a2 2 0 012-2h4a2 2 0 012 2v2" />
                      </svg>
                    </button>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}

      {/* Pagination */}
      {pagination.total > pagination.size && (
        <div className="mt-8 flex justify-center items-center gap-3 animate-fade-in">
          <button
            onClick={() =>
              setPagination({
                ...pagination,
                page: Math.max(1, pagination.page - 1),
              })
            }
            disabled={pagination.page === 1}
            className="btn-secondary disabled:opacity-30"
          >
            ← Trước
          </button>
          <span
            className="px-4 py-2 text-sm font-medium rounded-xl"
            style={{ background: 'var(--bg-glass)', color: 'var(--text-secondary)' }}
          >
            {pagination.page} / {Math.ceil(pagination.total / pagination.size)}
          </span>
          <button
            onClick={() =>
              setPagination({
                ...pagination,
                page: pagination.page + 1,
              })
            }
            disabled={
              pagination.page >= Math.ceil(pagination.total / pagination.size)
            }
            className="btn-secondary disabled:opacity-30"
          >
            Sau →
          </button>
        </div>
      )}
    </div>
  );
};

export default TagsPage;
