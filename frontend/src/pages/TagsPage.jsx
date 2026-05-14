import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import { useTagStore } from '../store/tagStore';
import tagApi from '../services/tagApi';

export const TagsPage = () => {
  const { tags, isLoading, pagination, setTags, setLoading, setPagination } =
    useTagStore();
  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [search, setSearch] = useState('');
  const [formData, setFormData] = useState({
    name: '',
    description: '',
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
      setTags(response.data.data?.data || []);
      setPagination({
        ...pagination,
        total: response.data.data?.total || 0,
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

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      if (editingId) {
        await tagApi.update(editingId, formData);
        toast.success('Cập nhật tag thành công!');
      } else {
        await tagApi.create(formData);
        toast.success('Tạo tag thành công!');
      }
      setFormData({ name: '', description: '' });
      setEditingId(null);
      setShowForm(false);
      fetchTags();
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
      description: tag.description || '',
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
      fetchTags();
    } catch (error) {
      toast.error('Không thể xóa tag');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingId(null);
    setFormData({ name: '', description: '' });
  };

  return (
    <div className="container py-8">
      <h1 className="text-3xl font-bold mb-8">Quản Lý Tags</h1>

      {/* Search and Create */}
      <div className="mb-6 flex gap-4">
        <input
          type="text"
          placeholder="Tìm kiếm tags..."
          className="form-input flex-1"
          value={search}
          onChange={handleSearch}
        />
        <button
          onClick={() => {
            setShowForm(!showForm);
            if (showForm) handleCancel();
          }}
          className="btn-primary"
        >
          {showForm ? 'Hủy' : 'Tạo Tag Mới'}
        </button>
      </div>

      {/* Form */}
      {showForm && (
        <div className="card mb-8">
          <h2 className="text-xl font-bold mb-6">
            {editingId ? 'Chỉnh Sửa Tag' : 'Tạo Tag Mới'}
          </h2>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="name" className="form-label">
                Tên Tag
              </label>
              <input
                id="name"
                type="text"
                name="name"
                className="form-input"
                value={formData.name}
                onChange={handleFormChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="description" className="form-label">
                Mô Tả
              </label>
              <textarea
                id="description"
                name="description"
                className="form-input"
                rows="4"
                value={formData.description}
                onChange={handleFormChange}
              />
            </div>

            <div className="flex gap-4">
              <button
                type="submit"
                className="btn-primary"
                disabled={isLoading}
              >
                {isLoading ? 'Đang xử lý...' : editingId ? 'Cập Nhật' : 'Tạo'}
              </button>
              <button
                type="button"
                onClick={handleCancel}
                className="btn-secondary"
              >
                Hủy
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Tags List */}
      {isLoading && <p className="text-center text-gray-600">Đang tải...</p>}

      {!isLoading && tags.length === 0 && (
        <p className="text-center text-gray-600">Không có tags nào</p>
      )}

      {!isLoading && tags.length > 0 && (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {tags.map((tag) => (
            <div key={tag.id} className="card">
              <h3 className="text-lg font-bold mb-2">{tag.name}</h3>
              <p className="text-gray-600 mb-4">
                {tag.description || 'Không có mô tả'}
              </p>
              <div className="flex gap-2">
                <button
                  onClick={() => handleEdit(tag)}
                  className="btn btn-primary text-sm"
                >
                  Sửa
                </button>
                <button
                  onClick={() => handleDelete(tag.id)}
                  className="btn btn-danger text-sm"
                >
                  Xóa
                </button>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Pagination */}
      {pagination.total > pagination.size && (
        <div className="mt-8 flex justify-center gap-2">
          <button
            onClick={() =>
              setPagination({
                ...pagination,
                page: Math.max(1, pagination.page - 1),
              })
            }
            disabled={pagination.page === 1}
            className="btn-secondary"
          >
            Trước
          </button>
          <span className="px-4 py-2">
            Trang {pagination.page} /{' '}
            {Math.ceil(pagination.total / pagination.size)}
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
            className="btn-secondary"
          >
            Sau
          </button>
        </div>
      )}
    </div>
  );
};

export default TagsPage;
