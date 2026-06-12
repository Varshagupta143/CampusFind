import { useEffect, useState } from 'react';
import { Alert, Badge, Button, Card, Col, Container, Row } from 'react-bootstrap';
import { approveClaim, myClaims, receivedClaims, rejectClaim } from '../services/claimService';

export default function Claims() {
  const [mine, setMine] = useState([]);
  const [received, setReceived] = useState([]);
  const [msg, setMsg] = useState('');

  const load = () => {
    myClaims().then((r) => setMine(r.data));
    receivedClaims().then((r) => setReceived(r.data));
  };

  useEffect(() => {
    load();
  }, []);

  const action = async (fn, id, success) => {
    try {
      await fn(id);
      setMsg(success);
      load();
    } catch (err) {
      setMsg(err.response?.data?.message || 'Action failed');
    }
  };

  const getBadgeColor = (status) => {
    if (status === 'APPROVED') return 'success';
    if (status === 'REJECTED') return 'danger';
    return 'warning';
  };

  const ClaimCard = ({ c, receivedMode }) => (
    <Card className="stat-card mb-3">
      <Card.Body>
        <div className="d-flex justify-content-between align-items-start">
          <h5>{c.itemName}</h5>
          <Badge bg={getBadgeColor(c.status)}>{c.status}</Badge>
        </div>

        <p className="mb-1">
          <b>Claimer:</b> {c.claimerName}
        </p>

        <p className="mb-1">
          <b>Message:</b> {c.message}
        </p>

        <p className="mb-2">
          <b>Proof:</b> {c.proof || 'Not provided'}
        </p>

        {/* This section is shown to item owner/admin in Claims Received */}
        {receivedMode && c.status === 'APPROVED' && (
          <Alert variant="success" className="mt-3 mb-2">
            <h6 className="fw-bold mb-2">Claimer Contact Details</h6>

            {c.claimerEmail && (
              <p className="mb-1">
                <b>Email:</b> {c.claimerEmail}
              </p>
            )}

            {c.claimerPhone && (
              <p className="mb-1">
                <b>Phone:</b> {c.claimerPhone}
              </p>
            )}

            {!c.claimerEmail && !c.claimerPhone && (
              <p className="mb-0">Contact details not available.</p>
            )}
          </Alert>
        )}

        {/* This section is shown to claimer in My Claims */}
        {!receivedMode && c.status === 'APPROVED' && (
          <Alert variant="success" className="mt-3 mb-2">
            <h6 className="fw-bold mb-2">Owner Contact Details</h6>

            {c.ownerName && (
              <p className="mb-1">
                <b>Name:</b> {c.ownerName}
              </p>
            )}

            {c.ownerEmail && (
              <p className="mb-1">
                <b>Email:</b> {c.ownerEmail}
              </p>
            )}

            {c.ownerPhone && (
              <p className="mb-1">
                <b>Phone:</b> {c.ownerPhone}
              </p>
            )}

            {c.approvedContactInfo && (
              <p className="mb-1">
                <b>Extra Contact Info:</b> {c.approvedContactInfo}
              </p>
            )}

            {!c.ownerEmail && !c.ownerPhone && !c.approvedContactInfo && (
              <p className="mb-0">Contact details not provided by owner.</p>
            )}
          </Alert>
        )}

        {receivedMode && c.status === 'PENDING' && (
          <>
            <Button
              size="sm"
              className="me-2"
              onClick={() => action(approveClaim, c.id, 'Claim approved')}
            >
              Approve
            </Button>

            <Button
              size="sm"
              variant="outline-danger"
              onClick={() => action(rejectClaim, c.id, 'Claim rejected')}
            >
              Reject
            </Button>
          </>
        )}
      </Card.Body>
    </Card>
  );

  return (
    <Container className="py-4">
      <h2>Claims</h2>

      {msg && (
        <Alert variant={msg.includes('failed') || msg.includes('only') ? 'danger' : 'success'}>
          {msg}
        </Alert>
      )}

      <Row>
        <Col md={6}>
          <h4>Claims Received</h4>

          {received.map((c) => (
            <ClaimCard key={c.id} c={c} receivedMode />
          ))}

          {received.length === 0 && (
            <p className="text-muted">No received claims.</p>
          )}
        </Col>

        <Col md={6}>
          <h4>My Claims</h4>

          {mine.map((c) => (
            <ClaimCard key={c.id} c={c} />
          ))}

          {mine.length === 0 && (
            <p className="text-muted">No claims sent.</p>
          )}
        </Col>
      </Row>
    </Container>
  );
}