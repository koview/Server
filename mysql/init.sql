CREATE DATABASE IF NOT EXISTS kodb;
USE kodb;

-- Member 데이터
CREATE TABLE IF NOT EXISTS member (
    MEMBER_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    login_pw VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL UNIQUE,
    age INT,
    role VARCHAR(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO member (email, login_pw, nickname, age, role)
SELECT 'test1@example.com', '$2a$10$c/pR2kwhJOWjvzy05fySB.tIorRRJ6r5ggxNSdxf4M/lkLenN3iiS', 'User1', 20, 'USER'
    WHERE NOT EXISTS (
    SELECT 1 FROM member WHERE email = 'test1@example.com' AND nickname = 'User1'
);

INSERT INTO member (email, login_pw, nickname, age, role)
SELECT 'admin@example.com', '$2a$10$qiIizKopu6SydQUmNmXAbOd7mSNaGYkdntdBu5fQgdf7pUdPjLJIq', '관리자', 20, 'ADMIN'
    WHERE NOT EXISTS (
    SELECT 1 FROM member WHERE email = 'admin@example.com' AND nickname = '관리자'
);

-- Shop 데이터
CREATE TABLE IF NOT EXISTS shop (
    shop_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shop_name VARCHAR(255) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO shop (shop_name)
SELECT 'Amazon'
    WHERE NOT EXISTS (
    SELECT 1 FROM shop WHERE shop_name = 'Amazon'
);

INSERT INTO shop (shop_name)
SELECT 'eBay'
    WHERE NOT EXISTS (
    SELECT 1 FROM shop WHERE shop_name = 'eBay'
);

INSERT INTO shop (shop_name)
SELECT 'AliExpress'
    WHERE NOT EXISTS (
    SELECT 1 FROM shop WHERE shop_name = 'AliExpress'
);

INSERT INTO shop (shop_name)
SELECT 'Walmart'
    WHERE NOT EXISTS (
    SELECT 1 FROM shop WHERE shop_name = 'Walmart'
);

INSERT INTO shop (shop_name)
SELECT 'Target'
    WHERE NOT EXISTS (
    SELECT 1 FROM shop WHERE shop_name = 'Target'
);

-- Review 데이터
CREATE TABLE IF NOT EXISTS review (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    member_id BIGINT NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member (member_id)
);

INSERT INTO review (content, member_id)
SELECT 'review1', 1
    WHERE NOT EXISTS (
    SELECT 1 FROM review WHERE content = 'review1' AND member_id = 1
);

INSERT INTO review (content, member_id)
SELECT 'review2', 1
    WHERE NOT EXISTS (
    SELECT 1 FROM review WHERE content = 'review2' AND member_id = 1
);