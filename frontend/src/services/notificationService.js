import api from './api';
export const notifications = () => api.get('/notifications');
