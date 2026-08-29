CREATE TABLE tags (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX uk_tags_name_lower
    ON tags (LOWER(name));
