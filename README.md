# CampusFind - College Lost and Found Portal

CampusFind is a secure full-stack College Lost and Found portal where students can post lost/found items with images, search posts, send claim requests, and manage item status. Admins can approve/reject item posts and view users.

## Tech Stack

- Frontend: React, Bootstrap, Axios, React Router
- Backend: Spring Boot, Spring Security, JWT, DTOs, Mapper pattern
- Database: MongoDB Atlas / MongoDB local
- File Handling: Secure multipart image upload saved in `backend/uploads`

## Secure & Professional Features Added

- JWT authentication
- Role-based authorization: Student/Admin
- Admin-only APIs under `/api/admin/**`
- Default admin seeder using environment variables
- MongoDB URI and JWT secret through environment variables
- Image upload instead of image URL
- Server-side image validation: JPG, PNG, WEBP, GIF only, max 5 MB
- Client-side image validation and preview
- Contact details hidden until claim approval
- Ownership checks: students can update/delete only their own posts
- Admin approval workflow for item posts
- Claim validation: no self-claim, no duplicate active claims
- Global exception handler with clean error messages
- Audit fields: createdAt and updatedAt
- Bootstrap dashboard, cards, badges, search, filters, notifications

## Important Security Note

Do not commit your real MongoDB username/password or JWT secret to GitHub.
Use environment variables instead.

## Backend Setup

Open `backend/src/main/resources/application.properties`. It already uses environment variables:

```properties
spring.data.mongodb.uri=${MONGODB_URI:mongodb://localhost:27017/campusfind}
app.jwt.secret=${JWT_SECRET:campusfind-dev-secret-change-before-production-1234567890}
```

### IntelliJ Environment Variables

In IntelliJ:

1. Click Run Configurations.
2. Select `CampusFindApplication`.
3. Add Environment variables:

```text
MONGODB_URI=mongodb+srv://YOUR_USERNAME:YOUR_PASSWORD@YOUR_CLUSTER.mongodb.net/CampusFind?retryWrites=true&w=majority
JWT_SECRET=your-very-long-secret-key-minimum-32-characters
ADMIN_EMAIL=admin@campusfind.com
ADMIN_PASSWORD=Admin@12345
ADMIN_NAME=CampusFind Admin
CORS_ALLOWED_ORIGIN=http://localhost:5173
```

Run the backend using IntelliJ green Run button or Maven:

```bash
cd backend
mvn spring-boot:run
```

If Maven Wrapper exists in your local folder, use:

```bash
cd backend
.\\mvnw.cmd spring-boot:run
```

Backend runs at:

```text
http://localhost:8080
```

## Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

Frontend runs at:

```text
http://localhost:5173
```

## Default Admin Account

The backend creates an admin automatically if it does not exist.

Default local values:

```text
Email: admin@campusfind.com
Password: Admin@12345
```

For real use, change these using environment variables:

```text
ADMIN_EMAIL
ADMIN_PASSWORD
ADMIN_NAME
```

## Main Flow

1. Student registers/logs in.
2. Student posts lost/found item with image.
3. Post status is `PENDING` until admin approval.
4. Admin approves/rejects post from Admin Dashboard.
5. Approved posts are visible to students.
6. Student sends claim request with proof.
7. Owner approves/rejects claim.
8. Contact details become visible only after approval.

## Important API Endpoints

### Auth

```text
POST /api/auth/register
POST /api/auth/login
GET  /api/auth/profile
```

### Items

```text
POST   /api/items
GET    /api/items
GET    /api/items/{id}
GET    /api/items/my-posts
PUT    /api/items/{id}
PUT    /api/items/{id}/status
DELETE /api/items/{id}
```

### Claims

```text
POST /api/claims
GET  /api/claims/my-claims
GET  /api/claims/received
PUT  /api/claims/{id}/approve
PUT  /api/claims/{id}/reject
```

### Admin

```text
GET /api/admin/items/pending
PUT /api/admin/items/{id}/approve
PUT /api/admin/items/{id}/reject
GET /api/admin/users
```

## Resume Description

Built a secure full-stack College Lost and Found Portal using React, Bootstrap, Spring Boot, MongoDB, and JWT authentication. Implemented role-based authorization, image upload validation, admin approval workflow, claim verification, hidden contact details until approval, ownership checks, DTO-mapper architecture, and professional REST API error handling.
