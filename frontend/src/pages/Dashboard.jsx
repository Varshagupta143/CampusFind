import { useEffect, useState } from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { dashboardSummary } from '../services/dashboardService';
import StatCard from '../components/StatCard';

export default function Dashboard() {
  const [data, setData] = useState({});
  useEffect(() => { dashboardSummary().then(res => setData(res.data)); }, []);
  return <Container className="py-4">
    <Card className="hero-card p-4 mb-4 border-0"><h2>Dashboard</h2><p className="mb-0">Track lost/found posts, claims, and returned items.</p></Card>
    <Row className="g-3">
      <Col md={4}><StatCard title="Total Lost Items" value={data.totalLostItems || 0} /></Col>
      <Col md={4}><StatCard title="Total Found Items" value={data.totalFoundItems || 0} /></Col>
      <Col md={4}><StatCard title="Returned Items" value={data.returnedItems || 0} /></Col>
      <Col md={4}><StatCard title="My Posts" value={data.myPosts || 0} /></Col>
      <Col md={4}><StatCard title="Pending Claims Received" value={data.pendingClaimsReceived || 0} /></Col>
      <Col md={4}><StatCard title="My Claims" value={data.myClaims || 0} /></Col>
    </Row>
    <div className="mt-4"><Button as={Link} to="/post-item" className="me-2">Post Item</Button><Button as={Link} to="/items" variant="outline-primary">Browse Items</Button></div>
  </Container>;
}
