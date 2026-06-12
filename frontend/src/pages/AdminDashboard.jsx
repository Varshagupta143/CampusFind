import { useEffect, useState } from 'react';
import { Alert, Badge, Button, Card, Col, Container, Row, Table } from 'react-bootstrap';
import { approveItem, getPendingItems, getUsers, rejectItem } from '../services/adminService';

export default function AdminDashboard() {
  const [items, setItems] = useState([]);
  const [users, setUsers] = useState([]);
  const [msg, setMsg] = useState('');

  const load = () => {
    getPendingItems().then(res => setItems(res.data));
    getUsers().then(res => setUsers(res.data));
  };

  useEffect(() => { load(); }, []);

  const action = async (fn, id, successMessage) => {
    try {
      await fn(id);
      setMsg(successMessage);
      load();
    } catch (err) {
      setMsg(err.response?.data?.message || 'Action failed');
    }
  };

  return <Container className="py-4">
    <h2 className="mb-3">Admin Dashboard</h2>
    {msg && <Alert variant={msg.includes('failed') ? 'danger' : 'success'}>{msg}</Alert>}

    <Row className="g-3 mb-4">
      <Col md={4}><Card className="stat-card"><Card.Body><h6>Pending Posts</h6><h2>{items.length}</h2></Card.Body></Card></Col>
      <Col md={4}><Card className="stat-card"><Card.Body><h6>Total Users</h6><h2>{users.length}</h2></Card.Body></Card></Col>
      <Col md={4}><Card className="stat-card"><Card.Body><h6>Admin Mode</h6><Badge bg="success">ACTIVE</Badge></Card.Body></Card></Col>
    </Row>

    <Card className="stat-card mb-4"><Card.Body>
      <h4>Pending Item Approvals</h4>
      {items.length === 0 ? <p className="text-muted mb-0">No pending posts.</p> : <div className="table-responsive"><Table hover>
        <thead><tr><th>Item</th><th>Type</th><th>Category</th><th>Location</th><th>Posted By</th><th>Action</th></tr></thead>
        <tbody>{items.map(item => <tr key={item.id}>
          <td>{item.itemName}</td>
          <td><Badge bg={item.type === 'LOST' ? 'danger' : 'success'}>{item.type}</Badge></td>
          <td>{item.category}</td>
          <td>{item.location}</td>
          <td>{item.postedByName}</td>
          <td>
            <Button size="sm" className="me-2" onClick={() => action(approveItem, item.id, 'Post approved')}>Approve</Button>
            <Button size="sm" variant="outline-danger" onClick={() => action(rejectItem, item.id, 'Post rejected')}>Reject</Button>
          </td>
        </tr>)}</tbody>
      </Table></div>}
    </Card.Body></Card>

    <Card className="stat-card"><Card.Body>
      <h4>Users</h4>
      <div className="table-responsive"><Table hover>
        <thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Branch</th><th>Year</th></tr></thead>
        <tbody>{users.map(u => <tr key={u.id}><td>{u.name}</td><td>{u.email}</td><td><Badge bg={u.role === 'ADMIN' ? 'dark' : 'secondary'}>{u.role}</Badge></td><td>{u.branch || '-'}</td><td>{u.year || '-'}</td></tr>)}</tbody>
      </Table></div>
    </Card.Body></Card>
  </Container>;
}
