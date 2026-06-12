import { Button, Card, Col, Container, Row } from 'react-bootstrap';
import { Link } from 'react-router-dom';

export default function Home() {
  return (
    <Container className="py-5">
      <Card className="hero-card p-4 p-md-5 border-0">
        <Row className="align-items-center">
          <Col md={8}>
            <h1 className="display-5 fw-bold">College Lost and Found Portal</h1>
            <p className="lead">Report lost items, post found items, search by location/category, and manage claim requests securely.</p>
            <Button as={Link} to="/register" variant="light" className="me-2">Get Started</Button>
            <Button as={Link} to="/login" variant="outline-light">Login</Button>
          </Col>
        </Row>
      </Card>
    </Container>
  );
}
