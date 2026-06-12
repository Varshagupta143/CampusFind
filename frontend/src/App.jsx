import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Navbar from './components/Navbar';
import ProtectedRoute from './components/ProtectedRoute';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import AllItems from './pages/AllItems';
import PostItem from './pages/PostItem';
import ItemDetails from './pages/ItemDetails';
import MyPosts from './pages/MyPosts';
import Claims from './pages/Claims';
import Notifications from './pages/Notifications';
import AdminDashboard from './pages/AdminDashboard';

function Private({ children, role }) { return <ProtectedRoute role={role}>{children}</ProtectedRoute>; }

export default function App() {
  return <AuthProvider><BrowserRouter><Navbar /><div className="page-wrap"><Routes>
    <Route path="/" element={<Home />} />
    <Route path="/login" element={<Login />} />
    <Route path="/register" element={<Register />} />
    <Route path="/dashboard" element={<Private><Dashboard /></Private>} />
    <Route path="/items" element={<Private><AllItems /></Private>} />
    <Route path="/items/:id" element={<Private><ItemDetails /></Private>} />
    <Route path="/post-item" element={<Private><PostItem /></Private>} />
    <Route path="/my-posts" element={<Private><MyPosts /></Private>} />
    <Route path="/claims" element={<Private><Claims /></Private>} />
    <Route path="/notifications" element={<Private><Notifications /></Private>} />
    <Route path="/admin" element={<Private role="ADMIN"><AdminDashboard /></Private>} />
    <Route path="*" element={<Navigate to="/" />} />
  </Routes></div></BrowserRouter></AuthProvider>;
}
