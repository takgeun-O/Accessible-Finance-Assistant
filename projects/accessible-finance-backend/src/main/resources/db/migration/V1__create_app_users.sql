CREATE TABLE app_users
(
    id            UUID         NOT NULL,
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    name          VARCHAR(100) NOT NULL,
    role          VARCHAR(20)  NOT NULL,
    status        VARCHAR(20)  NOT NULL,
    created_at    TIMESTAMPTZ  NOT NULL,
    updated_at    TIMESTAMPTZ  NOT NULL,

    CONSTRAINT pk_app_users
        PRIMARY KEY (id),

    CONSTRAINT uq_app_users_email
        UNIQUE (email),

    CONSTRAINT ck_app_users_email_not_blank
        CHECK (char_length(btrim(email)) > 0),

    CONSTRAINT ck_app_users_email_lowercase
        CHECK (email = lower(email)),

    CONSTRAINT ck_app_users_password_hash_not_blank
        CHECK (char_length(btrim(password_hash)) > 0),

    CONSTRAINT ck_app_users_name_not_blank
        CHECK (char_length(btrim(name)) > 0),

    CONSTRAINT ck_app_users_role
        CHECK (role IN ('CUSTOMER', 'STAFF')),

    CONSTRAINT ck_app_users_status
        CHECK (status IN ('ACTIVE', 'LOCKED', 'DISABLED')),

    CONSTRAINT ck_app_users_updated_at
        CHECK (updated_at >= created_at)
);

CREATE INDEX idx_app_users_role_status
    ON app_users (role, status);