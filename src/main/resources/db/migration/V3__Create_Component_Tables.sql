-- Create table for ComponentCategory
CREATE TABLE component_category (
    id UUID PRIMARY KEY,
    category_id VARCHAR(255) NOT NULL,
    category_name VARCHAR(255) NOT NULL,
    -- Audit and version control columns
    row_version SMALLINT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_created_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    row_updated_by VARCHAR(64) NOT NULL DEFAULT 'unknown'
);

-- Create table for ComponentSubCategory
CREATE TABLE component_sub_category (
    id UUID PRIMARY KEY,
    sub_category_id VARCHAR(255) NOT NULL,
    total_quantity INTEGER NOT NULL,
    sub_category_name VARCHAR(255) NOT NULL,
    category_id UUID,
    -- Audit and version control columns
    row_version SMALLINT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_created_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    row_updated_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES component_category(id)
);

-- Create table for Component
CREATE TABLE component (
    id UUID PRIMARY KEY,
    component_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    serial_number VARCHAR(255) NOT NULL,
    description TEXT,
    quantity INTEGER NOT NULL,
    sub_category_id UUID,
    category_id UUID,
    -- Audit and version control columns
    row_version SMALLINT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_created_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    row_updated_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    CONSTRAINT fk_sub_category
        FOREIGN KEY (sub_category_id)
        REFERENCES component_sub_category(id),
    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES component_category(id)
);

-- Create table for ComponentTest
CREATE TABLE component_test (
    id UUID PRIMARY KEY,
    test_description TEXT NOT NULL,
    component_id UUID,
    -- Audit and version control columns
    row_version SMALLINT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_created_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    row_updated_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    CONSTRAINT fk_component
        FOREIGN KEY (component_id)
        REFERENCES component(id)
);
