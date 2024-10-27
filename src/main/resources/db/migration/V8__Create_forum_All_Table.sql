-- Tabela Forum
CREATE TABLE forum (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    code_snippet TEXT,  -- Campo para armazenar trecho de código
    status VARCHAR(10) NOT NULL,
    user_id UUID REFERENCES users(id),
    creation_date TIMESTAMP,
    edit_date TIMESTAMP,
    vote_count INT DEFAULT 0
);

-- Tabela ForumComment (para evitar conflito com "comment" em outro cenário)
CREATE TABLE forum_comment (
    id UUID PRIMARY KEY,
    content TEXT NOT NULL,
    code_snippet TEXT,  -- Campo para armazenar trecho de código
    user_id UUID REFERENCES users(id),
    forum_id UUID REFERENCES forum(id),
    creation_date TIMESTAMP,
    edit_date TIMESTAMP,
    vote_count INT DEFAULT 0
);

-- Tabela Tag
CREATE TABLE tag (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Tabela de Relacionamento entre Forum e Tag
CREATE TABLE forum_tag (
    forum_id UUID REFERENCES forum(id),
    tag_id UUID REFERENCES tag(id),
    PRIMARY KEY (forum_id, tag_id)
);

-- Tabela EditHistory
CREATE TABLE edit_history (
    id UUID PRIMARY KEY,
    previous_content TEXT NOT NULL,
    type VARCHAR(10) NOT NULL,  -- Enum para diferenciar tipos de edição (pergunta, comentário, etc)
    reference_id UUID NOT NULL,  -- Refere-se ao ID do fórum ou comentário editado
    editor_user_id UUID REFERENCES users(id),
    edit_date TIMESTAMP
);
