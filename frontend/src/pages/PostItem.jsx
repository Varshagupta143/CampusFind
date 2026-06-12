import { useState } from 'react';
import { Alert, Button, Card, Col, Container, Form, Image, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { createItem } from '../services/itemService';

export default function PostItem() {
  const [form, setForm] = useState({
    itemName: '',
    category: '',
    type: 'LOST',
    description: '',
    location: '',
    itemDate: '',
    contactInfo: '',
    submittedTo: ''
  });
  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState('');
  const [msg, setMsg] = useState('');
  const navigate = useNavigate();

  const change = e => setForm({ ...form, [e.target.name]: e.target.value });

  const changeImage = e => {
    const file = e.target.files[0];
    setMsg('');
    if (!file) {
      setImage(null);
      setPreview('');
      return;
    }
    const allowedTypes = ['image/jpeg', 'image/png', 'image/webp', 'image/gif'];
    if (!allowedTypes.includes(file.type)) {
      setMsg('Only JPG, PNG, WEBP, and GIF images are allowed.');
      e.target.value = '';
      return;
    }
    if (file.size > 5 * 1024 * 1024) {
      setMsg('Image size must be less than 5 MB.');
      e.target.value = '';
      return;
    }
    setImage(file);
    setPreview(URL.createObjectURL(file));
  };

  const submit = async e => {
    e.preventDefault();
    setMsg('');

    const formData = new FormData();
    Object.entries(form).forEach(([key, value]) => formData.append(key, value));
    if (image) formData.append('image', image);

    try {
      await createItem(formData);
      navigate('/my-posts');
    } catch (err) {
      setMsg(err.response?.data?.message || 'Could not create item');
    }
  };

  return <Container className="py-4"><Card className="stat-card"><Card.Body className="p-4">
    <h2>Post Lost / Found Item</h2>
    {msg && <Alert variant="danger">{msg}</Alert>}
    <Form onSubmit={submit}>
      <Row>
        <Col md={6}>
          <Form.Group className="mb-3">
            <Form.Label>Item Name</Form.Label>
            <Form.Control name="itemName" value={form.itemName} onChange={change} required />
          </Form.Group>
        </Col>
        <Col md={6}>
          <Form.Group className="mb-3">
            <Form.Label>Type</Form.Label>
            <Form.Select name="type" value={form.type} onChange={change}>
              <option value="LOST">Lost</option>
              <option value="FOUND">Found</option>
            </Form.Select>
          </Form.Group>
        </Col>
      </Row>

      <Row>
        <Col md={6}>
          <Form.Group className="mb-3">
            <Form.Label>Category</Form.Label>
            <Form.Control name="category" placeholder="ID Card, Electronics, Books..." value={form.category} onChange={change} required />
          </Form.Group>
        </Col>
        <Col md={6}>
          <Form.Group className="mb-3">
            <Form.Label>Location</Form.Label>
            <Form.Control name="location" value={form.location} onChange={change} required />
          </Form.Group>
        </Col>
      </Row>

      <Form.Group className="mb-3">
        <Form.Label>Description</Form.Label>
        <Form.Control as="textarea" rows={3} name="description" value={form.description} onChange={change} />
      </Form.Group>

      <Row>
        <Col md={6}>
          <Form.Group className="mb-3">
            <Form.Label>Date</Form.Label>
            <Form.Control type="date" name="itemDate" value={form.itemDate} onChange={change} required />
          </Form.Group>
        </Col>
        <Col md={6}>
          <Form.Group className="mb-3">
            <Form.Label>Upload Image</Form.Label>
            <Form.Control type="file" accept="image/*" onChange={changeImage} />
            <Form.Text className="text-muted">Allowed: JPG, PNG, WEBP, GIF. Max size: 5 MB.</Form.Text>
          </Form.Group>
        </Col>
      </Row>

      {preview && <div className="mb-3">
        <p className="mb-2 fw-semibold">Image Preview</p>
        <Image src={preview} rounded style={{ maxWidth: '220px', maxHeight: '160px', objectFit: 'cover' }} />
      </div>}

      <Row>
        <Col md={6}>
          <Form.Group className="mb-3">
            <Form.Label>Contact Info</Form.Label>
            <Form.Control name="contactInfo" value={form.contactInfo} onChange={change} />
          </Form.Group>
        </Col>
        <Col md={6}>
          <Form.Group className="mb-3">
            <Form.Label>Submitted To</Form.Label>
            <Form.Control name="submittedTo" value={form.submittedTo} onChange={change} placeholder="Security office / department" />
          </Form.Group>
        </Col>
      </Row>

      <Alert variant="info" className="py-2">Your post will be visible to other students after admin approval.</Alert>
      <Button type="submit">Submit Post</Button>
    </Form>
  </Card.Body></Card></Container>;
}
