import api from './api';

export const getPendingItems = () => api.get('/admin/items/pending');
export const approveItem = (id) => api.put(`/admin/items/${id}/approve`);
export const rejectItem = (id) => api.put(`/admin/items/${id}/reject`);
export const getUsers = () => api.get('/admin/users');
