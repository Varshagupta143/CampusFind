# CampusFind - College Lost and Found Portal

CampusFind is a secure full-stack College Lost and Found Portal where students can post lost and found items with images, search items, send claim requests, and manage item status. Admins can approve or reject item posts and manage users.

The project is built using React, Bootstrap, Spring Boot, MongoDB, and JWT authentication with HttpOnly cookies.

---

## Tech Stack

### Frontend

* React.js
* Bootstrap
* Axios
* React Router

### Backend

* Spring Boot
* Spring Security
* JWT Authentication
* HttpOnly Cookie Authentication
* DTO Pattern
* Mapper Pattern
* Global Exception Handling

### Database

* MongoDB Atlas
* MongoDB Local Support

### File Handling

* Multipart image upload
* Images stored in `backend/uploads`
* Server-side image validation

---

## Features

### Student Features

* Register and login
* Post lost item
* Post found item
* Upload item image
* Search and filter items
* View item details
* Send claim request with proof
* View own posts
* View sent claim requests
* View received claim requests
* Approve or reject claim requests received on own posts
* Update or delete only own posts
* Contact details visible only after claim approval

### Admin Features

* Admin login
* Admin dashboard
* View pending item posts
* Approve or reject item posts
* View registered users
* Access admin-only APIs

---

## Secure and Professional Features

* JWT authentication using HttpOnly cookies
* JWT is not stored in localStorage
* Role-based authorization: Student/Admin
* Admin-only APIs under `/api/admin/**`
* MongoDB URI, JWT secret, and admin credentials stored using environment variables
* Default admin seeder using environment variables
* Image upload instead of image URL
* Server-side image validation
* Allowed image types: JPG, JPEG, PNG, WEBP, GIF
* Maximum image size: 5 MB
* Client-side image validation and preview
* Contact details hidden until claim is approved
* Ownership checks for update/delete operations
* Admin approval workflow for item posts
* Claim validation:

  * No self-claim
  * No duplicate active claims
* Global exception handler with clean error messages
* Audit fields: `createdAt` and `updatedAt`
* Bootstrap cards, badges, dashboard, filters, and responsive UI

---

## Project Structure

```text
CampusFind
│
├── backend
│   ├── src
│   │   └── main
│   │       ├── java
│   │       │   └── com
│   │       │       └── campusfind
│   │       │           └── campusfind
│   │       │               ├── controller
│   │       │               ├── dto
│   │       │               ├── exception
│   │       │               ├── mapper
│   │       │               ├── model
│   │       │               ├── repository
│   │       │               ├── security
│   │       │               ├── service
│   │       │               └── CampusFindApplication.java
│   │       │
│   │       └── resources
│   │           └── application.properties
│   │
│   ├── uploads
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
│
├── frontend
│   ├── src
│   │   ├── components
│   │   ├── context
│   │   ├── pages
│   │   ├── services
│   │   ├── App.jsx
│   │   └── main.jsx
│   │
│   ├── package.json
│   └── vite.config.js
│
├── .gitignore
└── README.md
```

---

## Important Security Note

Do not commit real MongoDB usernames, passwords, JWT secrets, or admin passwords to GitHub.

This project uses environment variables for sensitive values.

---

## Backend Configuration

Open:

```text
backend/src/main/resources/application.properties
```

The file should use environment variables:

```properties
spring.application.name=CampusFind
server.port=${PORT:8080}

spring.data.mongodb.uri=${CAMPUSFIND_MONGODB_URI}

app.jwt.secret=${CAMPUSFIND_JWT_SECRET}
app.jwt.expiration-ms=${JWT_EXPIRATION_MS:86400000}

app.cors.allowed-origin=${CORS_ALLOWED_ORIGIN:http://localhost:5173}

app.admin.email=${CAMPUSFIND_ADMIN_EMAIL}
app.admin.password=${CAMPUSFIND_ADMIN_PASSWORD}
app.admin.name=${CAMPUSFIND_ADMIN_NAME}

app.cookie.name=CAMPUSFIND_TOKEN
app.cookie.secure=false
app.cookie.same-site=Lax

spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
```

---

## Environment Variables

Set these variables in Windows System Environment Variables or IntelliJ Run Configuration.

```text
CAMPUSFIND_MONGODB_URI=mongodb+srv://YOUR_USERNAME:YOUR_PASSWORD@YOUR_CLUSTER.mongodb.net/CampusFind?retryWrites=true&w=majority

CAMPUSFIND_JWT_SECRET=your-very-long-secret-key-minimum-32-characters

CAMPUSFIND_ADMIN_EMAIL=admin@campusfind.com

CAMPUSFIND_ADMIN_PASSWORD=Admin@12345

CAMPUSFIND_ADMIN_NAME=CampusFind Admin

CORS_ALLOWED_ORIGIN=http://localhost:5173
```

