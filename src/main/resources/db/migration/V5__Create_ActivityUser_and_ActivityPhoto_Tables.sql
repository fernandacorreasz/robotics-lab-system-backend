-- Create table for ActivityUser
CREATE TABLE activity_user (
    id UUID PRIMARY KEY,
    activity_title VARCHAR(255) NOT NULL,
    activity_description TEXT NOT NULL,
    activity_status VARCHAR(50) NOT NULL,
    time_spent INTEGER,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    -- Audit and version control columns
    row_version SMALLINT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_created_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    row_updated_by VARCHAR(64) NOT NULL DEFAULT 'unknown'
);

-- Create table for ActivityPhoto
CREATE TABLE activity_photo (
    id UUID PRIMARY KEY,
    image_file BYTEA,
    -- Audit and version control columns
    row_version SMALLINT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_created_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    row_updated_by VARCHAR(64) NOT NULL DEFAULT 'unknown'
);

-- Create many-to-many relationship table between ActivityUser and Component
CREATE TABLE activity_component (
    activity_id UUID NOT NULL,
    component_id UUID NOT NULL,
    PRIMARY KEY (activity_id, component_id),
    -- Audit and version control columns
    row_version SMALLINT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_created_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    row_updated_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    CONSTRAINT fk_activity_user
        FOREIGN KEY (activity_id) 
        REFERENCES activity_user(id),
    CONSTRAINT fk_component
        FOREIGN KEY (component_id) 
        REFERENCES component(id)
);

-- Create many-to-many relationship table between ActivityUser and ActivityPhoto
CREATE TABLE activity_photo_relation (
    activity_id UUID NOT NULL,
    photo_id UUID NOT NULL,
    PRIMARY KEY (activity_id, photo_id),
    -- Audit and version control columns
    row_version SMALLINT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_created_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    row_updated_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    CONSTRAINT fk_activity_user
        FOREIGN KEY (activity_id) 
        REFERENCES activity_user(id),
    CONSTRAINT fk_activity_photo
        FOREIGN KEY (photo_id) 
        REFERENCES activity_photo(id)
);