-- Create table for LoanComponent
CREATE TABLE loan_component (
    id UUID PRIMARY KEY,
    loan_id VARCHAR(255) NOT NULL,
    borrower_id UUID,
    authorizer_id UUID,
    component_id UUID,
    loan_date TIMESTAMP,
    expected_return_date TIMESTAMP,
    actual_return_date TIMESTAMP,
    return_authorizer_id UUID,
    status VARCHAR(50) NOT NULL,
    -- Audit and version control columns
    row_version SMALLINT NOT NULL DEFAULT 0,
    row_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    row_created_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    row_updated_by VARCHAR(64) NOT NULL DEFAULT 'unknown',
    
    CONSTRAINT fk_borrower
        FOREIGN KEY (borrower_id) 
        REFERENCES users(id),
    CONSTRAINT fk_authorizer
        FOREIGN KEY (authorizer_id) 
        REFERENCES users(id),
    CONSTRAINT fk_component
        FOREIGN KEY (component_id) 
        REFERENCES component(id),
    CONSTRAINT fk_return_authorizer
        FOREIGN KEY (return_authorizer_id) 
        REFERENCES users(id)
);
