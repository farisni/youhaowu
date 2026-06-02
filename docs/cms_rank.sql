-- 商品排行表
CREATE TABLE IF NOT EXISTS cms_rank (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200),
    img_url VARCHAR(500),
    price VARCHAR(50),
    tab_type VARCHAR(20) NOT NULL,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE cms_rank IS '商品排行';
COMMENT ON COLUMN cms_rank.tab_type IS 'hot=热卖 / sale=特价 / new=新品';
