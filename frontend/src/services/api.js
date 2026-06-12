import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api',

  // Important for HttpOnly cookie
  // This allows browser to send cookie automatically with every request
  withCredentials: true,
});

export default api;