# Deployment checklist

## Supabase
Run all SQL files under `supabase/migrations` in filename order.

## Render backend
Use `backend` as the service root. Dockerfile: `backend/Dockerfile` if the Render service is configured from repository root, or the included Dockerfile when root directory is `backend`.

Set:
- DATABASE_URL
- DATABASE_USER
- DATABASE_PASSWORD
- JWT_SECRET
- FRONTEND_URL
- ADMIN_EMAIL
- ADMIN_PASSWORD
- MAIL_HOST / MAIL_PORT / MAIL_USERNAME / MAIL_PASSWORD

After deployment verify:
- `/api/health`
- `/swagger-ui/index.html`

## Cloudflare Pages
Use `frontend` as root directory.
Build: `npm run build`
Output: `dist`
Set `VITE_API_URL` to the Render backend URL.

## First admin
On first backend startup, if `ADMIN_EMAIL` and `ADMIN_PASSWORD` are set, an ADMIN account is created or updated for that email. Keep these environment variables private.

## Important
Never put `.env`, passwords, SMTP credentials or JWT secrets in Git.
