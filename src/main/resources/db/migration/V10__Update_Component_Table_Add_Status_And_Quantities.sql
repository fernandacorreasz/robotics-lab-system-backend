ALTER TABLE component
    ADD COLUMN defective_quantity INTEGER DEFAULT 0,
    ADD COLUMN discarded_quantity INTEGER DEFAULT 0,
    ADD COLUMN status VARCHAR(50) DEFAULT 'AVAILABLE';

-- Atualizar os registros antigos para valores padrão
UPDATE component
SET defective_quantity = 0, discarded_quantity = 0, status = 'AVAILABLE'
WHERE defective_quantity IS NULL OR discarded_quantity IS NULL OR status IS NULL;

-- Adicionar constraints para garantir valores consistentes nas novas colunas
ALTER TABLE component
    ADD CONSTRAINT chk_defective_quantity CHECK (defective_quantity >= 0),
    ADD CONSTRAINT chk_discarded_quantity CHECK (discarded_quantity >= 0),
    ADD CONSTRAINT chk_status CHECK (status IN ('AVAILABLE', 'DEFECTIVE', 'DISCARDED', 'IN_USE', 'UNDER_MAINTENANCE'));