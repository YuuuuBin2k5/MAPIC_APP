-- Migration: Thêm cột avatar_url và bảng tokens
-- Chạy script này trong pgAdmin

-- 1. Thêm cột avatar_url vào bảng users
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS avatar_url VARCHAR(500);

-- 2. Cập nhật is_active mặc định thành TRUE (vì không dùng OTP)
ALTER TABLE users 
ALTER COLUMN is_active SET DEFAULT TRUE;

-- 3. Tạo bảng tokens để lưu Opaque Token
CREATE TABLE IF NOT EXISTS tokens (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token VARCHAR(255) UNIQUE NOT NULL,
    device_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- 4. Tạo index để tăng tốc query
CREATE INDEX IF NOT EXISTS idx_tokens_token ON tokens(token);
CREATE INDEX IF NOT EXISTS idx_tokens_user_id ON tokens(user_id);
CREATE INDEX IF NOT EXISTS idx_tokens_active ON tokens(is_active);

-- 5. (Optional) Xóa bảng otp_codes nếu không dùng nữa
-- DROP TABLE IF EXISTS otp_codes;

COMMENT ON TABLE tokens IS 'Bảng lưu Opaque Token cho xác thực (không dùng JWT)';
COMMENT ON COLUMN users.avatar_url IS 'URL ảnh đại diện từ Cloudinary';
