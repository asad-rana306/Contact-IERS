# API Documentation

## Base URL
`/api`

## Authentication

Most contact and profile endpoints are public in the current implementation.
The admin endpoints require Basic Auth with the `ADMIN` role.

### Default admin credentials
- Username: `admin`
- Password: `admin123`

### Default user credentials
- Username: `user`
- Password: `user123`

## Standard Error Response

All validation and runtime errors are returned in a consistent structure:

```json
{
  "timestamp": "2026-04-02T02:45:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/contacts",
  "details": [
    "name: must not be blank"
  ]
}
```

## Contact APIs

### 1. Add a new contact
`POST /api/contacts`

**Success Response**
- `201 Created`

**Request Body**
```json
{
  "userId": 1,
  "name": "Asad",
  "phoneNumber": "1234567890",
  "relation": "Brother",
  "priority": "PRIMARY",
  "isVerified": false
}
```

### 2. Get all contacts for a user
`GET /api/contacts/user/{userId}`

**Success Response**
- `200 OK`

### 3. Update contact details
`PUT /api/contacts/{id}`

Uses `ContactRequestDto`.

**Success Response**
- `200 OK`

### 4. Update priority
`PATCH /api/contacts/{id}/priority`

Uses `ContactPriorityRequestDto`.

**Success Response**
- `200 OK`

**Request Body**
```json
{
  "priority": "SECONDARY"
}
```

### 5. Verify contact
`PATCH /api/contacts/{id}/verify`

**Success Response**
- `200 OK`

### 6. Delete contact
`DELETE /api/contacts/{id}`

**Success Response**
- `204 No Content`

## Profile APIs

### 1. Create profile
`POST /api/profiles`

Uses `UserProfileRequestDto`.

**Success Response**
- `201 Created`

**Request Body**
```json
{
  "userName": "asad",
  "bloodType": "O+",
  "allergies": "Peanuts",
  "medicalConditions": "Asthma",
  "vehicleLicensePlate": "ABC-123"
}
```

### 2. Get profile by username
`GET /api/profiles/user/{userName}`

**Success Response**
- `200 OK`

### 3. Update profile
`PUT /api/profiles/user/{userName}`

Uses `UserProfileRequestDto`.

**Success Response**
- `200 OK`

## Admin APIs
All admin endpoints require the `ADMIN` role.

### 1. Get paginated contacts
`GET /api/admin/contacts?page=0&size=20`

**Success Response**
- `200 OK`

### 2. Get statistics
`GET /api/admin/stats`

**Success Response**
- `200 OK`

Response example:
```json
{
  "totalContacts": 10,
  "verifiedContacts": 4,
  "unverifiedContacts": 6
}
```

### 3. Delete a profile by username
`DELETE /api/admin/profiles/{userName}`

**Success Response**
- `204 No Content`

**Typical error responses**
- `401 Unauthorized` when auth is missing or invalid for admin endpoints
- `403 Forbidden` when the authenticated user does not have `ADMIN`
- `404 Not Found` when a contact or profile does not exist
- `400 Bad Request` when required fields are missing

