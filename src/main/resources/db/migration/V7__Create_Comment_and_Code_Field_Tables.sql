-- Create table for Comment
CREATE TABLE comment (
    id UUID PRIMARY KEY,
    activity_id UUID NOT NULL,
    text TEXT NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT NOW(),
    -- Audit and version control columns
    row_version SMALLINT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_created_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    row_updated_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    CONSTRAINT fk_activity_user
        FOREIGN KEY (activity_id) 
        REFERENCES activity_user(id) ON DELETE CASCADE
);

-- Alter ActivityUser table to add user_code column
ALTER TABLE activity_user 
ADD COLUMN user_code TEXT; -- Optional field for user code
