import { useEffect, useState } from 'react';
import { Alert, Badge, Button, Card, Col, Container, Form, Image, Row } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import { getItem } from '../services/itemService';
import { createClaim } from '../services/claimService';
import { useAuth } from '../context/AuthContext';

const fallback = 'https://placehold.co/800x500?text=CampusFind';
const backendUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';
const getImageSrc = (imageUrl) => {
  if (!imageUrl) return fallback;
  if (imageUrl.startsWith('http')) return imageUrl;
  return `${backendUrl}${imageUrl}`;
};

export default function ItemDetails() {
  const { id } = useParams();
  const { user } = useAuth();
  const [item, setItem] = useState(null);
  const [claim, setClaim] = useState({ itemId:id, message:'', proof:'' });
  const [msg, setMsg] = useState('');
  useEffect(() => { getItem(id).then(res => setItem(res.data)); }, [id]);
  const submitClaim = async e => {
    e.preventDefault();
    try { await createClaim(claim); setMsg('Claim request sent successfully.'); setClaim({...claim,message:'',proof:''}); }
    catch(err) { setMsg(err.response?.data?.message || 'Claim failed'); }
  };
  if (!item) return <Container className="py-4">Loading...</Container>;

  const isOwner = item.userId === user?.id;
  const canClaim = !isOwner && item.approvalStatus === 'APPROVED' && item.status === 'ACTIVE';

  return <Container className="py-4"><Row className="g-4"><Col md={7}><Image src={getImageSrc(item.imageUrl)} fluid rounded /></Col><Col md={5}><Card className="stat-card"><Card.Body>
    <div className="mb-2"><Badge bg={item.type === 'LOST' ? 'danger' : 'success'}>{item.type}</Badge> <Badge bg="secondary">{item.status}</Badge> <Badge bg={item.approvalStatus === 'APPROVED' ? 'success' : item.approvalStatus === 'REJECTED' ? 'danger' : 'warning'}>{item.approvalStatus}</Badge></div>
    <h2>{item.itemName}</h2>
    <p className="text-muted">{item.category} • {item.location} • {item.itemDate}</p>
    <p>{item.description}</p>
    <p><b>Posted by:</b> {item.postedByName}</p>
    <p><b>Submitted to:</b> {item.submittedTo || 'Not mentioned'}</p>
    <p><b>Contact:</b> {item.contactVisible ? (item.contactInfo || 'Not mentioned') : 'Hidden until claim is approved'}</p>
    {item.approvalStatus === 'PENDING' && <Alert variant="warning" className="mb-0">This post is waiting for admin approval.</Alert>}
  </Card.Body></Card></Col></Row>
  {canClaim && <Card className="stat-card mt-4"><Card.Body><h4>Send Claim Request</h4>{msg && <Alert variant={msg.includes('success') ? 'success' : 'warning'}>{msg}</Alert>}<Form onSubmit={submitClaim}><Form.Group className="mb-3"><Form.Label>Why is this yours?</Form.Label><Form.Control as="textarea" rows={2} value={claim.message} onChange={e=>setClaim({...claim,message:e.target.value})} required /></Form.Group><Form.Group className="mb-3"><Form.Label>Proof / Extra detail</Form.Label><Form.Control value={claim.proof} onChange={e=>setClaim({...claim,proof:e.target.value})} placeholder="Example: initials, color, special mark, exact place" /></Form.Group><Button type="submit">Send Claim</Button></Form></Card.Body></Card>}
  </Container>;
}
