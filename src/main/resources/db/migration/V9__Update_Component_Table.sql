ALTER TABLE component
    ADD COLUMN tutorial_link VARCHAR(255);

ALTER TABLE component
    ADD COLUMN project_ideas TEXT;

ALTER TABLE component
    ADD COLUMN library_suggestions TEXT;

-- Atualizar os registros antigos para evitar nulos nos novos campos (opcional)
UPDATE component
SET tutorial_link = '', project_ideas = '', library_suggestions = ''
WHERE tutorial_link IS NULL OR project_ideas IS NULL OR library_suggestions IS NULL;
