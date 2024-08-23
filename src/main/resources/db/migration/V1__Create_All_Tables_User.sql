-- Create Users table
CREATE TABLE users (
    id UUID PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    phone_number VARCHAR(15),
    address VARCHAR(255)
);

-- Create Role table
CREATE TABLE role (
    id UUID PRIMARY KEY,
    role_id VARCHAR(36) NOT NULL,
    name_role VARCHAR(100) NOT NULL
);

-- Create UserRole table (many-to-many relationship between Users and Role)
CREATE TABLE user_role (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

-- Optional: Add indexes to improve performance
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_role_name_role ON role (name_role);
