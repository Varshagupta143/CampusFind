import { Link, NavLink, useNavigate } from 'react-router-dom';
import { Container, Nav, Navbar as BsNavbar, Button, Badge } from 'react-bootstrap';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const handleLogout = () => { logout(); navigate('/login'); };

  return (
    <BsNavbar bg="dark" data-bs-theme="dark" expand="lg" sticky="top">
      <Container>
        <BsNavbar.Brand as={Link} to="/">CampusFind</BsNavbar.Brand>
        <BsNavbar.Toggle />
        <BsNavbar.Collapse>
          {user && (
            <Nav className="me-auto">
              <Nav.Link as={NavLink} to="/dashboard">Dashboard</Nav.Link>
              <Nav.Link as={NavLink} to="/items">All Items</Nav.Link>
              <Nav.Link as={NavLink} to="/post-item">Post Item</Nav.Link>
              <Nav.Link as={NavLink} to="/my-posts">My Posts</Nav.Link>
              <Nav.Link as={NavLink} to="/claims">Claims</Nav.Link>
              <Nav.Link as={NavLink} to="/notifications">Notifications</Nav.Link>
              {user.role === 'ADMIN' && <Nav.Link as={NavLink} to="/admin">Admin</Nav.Link>}
            </Nav>
          )}
          <Nav className="ms-auto align-items-lg-center">
            {user ? (
              <>
                <span className="navbar-text me-3">Hi, {user.name} {user.role === 'ADMIN' && <Badge bg="warning" text="dark">ADMIN</Badge>}</span>
                <Button size="sm" variant="outline-light" onClick={handleLogout}>Logout</Button>
              </>
            ) : (
              <>
                <Nav.Link as={NavLink} to="/login">Login</Nav.Link>
                <Nav.Link as={NavLink} to="/register">Register</Nav.Link>
              </>
            )}
          </Nav>
        </BsNavbar.Collapse>
      </Container>
    </BsNavbar>
  );
}
