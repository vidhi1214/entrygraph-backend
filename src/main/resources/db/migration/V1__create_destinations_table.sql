CREATE TABLE destinations (
    id UUID PRIMARY KEY,

    name VARCHAR(255) NOT NULL,
    address VARCHAR(500),

    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,

    category VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(100) NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);