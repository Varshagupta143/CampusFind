import { useEffect, useState } from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';
import ItemCard from '../components/ItemCard';
import { getItems } from '../services/itemService';

export default function AllItems() {
  const [items, setItems] = useState([]);
  const [filters, setFilters] = useState({ type:'', category:'', location:'', search:'' });
  const load = () => getItems(Object.fromEntries(Object.entries(filters).filter(([_,v])=>v))).then(res=>setItems(res.data));
  useEffect(() => { load(); }, []);
  const change = e => setFilters({ ...filters, [e.target.name]: e.target.value });

  return <Container className="py-4">
    <h2 className="mb-3">All Lost & Found Items</h2>
    <Row className="g-2 mb-4">
      <Col md><Form.Control name="search" placeholder="Search item" value={filters.search} onChange={change} /></Col>
      <Col md><Form.Select name="type" value={filters.type} onChange={change}><option value="">All Types</option><option value="LOST">Lost</option><option value="FOUND">Found</option></Form.Select></Col>
      <Col md><Form.Control name="category" placeholder="Category" value={filters.category} onChange={change} /></Col>
      <Col md><Form.Control name="location" placeholder="Location" value={filters.location} onChange={change} /></Col>
      <Col md="auto"><Button onClick={load}>Filter</Button></Col>
    </Row>
    <Row className="g-4">{items.map(item => <Col md={4} key={item.id}><ItemCard item={item} /></Col>)}</Row>
    {items.length === 0 && <p className="text-muted">No items found.</p>}
  </Container>;
}
