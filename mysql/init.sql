CREATE DATABASE IF NOT EXISTS kodb;
USE kodb;

CREATE TABLE IF NOT EXISTS member (
    MEMBER_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    loginPw VARCHAR(255) NOT NULL,
    verifiedPw VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL UNIQUE,
    age INT,
    role VARCHAR(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO member (email, loginPw, verifiedPw, nickname, age, role)
SELECT 'test1@example.com', 'password1', 'password1', 'User1', 20, 'USER'
    WHERE NOT EXISTS (
    SELECT 1 FROM member WHERE email = 'test1@example.com' AND nickname = 'User1'
);

INSERT INTO member (email, loginPw, verifiedPw, nickname, age, role)
SELECT 'admin@example.com', 'adminpw', 'adminpw', '관리자', 20, 'ADMIN'
    WHERE NOT EXISTS (
    SELECT 1 FROM member WHERE email = 'admin@example.com' AND nickname = '관리자'
);
