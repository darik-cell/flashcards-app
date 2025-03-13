-- Таблица карточек
CREATE TABLE cards (
    id BIGSERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    collection_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cards_collection FOREIGN KEY (collection_id) REFERENCES collections(id) ON DELETE CASCADE
);