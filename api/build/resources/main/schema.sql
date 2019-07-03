CREATE TABLE search_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    keyword VARCHAR(100) NOT NULL,
    create_at TIMESTAMP NOT NULL,
    create_by VARCHAR(20) NOT NULL
);

CREATE TABLE top_keyword (
    keyword VARCHAR(100) PRIMARY KEY,
    cnt INT NOT NULL
);

CREATE TABLE member (
    id VARCHAR(20) PRIMARY KEY,
    password VARCHAR(44) NOT NULL,
    token VARCHAR(44) NOT NULL,
    token_expire TIMESTAMP NOT NULL
);