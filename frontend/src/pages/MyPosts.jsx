import { useEffect, useState } from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';
import ItemCard from '../components/ItemCard';
import { getMyPosts, updateItemStatus } from '../services/itemService';

export default function MyPosts() {
  const [items, setItems] = useState([]);
  const load = () => getMyPosts().then(res => setItems(res.data));
  useEffect(() => { load(); }, []);
  const statusChange = async (id, status) => { await updateItemStatus(id, status); load(); };
  return <Container className="py-4"><h2>My Posts</h2><Row className="g-4">{items.map(item => <Col md={4} key={item.id}><ItemCard item={item} /><div className="mt-2 d-flex gap-2"><Form.Select size="sm" value={item.status} onChange={e=>statusChange(item.id,e.target.value)}><option value="ACTIVE">ACTIVE</option><option value="CLAIMED">CLAIMED</option><option value="RETURNED">RETURNED</option><option value="CLOSED">CLOSED</option></Form.Select></div></Col>)}</Row>{items.length===0 && <p className="text-muted">No posts yet.</p>}</Container>;
}