After adding environment variables, restart IntelliJ or terminal so the new values are loaded.

---

## Cookie Security

For local development, the project uses:

```properties
app.cookie.secure=false
```

This is required because local development runs on HTTP:

```text
Frontend: http://localhost:5173
Backend:  http://localhost:8080
```

For production deployment with HTTPS, change it to:

```properties
app.cookie.secure=true
```

---

## Authentication Flow

1. User logs in using email and password.
2. Backend validates credentials.
3. Backend generates JWT token.
4. JWT is stored in an HttpOnly cookie named `CAMPUSFIND_TOKEN`.
5. Frontend does not store JWT in localStorage.
6. Browser automatically sends the cookie with API requests.
7. Backend reads JWT from cookie using `JwtFilter`.
8. Protected APIs are accessible only after successful authentication.
9. Logout clears the HttpOnly cookie.

This improves security because JavaScript cannot directly access the JWT token.

---

## Backend Setup

Go to the backend folder:

```bash
cd backend
```

Run the backend using Maven Wrapper:

```bash
.\mvnw.cmd spring-boot:run
```

Or using Maven if Maven is installed globally:

```bash
mvn spring-boot:run
```

Backend runs at:

```text
http://localhost:8080
```

---

## Frontend Setup

Go to the frontend folder:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Run frontend:

```bash
npm run dev
```

Frontend runs at:

```text
http://localhost:5173
```

---

## Default Admin Account

The backend creates an admin automatically if it does not already exist.

Admin credentials are taken from environment variables:

```text
CAMPUSFIND_ADMIN_EMAIL
CAMPUSFIND_ADMIN_PASSWORD
CAMPUSFIND_ADMIN_NAME
```

Example local values:

```text
Email: admin@campusfind.com
Password: Admin@12345
```

For real use, change these values using environment variables.

---

## Main Project Flow

1. Student registers or logs in.
2. Student posts a lost or found item with image.
3. Item is saved with approval status `PENDING`.
4. Admin reviews pending posts.
5. Admin approves or rejects the post.
6. Approved posts are visible to students.
7. Another student sends a claim request with proof.
8. Item owner reviews received claims.
9. Owner approves or rejects the claim.
10. Contact details become visible only after claim approval.

---

## API Endpoints

### Auth APIs

```text
POST /api/auth/register
POST /api/auth/login
POST /api/auth/logout
GET  /api/auth/profile
```

### Item APIs

```text
POST   /api/items
GET    /api/items
GET    /api/items/{id}
GET    /api/items/my-posts
PUT    /api/items/{id}
PUT    /api/items/{id}/status
DELETE /api/items/{id}
```

### Claim APIs

```text
POST /api/claims
GET  /api/claims/my-claims
GET  /api/claims/received
PUT  /api/claims/{id}/approve
PUT  /api/claims/{id}/reject
```

### Admin APIs

```text
GET /api/admin/items/pending
PUT /api/admin/items/{id}/approve
PUT /api/admin/items/{id}/reject
GET /api/admin/users
```

---

## Testing Checklist

Use this checklist to verify the project:

```text
Backend starts successfully
MongoDB Atlas connects successfully
Admin user is auto-created
Student registration works
Student login works
JWT is stored in HttpOnly cookie
JWT is not stored in localStorage
Profile API works after login
Protected APIs are blocked before login
Student can upload item image
Invalid image files are rejected
Item goes to pending approval
Admin can approve item
Approved item appears to users
Student can send claim request
Owner can approve or reject claim
Contact details are hidden until claim approval
Student cannot access admin APIs
Student cannot edit/delete other users' posts
Logout clears HttpOnly cookie
```

---

## How to Verify HttpOnly Cookie JWT

Open browser DevTools:

```text
Inspect → Application → Cookies
```

After login, check cookie:

```text
CAMPUSFIND_TOKEN
```

It should have:

```text
HttpOnly = true
SameSite = Lax
Path = /
```

Then check:

```text
Application → Local Storage
```

There should be no JWT token stored in localStorage.

---

## Resume Description

Built a secure full-stack College Lost and Found Portal using React, Bootstrap, Spring Boot, MongoDB, and JWT HttpOnly cookie authentication. Implemented role-based authorization, image upload validation, admin approval workflow, claim verification, hidden contact details until approval, ownership checks, DTO-mapper architecture, and professional REST API error handling.

---

## Future Improvements

* Email notifications for claim approval/rejection
* Cloudinary or AWS S3 image storage
* Pagination for item listing
* Advanced search and sorting
* Password reset using email OTP
* CSRF protection for production cookie-based authentication
* Deployment on Render, Railway, Netlify, or Vercel
