import { useState } from 'react';
import { Alert, Button, Card, Col, Container, Form, Row } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { register } from '../services/authService';
import { useAuth } from '../context/AuthContext';

export default function Register() {
  const [form, setForm] = useState({
    name: '',
    email: '',
    password: '',
    rollNumber: '',
    branch: '',
    year: '',
    phone: ''
  });

  const [error, setError] = useState('');
  const { saveAuth } = useAuth();
  const navigate = useNavigate();

  const change = (e) => {
    setForm({ ...form, [e.target.dataset.field]: e.target.value });
  };

  const submit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const res = await register(form);
      saveAuth(res.data);
      navigate('/dashboard');
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed');
    }
  };

  return (
    <Container>
      <Card className="auth-card">
        <Card.Body className="p-4">
          <h3 className="mb-3">Create Account</h3>

          {error && <Alert variant="danger">{error}</Alert>}

          <Form onSubmit={submit} autoComplete="off">
            <Form.Group className="mb-3">
              <Form.Label>Name</Form.Label>
              <Form.Control
                name="register-name"
                data-field="name"
                value={form.name}
                onChange={change}
                autoComplete="off"
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                name="register-email-new"
                data-field="email"
                value={form.email}
                onChange={change}
                autoComplete="new-email"
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                name="register-password-new"
                data-field="password"
                value={form.password}
                onChange={change}
                autoComplete="new-password"
                required
              />
            </Form.Group>

            <Row>
              <Col>
                <Form.Group className="mb-3">
                  <Form.Label>Roll Number</Form.Label>
                  <Form.Control
                    name="register-roll-number"
                    data-field="rollNumber"
                    value={form.rollNumber}
                    onChange={change}
                    autoComplete="off"
                  />
                </Form.Group>
              </Col>

              <Col>
                <Form.Group className="mb-3">
                  <Form.Label>Branch</Form.Label>
                  <Form.Control
                    name="register-branch"
                    data-field="branch"
                    value={form.branch}
                    onChange={change}
                    autoComplete="off"
                  />
                </Form.Group>
              </Col>
            </Row>

            <Row>
              <Col>
                <Form.Group className="mb-3">
                  <Form.Label>Year</Form.Label>
                  <Form.Control
                    name="register-year"
                    data-field="year"
                    value={form.year}
                    onChange={change}
                    autoComplete="off"
                  />
                </Form.Group>
              </Col>

              <Col>
                <Form.Group className="mb-3">
                  <Form.Label>Phone</Form.Label>
                  <Form.Control
                    name="register-phone"
                    data-field="phone"
                    value={form.phone}
                    onChange={change}
                    autoComplete="off"
                  />
                </Form.Group>
              </Col>
            </Row>

            <Button type="submit" className="w-100">
              Register
            </Button>
          </Form>

          <div className="mt-3 text-center">
            Already registered? <Link to="/login">Login</Link>
          </div>
        </Card.Body>
      </Card>
    </Container>
  );
}