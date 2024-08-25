-- Create table for ComponentCategory
CREATE TABLE component_category (
    id UUID PRIMARY KEY,
    category_id VARCHAR(255) NOT NULL,
    category_name VARCHAR(255) NOT NULL
);

-- Create table for ComponentSubCategory
CREATE TABLE component_sub_category (
    id UUID PRIMARY KEY,
    sub_category_id VARCHAR(255) NOT NULL,
    total_quantity INTEGER NOT NULL,
    sub_category_name VARCHAR(255) NOT NULL,
    sub_category_image BYTEA,
    category_id UUID,
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
    CONSTRAINT fk_component
        FOREIGN KEY (component_id)
        REFERENCES component(id)
);
