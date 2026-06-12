import { useEffect, useState } from 'react';
import { Card, Container } from 'react-bootstrap';
import { notifications } from '../services/notificationService';

export default function Notifications() {
  const [list, setList] = useState([]);
  useEffect(() => { notifications().then(res=>setList(res.data)); }, []);
  return <Container className="py-4"><h2>Notifications</h2>{list.map(n=><Card className="stat-card mb-3" key={n.id}><Card.Body><p className="mb-1">{n.message}</p><small className="text-muted">{new Date(n.createdAt).toLocaleString()}</small></Card.Body></Card>)}{list.length===0 && <p className="text-muted">No notifications.</p>}</Container>;
}
