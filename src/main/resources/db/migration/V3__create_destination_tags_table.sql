CREATE TABLE destination_tags (
    destination_id UUID NOT NULL,
    tag_id UUID NOT NULL,

    PRIMARY KEY (destination_id, tag_id),

    CONSTRAINT fk_destination_tags_destination
        FOREIGN KEY (destination_id)
        REFERENCES destinations(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_destination_tags_tag
        FOREIGN KEY (tag_id)
        REFERENCES tags(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_destination_tags_tag_id
    ON destination_tags(tag_id);
