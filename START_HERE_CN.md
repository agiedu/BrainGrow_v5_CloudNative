# BrainGrow V5 —— 小白使用说明

这是完整项目，不需要逐个替换 Java 文件。

## 你拿到的是什么？

- `backend`：服务器、登录、安全、题库、学习记录、管理员接口
- `frontend`：用户网页和管理员网页
- `supabase/migrations`：数据库初始化脚本
- `docs`：部署说明

## 如果你只是要继续使用现有 Render / Supabase

### 第一步：数据库
打开 Supabase SQL Editor，把 `supabase/migrations` 下的 001 到 005 按顺序执行。

### 第二步：GitHub
把这个项目完整上传到你的 GitHub 仓库。

### 第三步：Render
Backend 环境变量至少设置：

- `DATABASE_URL`
- `DATABASE_USER`
- `DATABASE_PASSWORD`
- `JWT_SECRET`
- `FRONTEND_URL`
- `ADMIN_EMAIL`
- `ADMIN_PASSWORD`

### 第四步：邮箱
如果要真正发送找回密码验证码，再设置：

- `MAIL_HOST`
- `MAIL_PORT`
- `MAIL_USERNAME`
- `MAIL_PASSWORD`

没有配置邮箱时，验证码会打印在 Backend 日志中，仅适合开发测试。

### 第五步：Cloudflare Pages
Frontend：

- Root directory：`frontend`
- Build command：`npm run build`
- Output directory：`dist`
- `VITE_API_URL`：你的 Render 后端地址

## 部署完成后

打开：

`https://你的-render-地址/swagger-ui/index.html`

登录接口取得 JWT 后，在 Swagger 页面点击右上角：

`Authorize`

只粘贴：

`eyJ...`

**不要自己输入 `Bearer `。**

Swagger 会自动发送：

`Authorization: Bearer eyJ...`

## 管理员

第一次启动 Backend 时，如果设置了：

`ADMIN_EMAIL`
`ADMIN_PASSWORD`

系统会自动建立/更新这个管理员账户。

## 安全

不要把以下内容提交到 GitHub：

- JWT_SECRET
- ADMIN_PASSWORD
- DATABASE_PASSWORD
- MAIL_PASSWORD

如果以前曾经把真实密码或 JWT secret 推到 GitHub，请立即更换。
