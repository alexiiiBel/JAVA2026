-- Schema for `phonetest` database
-- Charset: utf8mb4, engine: InnoDB

CREATE TABLE IF NOT EXISTS users (
    idusers             BIGINT       NOT NULL AUTO_INCREMENT,
    lastname            VARCHAR(64)  NOT NULL,
    password            CHAR(64)     NOT NULL,
    email               VARCHAR(255) NOT NULL,
    phone               VARCHAR(32)  NULL,
    is_active           BOOLEAN      NOT NULL DEFAULT FALSE,
    confirmation_token  VARCHAR(128) NULL,
    token_created_at    DATETIME     NULL,
    telegram_chat_id    BIGINT       NULL,
    PRIMARY KEY (idusers),
    UNIQUE KEY uq_users_lastname (lastname),
    UNIQUE KEY uq_users_email (email),
    KEY idx_users_confirmation_token (confirmation_token)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS phone_entries (
    id              INT          NOT NULL AUTO_INCREMENT,
    user_id         BIGINT       NOT NULL,
    contact_name    VARCHAR(128) NOT NULL,
    contact_phone   VARCHAR(32)  NOT NULL,
    contact_email   VARCHAR(255) NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_phone_entries_user_id (user_id),
    CONSTRAINT fk_phone_entries_user
        FOREIGN KEY (user_id) REFERENCES users (idusers) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS media_files (
    id                 INT          NOT NULL AUTO_INCREMENT,
    user_id            BIGINT       NOT NULL,
    stored_filename    VARCHAR(255) NOT NULL,
    original_filename  VARCHAR(255) NOT NULL,
    content_type       VARCHAR(100) NULL,
    file_size          BIGINT       NOT NULL DEFAULT 0,
    file_path          VARCHAR(512) NOT NULL,
    upload_date        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_media_files_user_id (user_id),
    CONSTRAINT fk_media_files_user
        FOREIGN KEY (user_id) REFERENCES users (idusers) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
