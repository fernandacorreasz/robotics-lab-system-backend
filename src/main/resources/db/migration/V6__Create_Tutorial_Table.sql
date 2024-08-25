-- Create table for Tutorial
CREATE TABLE tutorial (
    id UUID PRIMARY KEY,
    tutorial_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    -- Audit and version control columns
    row_version SMALLINT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_created_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    row_updated_by VARCHAR(64) NOT NULL DEFAULT 'unknown'
);

-- Create many-to-many relationship table between Tutorial and Component
CREATE TABLE tutorial_component (
    tutorial_id UUID NOT NULL,
    component_id UUID NOT NULL,
    PRIMARY KEY (tutorial_id, component_id),
    CONSTRAINT fk_tutorial
        FOREIGN KEY (tutorial_id) 
        REFERENCES tutorial(id),
    CONSTRAINT fk_component
        FOREIGN KEY (component_id) 
        REFERENCES component(id)
);
