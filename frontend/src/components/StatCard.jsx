import { Card } from 'react-bootstrap';
export default function StatCard({ title, value }) {
  return <Card className="stat-card"><Card.Body><div className="text-muted small">{title}</div><h3 className="mb-0">{value}</h3></Card.Body></Card>;
}
