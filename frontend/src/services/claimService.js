import api from './api';

export const createClaim = (data) => api.post('/claims', data);
export const myClaims = () => api.get('/claims/my-claims');
export const receivedClaims = () => api.get('/claims/received');
export const approveClaim = (id) => api.put(`/claims/${id}/approve`);
export const rejectClaim = (id) => api.put(`/claims/${id}/reject`);
