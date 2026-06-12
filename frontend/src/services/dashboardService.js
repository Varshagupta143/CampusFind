import api from './api';
export const dashboardSummary = () => api.get('/dashboard/summary');
