import { Badge, Button, Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const fallback = 'https://placehold.co/600x400?text=CampusFind';
const backendUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';

function getImageSrc(imageUrl) {
  if (!imageUrl) return fallback;
  if (imageUrl.startsWith('http')) return imageUrl;
  return `${backendUrl}${imageUrl}`;
}

export default function ItemCard({ item }) {
  return (
    <Card className="item-card h-100">
      <Card.Img src={getImageSrc(item.imageUrl)} className="item-img" />
      <Card.Body>
        <div className="d-flex justify-content-between mb-2">
          <Badge bg={item.type === 'LOST' ? 'danger' : 'success'}>{item.type}</Badge>
          <div className="d-flex gap-1">
            <Badge bg="secondary">{item.status}</Badge>
            {item.approvalStatus && <Badge bg={item.approvalStatus === 'APPROVED' ? 'success' : item.approvalStatus === 'REJECTED' ? 'danger' : 'warning'}>{item.approvalStatus}</Badge>}
          </div>
        </div>
        <Card.Title>{item.itemName}</Card.Title>
        <Card.Text className="text-muted small mb-2">
          {item.category} • {item.location} • {item.itemDate}
        </Card.Text>
        <Card.Text>{item.description?.slice(0, 90)}{item.description?.length > 90 ? '...' : ''}</Card.Text>
        <Button as={Link} to={`/items/${item.id}`} variant="primary" size="sm">View Details</Button>
      </Card.Body>
    </Card>
  );
}
