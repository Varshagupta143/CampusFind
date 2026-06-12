import { useEffect, useState } from 'react';
import { Alert, Badge, Button, Card, Col, Container, Row } from 'react-bootstrap';
import { approveClaim, myClaims, receivedClaims, rejectClaim } from '../services/claimService';

export default function Claims() {
  const [mine, setMine] = useState([]);
  const [received, setReceived] = useState([]);
  const [msg, setMsg] = useState('');
  const load = () => { myClaims().then(r=>setMine(r.data)); receivedClaims().then(r=>setReceived(r.data)); };
  useEffect(() => { load(); }, []);
  const action = async (fn, id, success) => {
    try { await fn(id); setMsg(success); load(); }
    catch (err) { setMsg(err.response?.data?.message || 'Action failed'); }
  };

  const ClaimCard = ({ c, receivedMode }) => <Card className="stat-card mb-3"><Card.Body>
    <div className="d-flex justify-content-between"><h5>{c.itemName}</h5><Badge bg={c.status==='APPROVED'?'success':c.status==='REJECTED'?'danger':'warning'}>{c.status}</Badge></div>
    <p className="mb-1"><b>Claimer:</b> {c.claimerName}</p>
    <p className="mb-1"><b>Message:</b> {c.message}</p>
    <p className="mb-2"><b>Proof:</b> {c.proof || 'Not provided'}</p>
    {!receivedMode && c.status === 'APPROVED' && <Alert variant="success" className="py-2"><b>Approved Contact:</b> {c.approvedContactInfo || 'Contact not provided by owner'}</Alert>}
    {receivedMode && c.status==='PENDING' && <><Button size="sm" className="me-2" onClick={()=>action(approveClaim,c.id,'Claim approved')}>Approve</Button><Button size="sm" variant="outline-danger" onClick={()=>action(rejectClaim,c.id,'Claim rejected')}>Reject</Button></>}
  </Card.Body></Card>;

  return <Container className="py-4"><h2>Claims</h2>{msg && <Alert variant={msg.includes('failed') || msg.includes('only') ? 'danger' : 'success'}>{msg}</Alert>}<Row><Col md={6}><h4>Claims Received</h4>{received.map(c=><ClaimCard key={c.id} c={c} receivedMode />)}{received.length===0 && <p className="text-muted">No received claims.</p>}</Col><Col md={6}><h4>My Claims</h4>{mine.map(c=><ClaimCard key={c.id} c={c} />)}{mine.length===0 && <p className="text-muted">No claims sent.</p>}</Col></Row></Container>;
}
