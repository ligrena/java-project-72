CREATE TABLE IF NOT EXISTS urls (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS url_checks (
    id BIGSERIAL PRIMARY KEY,
    url_id BIGINT NOT NULL,
    status_code INT,
    h1 TEXT,
    title TEXT,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (url_id) REFERENCES urls(id) ON DELETE CASCADE
);