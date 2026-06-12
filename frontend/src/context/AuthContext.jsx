import { createContext, useContext, useEffect, useState } from 'react';
import { logout as logoutApi, profile } from '../services/authService';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() =>
    JSON.parse(localStorage.getItem('user') || 'null')
  );

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // With HttpOnly cookie, we cannot read token from JS.
    // So we directly call profile. Browser will send cookie automatically.
    profile()
      .then((res) => {
        setUser(res.data);
        localStorage.setItem('user', JSON.stringify(res.data));
      })
      .catch(() => {
        localStorage.removeItem('user');
        setUser(null);
      })
      .finally(() => setLoading(false));
  }, []);

  const saveAuth = (auth) => {
    // Token is now inside HttpOnly cookie, so do not store token in localStorage.
    localStorage.setItem('user', JSON.stringify(auth.user));
    setUser(auth.user);
  };

  const logout = async () => {
    try {
      // This clears HttpOnly cookie from backend
      await logoutApi();
    } catch (error) {
      console.log('Logout failed:', error);
    }

    localStorage.removeItem('user');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, loading, saveAuth, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);