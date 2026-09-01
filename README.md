# BrainGrow V5 Cloud Native — Complete 5.1

BrainGrow is a learning application for children aged 7–18, focused on logical reasoning and memory training.

## Included
- Student registration (7–18 only)
- Secure login with BCrypt + JWT
- Password reset by email verification code
- Verification-code expiration and resend cooldown
- Login failure lockout
- Student exercise catalogue and server-side answer grading
- Learning history
- Admin-only user management and role/status management
- Admin exercise management
- Admin statistics
- Swagger/OpenAPI with JWT Authorize
- PostgreSQL/Supabase migrations
- Render + Cloudflare Pages deployment files

## 1. Database
Create a Supabase PostgreSQL database and run:
1. `supabase/migrations/001_users.sql`
2. `supabase/migrations/002_exercises.sql`
3. `supabase/migrations/003_learning.sql`
4. `supabase/migrations/004_auth_security.sql`
5. `supabase/migrations/005_integrity.sql`

For a brand-new database, running them in order is sufficient.

## 2. Backend
Required environment variables:
- `DATABASE_URL` (JDBC PostgreSQL URL), or `DATABASE_HOST`, `DATABASE_PORT`, `DATABASE_NAME`
- `DATABASE_USER`
- `DATABASE_PASSWORD`
- `JWT_SECRET` — at least 32 characters
- `FRONTEND_URL`
- `ADMIN_EMAIL`
- `ADMIN_PASSWORD`

For real password-reset email, set:
- `MAIL_HOST`
- `MAIL_PORT`
- `MAIL_USERNAME`
- `MAIL_PASSWORD`

If mail credentials are absent, the backend prints the development verification code to its log instead of sending email.

Local:
```bash
cd backend
mvn spring-boot:run
```

Swagger:
`http://localhost:8080/swagger-ui/index.html`

## 3. Frontend
```bash
cd frontend
npm install
npm run dev
```
Set `VITE_API_URL=http://localhost:8080` for local use.

## 4. Render
Deploy `backend` using the included Dockerfile. Add the backend environment variables in Render.

## 5. Cloudflare Pages
Root directory: `frontend`
Build command: `npm run build`
Output directory: `dist`
Environment variable: `VITE_API_URL=<Render backend URL>`

## Security notes
Do not commit real passwords, SMTP credentials, database passwords or JWT secrets. Rotate any secret that has previously been committed to a public repository.
